package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@Entity(tableName = "study_notes")
@JsonClass(generateAdapter = true)
data class StudyNoteEntity(
    @PrimaryKey val id: String,
    val title: String,
    val commissionKey: String, // "ALL", "FEDERAL", "KOSHI", etc.
    val postLevelScope: String, // "ALL", "OFFICER", "JT_JTA"
    val paper: String, // "Paper I", "Paper II", "Paper I & II", "Practical"
    val tag: String, // e.g., "Constitution & Acts", "Agronomy"
    val overview: String,
    val sections: List<NoteSection>,
    val quickFact: String,
    val isCompleted: Boolean = false,
    val isBookmarked: Boolean = false,
    val isUserCreated: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "mcq_questions")
@JsonClass(generateAdapter = true)
data class McqQuestionEntity(
    @PrimaryKey val id: String,
    val question: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctIndex: Int, // 0 = A, 1 = B, 2 = C, 3 = D
    val explanation: String,
    val commissionKey: String = "ALL",
    val postLevelScope: String = "ALL",
    val subjectTag: String = "General Agriculture",
    val isUserCreated: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun optionsList(): List<String> = listOf(optionA, optionB, optionC, optionD)
}

@Entity(tableName = "exam_attempts")
data class ExamAttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val modeKey: String,
    val commissionLabel: String,
    val totalQuestions: Int,
    val correctCount: Int,
    val wrongCount: Int,
    val skippedCount: Int,
    val netScore: Double,
    val durationSeconds: Int,
    val completedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "gd_checklist_items")
data class GdChecklistEntity(
    @PrimaryKey val id: String,
    val category: String, // "Group Discussion" or "Interview & Viva"
    val title: String,
    val guidance: String,
    val isChecked: Boolean = false,
    val sortOrder: Int = 0
)

class KrishiTypeConverters {
    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val sectionListType = Types.newParameterizedType(List::class.java, NoteSection::class.java)
    private val sectionAdapter = moshi.adapter<List<NoteSection>>(sectionListType)

    @TypeConverter
    fun fromNoteSections(sections: List<NoteSection>): String {
        return sectionAdapter.toJson(sections)
    }

    @TypeConverter
    fun toNoteSections(json: String): List<NoteSection> {
        return try {
            sectionAdapter.fromJson(json) ?: emptyList()
        } catch (_: Exception) {
            emptyList()
        }
    }
}
