package com.example.viewmodel

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CommissionOption
import com.example.data.ExamAttemptEntity
import com.example.data.ExamMode
import com.example.data.GdChecklistEntity
import com.example.data.JsonExportManager
import com.example.data.KrishiDatabase
import com.example.data.KrishiRepository
import com.example.data.McqQuestionEntity
import com.example.data.NavTab
import com.example.data.NoteSection
import com.example.data.PolicySection
import com.example.data.PostLevelOption
import com.example.data.SeedExamAndSkillData
import com.example.data.StudyNoteEntity
import com.example.data.TypingLanguage
import com.example.data.TypingPassage
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.math.max

sealed interface ExamPhase {
    data object PreExam : ExamPhase
    data class InProgress(
        val mode: ExamMode,
        val questions: List<McqQuestionEntity>,
        val selectedAnswers: Map<String, Int>,
        val remainingSeconds: Int,
        val elapsedSeconds: Int,
        val isTimed: Boolean
    ) : ExamPhase

    data class Completed(
        val mode: ExamMode,
        val questions: List<McqQuestionEntity>,
        val selectedAnswers: Map<String, Int>,
        val correctCount: Int,
        val wrongCount: Int,
        val skippedCount: Int,
        val netScore: Double,
        val durationSeconds: Int
    ) : ExamPhase
}

data class TypingUiState(
    val language: TypingLanguage = TypingLanguage.ENGLISH,
    val passageIndex: Int = 0,
    val typedText: String = "",
    val remainingSeconds: Int = 300,
    val isRunning: Boolean = false,
    val isFinished: Boolean = false,
    val totalWordsTyped: Int = 0,
    val wrongWords: Int = 0,
    val correctWords: Int = 0,
    val netWpm: Double = 0.0
)

data class StudyProgressSummary(
    val totalNotes: Int = 0,
    val completedNotes: Int = 0,
    val bookmarkedNotes: Int = 0,
    val totalMcqs: Int = 0,
    val bestNetScore: Double? = null,
    val totalAttempts: Int = 0
) {
    val completionFraction: Float
        get() = if (totalNotes == 0) 0f else completedNotes.toFloat() / totalNotes.toFloat()
}

class KrishiPrepViewModel(application: Application) : AndroidViewModel(application) {

    private val database = KrishiDatabase.getInstance(application)
    private val repository = KrishiRepository(
        noteDao = database.studyNoteDao(),
        mcqDao = database.mcqQuestionDao(),
        examAttemptDao = database.examAttemptDao(),
        gdChecklistDao = database.gdChecklistDao()
    )

    private val _activeTab = MutableStateFlow(NavTab.HOME)
    val activeTab: StateFlow<NavTab> = _activeTab.asStateFlow()

    private val _selectedCommission = MutableStateFlow(CommissionOption.ALL)
    val selectedCommission: StateFlow<CommissionOption> = _selectedCommission.asStateFlow()

    private val _selectedPostLevel = MutableStateFlow(PostLevelOption.OFFICER)
    val selectedPostLevel: StateFlow<PostLevelOption> = _selectedPostLevel.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _bookmarksOnly = MutableStateFlow(false)
    val bookmarksOnly: StateFlow<Boolean> = _bookmarksOnly.asStateFlow()

    private val _selectedPracticeSubject = MutableStateFlow("ALL")
    val selectedPracticeSubject: StateFlow<String> = _selectedPracticeSubject.asStateFlow()

    private val _selectedExamMode = MutableStateFlow(ExamMode.PSC_FULL)
    val selectedExamMode: StateFlow<ExamMode> = _selectedExamMode.asStateFlow()

    private val _examPhase = MutableStateFlow<ExamPhase>(ExamPhase.PreExam)
    val examPhase: StateFlow<ExamPhase> = _examPhase.asStateFlow()

    private val _typingState = MutableStateFlow(TypingUiState())
    val typingState: StateFlow<TypingUiState> = _typingState.asStateFlow()

    private val _activePolicySection = MutableStateFlow<PolicySection?>(null)
    val activePolicySection: StateFlow<PolicySection?> = _activePolicySection.asStateFlow()

    private val _showCreatorManager = MutableStateFlow(false)
    val showCreatorManager: StateFlow<Boolean> = _showCreatorManager.asStateFlow()

    private val _snackbarMessages = MutableSharedFlow<String>(extraBufferCapacity = 4)
    val snackbarMessages: SharedFlow<String> = _snackbarMessages.asSharedFlow()

