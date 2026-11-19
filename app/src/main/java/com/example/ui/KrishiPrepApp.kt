package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.NavTab
import com.example.data.PolicySection
import com.example.ui.components.JurisdictionFilterBar
import com.example.ui.components.KrishiBottomNavigationBar
import com.example.ui.components.KrishiNavigationRail
import com.example.ui.components.StickyKrishiTopHeader
import com.example.ui.modals.CreatorManagerModal
import com.example.ui.modals.PolicyLegalModal
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MockExamScreen
import com.example.ui.screens.NotesScreen
import com.example.ui.screens.SkillGdScreen
import com.example.viewmodel.ExamPhase
import com.example.viewmodel.KrishiPrepViewModel

@Composable
fun KrishiPrepApp(
    viewModel: KrishiPrepViewModel = viewModel()
) {
    val activeTab by viewModel.activeTab.collectAsStateWithLifecycle()
    val selectedCommission by viewModel.selectedCommission.collectAsStateWithLifecycle()
    val selectedPostLevel by viewModel.selectedPostLevel.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val bookmarksOnly by viewModel.bookmarksOnly.collectAsStateWithLifecycle()
    val filteredNotes by viewModel.filteredNotes.collectAsStateWithLifecycle()
    val allNotes by viewModel.allNotes.collectAsStateWithLifecycle()
    val allQuestions by viewModel.allQuestions.collectAsStateWithLifecycle()
    val progressSummary by viewModel.progressSummary.collectAsStateWithLifecycle()
    val selectedExamMode by viewModel.selectedExamMode.collectAsStateWithLifecycle()
    val selectedPracticeSubject by viewModel.selectedPracticeSubject.collectAsStateWithLifecycle()
    val availablePracticeSubjects by viewModel.availablePracticeSubjects.collectAsStateWithLifecycle()
    val examPhase by viewModel.examPhase.collectAsStateWithLifecycle()
    val recentAttempts by viewModel.recentAttempts.collectAsStateWithLifecycle()
    val typingState by viewModel.typingState.collectAsStateWithLifecycle()
    val gdChecklist by viewModel.gdChecklist.collectAsStateWithLifecycle()
    val activePolicySection by viewModel.activePolicySection.collectAsStateWithLifecycle()
    val showCreatorManager by viewModel.showCreatorManager.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbarMessages.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    // Back gesture returns non-Home tabs to Home cleanly (unless an active exam handles back itself)
    BackHandler(enabled = activeTab != NavTab.HOME && examPhase is ExamPhase.PreExam) {
        viewModel.selectTab(NavTab.HOME)
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useNavigationRail = maxWidth >= 600.dp
        val isExamInProgress = activeTab == NavTab.EXAM && examPhase is ExamPhase.InProgress

        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                Column {
                    StickyKrishiTopHeader(
                        onCreatorTap = viewModel::onCreatorTitleTapped,
                        onOpenPolicy = { viewModel.openPolicyModal(PolicySection.ABOUT) }
                    )
                    if (!isExamInProgress) {
                        JurisdictionFilterBar(
                            selectedCommission = selectedCommission,
                            selectedPostLevel = selectedPostLevel,
                            onSelectCommission = viewModel::selectCommission,
                            onSelectPostLevel = viewModel::selectPostLevel
                        )
                    }
                }
            },
            bottomBar = {
                if (!useNavigationRail) {
                    KrishiBottomNavigationBar(
                        activeTab = activeTab,
                        onSelectTab = viewModel::selectTab
                    )
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (useNavigationRail) {
                    KrishiNavigationRail(
                        activeTab = activeTab,
                        onSelectTab = viewModel::selectTab
                    )
                }

                when (activeTab) {
                    NavTab.HOME -> HomeScreen(
                        progress = progressSummary,
                        selectedCommission = selectedCommission,
                        selectedPostLevel = selectedPostLevel,
                        onNavigateTab = viewModel::selectTab,
                        onOpenPolicy = viewModel::openPolicyModal,
                        modifier = Modifier.weight(1f)
                    )

                    NavTab.NOTES -> NotesScreen(
                        notes = filteredNotes,
                        searchQuery = searchQuery,
                        bookmarksOnly = bookmarksOnly,
                        onSearchQueryChange = viewModel::updateSearchQuery,
                        onToggleBookmarksOnly = viewModel::toggleBookmarksOnly,
                        onResetFilters = viewModel::resetNoteFilters,
                        onToggleCompleted = viewModel::toggleNoteCompleted,
                        onToggleBookmarked = viewModel::toggleNoteBookmarked,
                        modifier = Modifier.weight(1f)
                    )

                    NavTab.EXAM -> MockExamScreen(
                        examPhase = examPhase,
                        selectedMode = selectedExamMode,
                        selectedCommission = selectedCommission,
                        selectedPostLevel = selectedPostLevel,
                        selectedPracticeSubject = selectedPracticeSubject,
                        availableSubjects = availablePracticeSubjects,
                        totalQuestionsAvailable = allQuestions.size,
                        practiceQuestionsCount = viewModel.matchingPracticeQuestionsCount(),
                        recentAttempts = recentAttempts,
                        onSelectMode = viewModel::selectExamMode,
                        onSelectPracticeSubject = viewModel::selectPracticeSubject,
                        onStartExam = viewModel::startExam,
                        onSelectAnswer = viewModel::selectAnswer,
                        onClearAnswer = viewModel::clearAnswer,
                        onSubmitExam = viewModel::submitExam,
                        onExitExam = viewModel::exitExamToPreExam,
                        modifier = Modifier.weight(1f)
                    )

                    NavTab.SKILL_GD -> SkillGdScreen(
                        typingState = typingState,
                        availablePassages = viewModel.passagesForLanguage(typingState.language),
                        currentPassage = viewModel.currentTypingPassage(),
                        checklist = gdChecklist,
                        onSelectLanguage = viewModel::selectTypingLanguage,
                        onSelectPassage = viewModel::selectTypingPassage,
                        onStartTyping = viewModel::startTypingTimer,
                        onTypingInputChange = viewModel::onTypingInputChanged,
                        onFinishTyping = viewModel::finishTypingTest,
                        onResetTyping = viewModel::resetTypingTest,
                        onToggleChecklistItem = viewModel::toggleChecklistItem,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }

    // Policy & Legal Modal
    activePolicySection?.let { section ->
        PolicyLegalModal(
            initialSection = section,
            onDismiss = viewModel::closePolicyModal
        )
    }

    // Visual Creator Content Manager Modal (unlocked by 5 taps on "Creator: Apar Neupane")
    if (showCreatorManager) {
        CreatorManagerModal(
            totalNotesCount = allNotes.size,
            totalMcqsCount = allQuestions.size,
            onAddStudyNote = viewModel::saveCreatorStudyNote,
            onAddMcq = viewModel::saveCreatorMcq,
            onExportShareJson = viewModel::exportAndShareStudyData,
            onCopyJson = viewModel::copyStudyDataJsonToClipboard,
            onDismiss = viewModel::closeCreatorManager
        )
    }
}
