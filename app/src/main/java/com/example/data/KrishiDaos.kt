package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyNoteDao {
    @Query("SELECT * FROM study_notes ORDER BY isUserCreated DESC, createdAt ASC")
    fun observeAllNotes(): Flow<List<StudyNoteEntity>>

    @Query("SELECT * FROM study_notes ORDER BY isUserCreated DESC, createdAt ASC")
    suspend fun getAllNotesSnapshot(): List<StudyNoteEntity>

    @Query("SELECT COUNT(*) FROM study_notes")
    suspend fun countNotes(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllIgnore(notes: List<StudyNoteEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: StudyNoteEntity)

    @Query("UPDATE study_notes SET isCompleted = :completed WHERE id = :noteId")
    suspend fun setCompleted(noteId: String, completed: Boolean)

    @Query("UPDATE study_notes SET isBookmarked = :bookmarked WHERE id = :noteId")
    suspend fun setBookmarked(noteId: String, bookmarked: Boolean)
}

@Dao
interface McqQuestionDao {
    @Query("SELECT * FROM mcq_questions ORDER BY isUserCreated DESC, createdAt ASC")
    fun observeAllQuestions(): Flow<List<McqQuestionEntity>>

    @Query("SELECT * FROM mcq_questions ORDER BY isUserCreated DESC, createdAt ASC")
    suspend fun getAllQuestionsSnapshot(): List<McqQuestionEntity>

    @Query("SELECT COUNT(*) FROM mcq_questions")
    suspend fun countQuestions(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllIgnore(questions: List<McqQuestionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: McqQuestionEntity)
}

@Dao
interface ExamAttemptDao {
    @Query("SELECT * FROM exam_attempts ORDER BY completedAt DESC LIMIT 20")
    fun observeRecentAttempts(): Flow<List<ExamAttemptEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttempt(attempt: ExamAttemptEntity)
}

@Dao
interface GdChecklistDao {
    @Query("SELECT * FROM gd_checklist_items ORDER BY sortOrder ASC")
    fun observeChecklist(): Flow<List<GdChecklistEntity>>

    @Query("SELECT COUNT(*) FROM gd_checklist_items")
    suspend fun countChecklist(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllIgnore(items: List<GdChecklistEntity>)

    @Query("UPDATE gd_checklist_items SET isChecked = :checked WHERE id = :itemId")
    suspend fun setChecked(itemId: String, checked: Boolean)
}