    private var creatorTapCount = 0
    private var lastCreatorTapTimeMs = 0L
    private var examTimerJob: Job? = null
    private var typingTimerJob: Job? = null

    val allNotes: StateFlow<List<StudyNoteEntity>> = repository.allNotes.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val allQuestions: StateFlow<List<McqQuestionEntity>> = repository.allQuestions.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val recentAttempts: StateFlow<List<ExamAttemptEntity>> = repository.recentAttempts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val gdChecklist: StateFlow<List<GdChecklistEntity>> = repository.gdChecklist.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val filteredNotes: StateFlow<List<StudyNoteEntity>> = combine(
        allNotes,
        selectedCommission,
        selectedPostLevel,
        searchQuery,
        bookmarksOnly
    ) { notes, commission, postLevel, query, onlyBookmarked ->
        val trimmedQuery = query.trim().lowercase()
        notes.filter { note ->
            val matchesCommission = when (commission) {
                CommissionOption.ALL -> true
                else -> note.commissionKey == commission.key || note.commissionKey == "ALL"
            }
            val matchesPostLevel = note.postLevelScope == "ALL" || note.postLevelScope == postLevel.key
            val matchesBookmark = !onlyBookmarked || note.isBookmarked
            val matchesQuery = trimmedQuery.isEmpty() ||
                note.title.lowercase().contains(trimmedQuery) ||
                note.tag.lowercase().contains(trimmedQuery) ||
                note.overview.lowercase().contains(trimmedQuery) ||
                note.quickFact.lowercase().contains(trimmedQuery) ||
                note.sections.any { sec ->
                    sec.subheading.lowercase().contains(trimmedQuery) ||
                        sec.bullets.any { it.lowercase().contains(trimmedQuery) }
                }
            matchesCommission && matchesPostLevel && matchesBookmark && matchesQuery
        }.sortedByDescending { note ->
            // Surface province-specific flagship cards & user-created notes at the top
            var priority = 0
            if (note.isUserCreated) priority += 10
            if (commission != CommissionOption.ALL && note.commissionKey == commission.key) priority += 5
            if (note.postLevelScope == postLevel.key) priority += 2
            priority
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val availablePracticeSubjects: StateFlow<List<String>> = allQuestions.combine(
        selectedCommission
    ) { questions, _ ->
        listOf("ALL") + questions.map { it.subjectTag }.distinct().sorted()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf("ALL")
    )

    val progressSummary: StateFlow<StudyProgressSummary> = combine(
        allNotes,
        allQuestions,
        recentAttempts
    ) { notes, mcqs, attempts ->
        StudyProgressSummary(
            totalNotes = notes.size,
            completedNotes = notes.count { it.isCompleted },
            bookmarkedNotes = notes.count { it.isBookmarked },
            totalMcqs = mcqs.size,
            bestNetScore = attempts.maxOfOrNull { it.netScore },
            totalAttempts = attempts.size
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StudyProgressSummary()
    )

    init {
        viewModelScope.launch {
            repository.ensureSeeded()
        }
    }

    fun selectTab(tab: NavTab) {
        _activeTab.value = tab
    }

    fun selectCommission(option: CommissionOption) {
        _selectedCommission.value = option
    }

    fun selectPostLevel(option: PostLevelOption) {
        _selectedPostLevel.value = option
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleBookmarksOnly() {
        _bookmarksOnly.update { !it }
    }

    fun resetNoteFilters() {
        _searchQuery.value = ""
        _bookmarksOnly.value = false
        _selectedCommission.value = CommissionOption.ALL
    }

    fun toggleNoteCompleted(note: StudyNoteEntity) {
        viewModelScope.launch {
            val newState = !note.isCompleted
            repository.setNoteCompleted(note.id, newState)
            _snackbarMessages.tryEmit(
                if (newState) "Marked '${note.title}' as completed" else "Removed completion mark"
            )
        }
    }

    fun toggleNoteBookmarked(note: StudyNoteEntity) {
        viewModelScope.launch {
            val newState = !note.isBookmarked
            repository.setNoteBookmarked(note.id, newState)
        }
    }

    // --- Creator 5-Tap Unlock & Modals ---

    fun onCreatorTitleTapped() {
        val now = System.currentTimeMillis()
        if (now - lastCreatorTapTimeMs > 2500L) {
            creatorTapCount = 0
        }
        lastCreatorTapTimeMs = now
        creatorTapCount += 1
        when {
            creatorTapCount >= 5 -> {
                creatorTapCount = 0
                _showCreatorManager.value = true
            }
            creatorTapCount >= 3 -> {
                val remaining = 5 - creatorTapCount
                _snackbarMessages.tryEmit(
                    "Creator Mode: tap $remaining more time${if (remaining == 1) "" else "s"}"
                )
            }
        }
    }

    fun closeCreatorManager() {
        _showCreatorManager.value = false
    }

    fun openPolicyModal(section: PolicySection = PolicySection.ABOUT) {
        _activePolicySection.value = section
    }

    fun closePolicyModal() {
        _activePolicySection.value = null
    }

    // --- Mock Exam Engine ---

    fun selectExamMode(mode: ExamMode) {
        _selectedExamMode.value = mode
    }

    fun selectPracticeSubject(subject: String) {
        _selectedPracticeSubject.value = subject
    }

    fun matchingPracticeQuestionsCount(): Int {
        return computeExamQuestionSet(ExamMode.TOPIC_PRACTICE).size
    }

    private fun computeExamQuestionSet(mode: ExamMode): List<McqQuestionEntity> {
        val all = allQuestions.value
        if (mode == ExamMode.PSC_FULL) {
            return all
        }
        val commission = selectedCommission.value
        val postLevel = selectedPostLevel.value
        val subject = selectedPracticeSubject.value

        val filtered = all.filter { q ->
            val matchesCommission = commission == CommissionOption.ALL ||
                q.commissionKey == commission.key || q.commissionKey == "ALL"
            val matchesLevel = q.postLevelScope == "ALL" || q.postLevelScope == postLevel.key
            val matchesSubject = subject == "ALL" || q.subjectTag == subject
            matchesCommission && matchesLevel && matchesSubject
        }
        return if (filtered.isNotEmpty()) filtered else all
    }

    fun startExam(mode: ExamMode = _selectedExamMode.value) {
        val questions = computeExamQuestionSet(mode)
        if (questions.isEmpty()) {
            _snackbarMessages.tryEmit("No MCQs available yet.")
            return
        }
        examTimerJob?.cancel()
        val isTimed = mode == ExamMode.PSC_FULL
        val initialRemaining = if (isTimed) 45 * 60 else 0
        _examPhase.value = ExamPhase.InProgress(
            mode = mode,
            questions = questions,
            selectedAnswers = emptyMap(),
            remainingSeconds = initialRemaining,
            elapsedSeconds = 0,
            isTimed = isTimed
        )

        examTimerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)
                val current = _examPhase.value
                if (current !is ExamPhase.InProgress) break
                if (current.isTimed) {
                    val nextRemaining = current.remainingSeconds - 1
                    if (nextRemaining <= 0) {
                        submitExam()
                        break
                    } else {
                        _examPhase.value = current.copy(
                            remainingSeconds = nextRemaining,
                            elapsedSeconds = current.elapsedSeconds + 1
                        )
                    }
                } else {
                    _examPhase.value = current.copy(
                        elapsedSeconds = current.elapsedSeconds + 1
                    )
                }
            }
        }
    }

    fun selectAnswer(questionId: String, optionIndex: Int) {
        val current = _examPhase.value as? ExamPhase.InProgress ?: return
        val updated = current.selectedAnswers.toMutableMap()
        if (updated[questionId] == optionIndex) {
            // Tapping the selected answer again clears it so user can skip with 0 deduction
            updated.remove(questionId)
        } else {
            updated[questionId] = optionIndex
        }
        _examPhase.value = current.copy(selectedAnswers = updated)
    }

    fun clearAnswer(questionId: String) {
        val current = _examPhase.value as? ExamPhase.InProgress ?: return
        val updated = current.selectedAnswers.toMutableMap().apply { remove(questionId) }
        _examPhase.value = current.copy(selectedAnswers = updated)
    }

    fun submitExam() {
        val current = _examPhase.value as? ExamPhase.InProgress ?: return
        examTimerJob?.cancel()

        var correct = 0
        var wrong = 0
        var skipped = 0

        current.questions.forEach { q ->
            val selected = current.selectedAnswers[q.id]
            when {
                selected == null -> skipped++
                selected == q.correctIndex -> correct++
                else -> wrong++
            }
        }

        // Exact Nepal PSC Formula: Net Score = Correct - (Wrong * 0.20)
        val netScore = correct - (wrong * 0.20)

        _examPhase.value = ExamPhase.Completed(
            mode = current.mode,
            questions = current.questions,
            selectedAnswers = current.selectedAnswers,
            correctCount = correct,
            wrongCount = wrong,
            skippedCount = skipped,
            netScore = netScore,
            durationSeconds = current.elapsedSeconds
        )

        viewModelScope.launch {
            repository.saveExamAttempt(
                ExamAttemptEntity(
                    modeKey = current.mode.key,
                    commissionLabel = selectedCommission.value.shortName,
                    totalQuestions = current.questions.size,
                    correctCount = correct,
                    wrongCount = wrong,
                    skippedCount = skipped,
                    netScore = netScore,
                    durationSeconds = current.elapsedSeconds
                )
            )
        }
    }

    fun exitExamToPreExam() {
        examTimerJob?.cancel()
        _examPhase.value = ExamPhase.PreExam
    }

    // --- PSC Typing Speed Simulator ---

    fun passagesForLanguage(language: TypingLanguage): List<TypingPassage> =
        SeedExamAndSkillData.typingPassages().filter { it.language == language }

    fun currentTypingPassage(): TypingPassage {
        val state = _typingState.value
        val list = passagesForLanguage(state.language)
        return list.getOrElse(state.passageIndex) { list.first() }
    }

    fun selectTypingLanguage(language: TypingLanguage) {
        typingTimerJob?.cancel()
        _typingState.value = TypingUiState(language = language, passageIndex = 0)
    }

    fun selectTypingPassage(index: Int) {
        typingTimerJob?.cancel()
        _typingState.value = TypingUiState(
            language = _typingState.value.language,
            passageIndex = index
        )
    }

    fun startTypingTimer() {
        val current = _typingState.value
        if (current.isRunning || current.isFinished) return
        _typingState.value = current.copy(isRunning = true)
        launchTypingCountdown()
    }

    fun onTypingInputChanged(newText: String) {
        val current = _typingState.value
        if (current.isFinished) return

        val shouldStart = !current.isRunning && newText.isNotEmpty()
        val metrics = evaluateTypingMetrics(currentTypingPassage().content, newText)
        _typingState.value = current.copy(
            typedText = newText,
            isRunning = current.isRunning || shouldStart,
            totalWordsTyped = metrics.totalWords,
            wrongWords = metrics.wrongWords,
            correctWords = metrics.correctWords,
            netWpm = metrics.netWpm
        )
        if (shouldStart) {
            launchTypingCountdown()
        }
    }

    fun finishTypingTest() {
        typingTimerJob?.cancel()
        val current = _typingState.value
        val metrics = evaluateTypingMetrics(currentTypingPassage().content, current.typedText)
        _typingState.value = current.copy(
            isRunning = false,
            isFinished = true,
            totalWordsTyped = metrics.totalWords,
            wrongWords = metrics.wrongWords,
            correctWords = metrics.correctWords,
            netWpm = metrics.netWpm
        )
    }

    fun resetTypingTest() {
        typingTimerJob?.cancel()
        val current = _typingState.value
        _typingState.value = TypingUiState(
            language = current.language,
            passageIndex = current.passageIndex
        )
    }

    private fun launchTypingCountdown() {
        typingTimerJob?.cancel()
        typingTimerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)
                val current = _typingState.value
                if (!current.isRunning || current.isFinished) break
                val next = current.remainingSeconds - 1
                if (next <= 0) {
                    _typingState.value = current.copy(remainingSeconds = 0)
                    finishTypingTest()
                    break
                } else {
                    _typingState.value = current.copy(remainingSeconds = next)
                }
            }
        }
    }

    private data class TypingMetrics(
        val totalWords: Int,
        val correctWords: Int,
        val wrongWords: Int,
        val netWpm: Double
    )

    private fun evaluateTypingMetrics(reference: String, typed: String): TypingMetrics {
        val refWords = reference.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }
        val typedWords = typed.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }
        var correct = 0
        var wrong = 0
        typedWords.forEachIndexed { idx, word ->
            if (idx < refWords.size && word == refWords[idx]) {
                correct++
            } else {
                wrong++
            }
        }
        // Official PSC 5-minute formula: Net WPM = Math.max(0, (Total Words Typed - Wrong Words) / 5)
        val netWpm = max(0.0, (typedWords.size - wrong) / 5.0)
        return TypingMetrics(
            totalWords = typedWords.size,
            correctWords = correct,
            wrongWords = wrong,
            netWpm = netWpm
        )
    }

    // --- GD & Interview Checklist ---

    fun toggleChecklistItem(item: GdChecklistEntity) {
        viewModelScope.launch {
            repository.setChecklistChecked(item.id, !item.isChecked)
        }
    }

    // --- Creator Content Manager ---

    fun saveCreatorStudyNote(
        title: String,
        commissionKey: String,
        postLevelScope: String,
        paper: String,
        tag: String,
        overview: String,
        bulletsRaw: String,
        quickFact: String
    ): Boolean {
        if (title.isBlank() || overview.isBlank()) {
            _snackbarMessages.tryEmit("Please fill in at least the Title and Overview.")
            return false
        }
        val bullets = bulletsRaw
            .lines()
            .map { it.trim().removePrefix("-").removePrefix("•").trim() }
            .filter { it.isNotEmpty() }

        val note = StudyNoteEntity(
            id = "custom_note_${UUID.randomUUID().toString().take(8)}",
            title = title.trim(),
            commissionKey = commissionKey,
            postLevelScope = postLevelScope,
            paper = paper.ifBlank { "Paper I & II" }.trim(),
            tag = tag.ifBlank { "Creator Update" }.trim(),
            overview = overview.trim(),
            sections = if (bullets.isNotEmpty()) {
                listOf(NoteSection(subheading = "Key Study Points", bullets = bullets))
            } else {
                emptyList()
            },
            quickFact = quickFact.trim(),
            isUserCreated = true,
            createdAt = System.currentTimeMillis()
        )
        viewModelScope.launch {
            repository.addStudyNote(note)
            _snackbarMessages.tryEmit("Study note added locally & ready for JSON export.")
        }
        return true
    }

    fun saveCreatorMcq(
        question: String,
        optionA: String,
        optionB: String,
        optionC: String,
        optionD: String,
        correctIndex: Int,
        explanation: String,
        commissionKey: String,
        postLevelScope: String,
        subjectTag: String
    ): Boolean {
        if (question.isBlank() || optionA.isBlank() || optionB.isBlank() ||
            optionC.isBlank() || optionD.isBlank()
        ) {
            _snackbarMessages.tryEmit("Please complete the question and all 4 options.")
            return false
        }
        val mcq = McqQuestionEntity(
            id = "custom_mcq_${UUID.randomUUID().toString().take(8)}",
            question = question.trim(),
            optionA = optionA.trim(),
            optionB = optionB.trim(),
            optionC = optionC.trim(),
            optionD = optionD.trim(),
            correctIndex = correctIndex.coerceIn(0, 3),
            explanation = explanation.ifBlank { "Verified by Creator: Apar Neupane." }.trim(),
            commissionKey = commissionKey,
            postLevelScope = postLevelScope,
            subjectTag = subjectTag.ifBlank { "General Agriculture" }.trim(),
            isUserCreated = true,
            createdAt = System.currentTimeMillis()
        )
        viewModelScope.launch {
            repository.addMcqQuestion(mcq)
            _snackbarMessages.tryEmit("MCQ added locally & ready for Mock Exam.")
        }
        return true
    }

    fun exportAndShareStudyData(context: Context) {
        viewModelScope.launch {
            val (notes, mcqs) = repository.getExportSnapshot()
            val result = JsonExportManager.shareJsonFile(context, notes, mcqs)
            result.onSuccess {
                _snackbarMessages.tryEmit("Exported studyData.json (${notes.size} notes, ${mcqs.size} MCQs)")
            }.onFailure {
                _snackbarMessages.tryEmit("Unable to open Share Sheet: ${it.localizedMessage ?: "unknown error"}")
            }
        }
    }

    fun copyStudyDataJsonToClipboard(context: Context) {
        viewModelScope.launch {
            val (notes, mcqs) = repository.getExportSnapshot()
            val json = JsonExportManager.buildJson(notes, mcqs)
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("studyData.json", json))
            _snackbarMessages.tryEmit("Copied studyData.json (${notes.size} notes, ${mcqs.size} MCQs) to clipboard")
        }
    }

    override fun onCleared() {
        examTimerJob?.cancel()
        typingTimerJob?.cancel()
        super.onCleared()
    }
}
