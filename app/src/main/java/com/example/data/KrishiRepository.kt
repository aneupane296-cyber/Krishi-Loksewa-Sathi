package com.example.data

import kotlinx.coroutines.flow.Flow

class KrishiRepository(
    private val noteDao: StudyNoteDao,
    private val mcqDao: McqQuestionDao,
    private val examAttemptDao: ExamAttemptDao,
    private val gdChecklistDao: GdChecklistDao
) {
    val allNotes: Flow<List<StudyNoteEntity>> = noteDao.observeAllNotes()
    val allQuestions: Flow<List<McqQuestionEntity>> = mcqDao.observeAllQuestions()
    val recentAttempts: Flow<List<ExamAttemptEntity>> = examAttemptDao.observeRecentAttempts()
    val gdChecklist: Flow<List<GdChecklistEntity>> = gdChecklistDao.observeChecklist()

    suspend fun ensureSeeded() {
        if (noteDao.countNotes() == 0) {
            noteDao.insertAllIgnore(SeedNotesData.initialNotes())
        }
        if (mcqDao.countQuestions() == 0) {
            mcqDao.insertAllIgnore(SeedExamAndSkillData.initialMcqs())
        }
        if (gdChecklistDao.countChecklist() == 0) {
            gdChecklistDao.insertAllIgnore(SeedExamAndSkillData.initialGdChecklist())
        }
    }

    suspend fun setNoteCompleted(noteId: String, completed: Boolean) {
        noteDao.setCompleted(noteId, completed)
    }

    suspend fun setNoteBookmarked(noteId: String, bookmarked: Boolean) {
        noteDao.setBookmarked(noteId, bookmarked)
    }

    suspend fun addStudyNote(note: StudyNoteEntity) {
        noteDao.insertNote(note)
    }

    suspend fun addMcqQuestion(question: McqQuestionEntity) {
        mcqDao.insertQuestion(question)
    }

    suspend fun saveExamAttempt(attempt: ExamAttemptEntity) {
        examAttemptDao.insertAttempt(attempt)
    }

    suspend fun setChecklistChecked(itemId: String, checked: Boolean) {
        gdChecklistDao.setChecked(itemId, checked)
    }

    suspend fun getExportSnapshot(): Pair<List<StudyNoteEntity>, List<McqQuestionEntity>> {
        return noteDao.getAllNotesSnapshot() to mcqDao.getAllQuestionsSnapshot()
    }
}
