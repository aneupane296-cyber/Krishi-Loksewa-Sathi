package com.example.data

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@JsonClass(generateAdapter = true)
data class StudyDataExportBundle(
    val appTitle: String = "Nepal Krishi Lok Sewa Prep",
    val creator: String = "Apar Neupane",
    val exportedAtIso: String,
    val totalNotes: Int,
    val totalMcqs: Int,
    val studyNotes: List<StudyNoteEntity>,
    val mcqQuestions: List<McqQuestionEntity>
)

object JsonExportManager {

    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val bundleAdapter = moshi
        .adapter(StudyDataExportBundle::class.java)
        .indent("  ")

    fun buildJson(
        notes: List<StudyNoteEntity>,
        mcqs: List<McqQuestionEntity>
    ): String {
        val timestamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).format(Date())
        val bundle = StudyDataExportBundle(
            exportedAtIso = timestamp,
            totalNotes = notes.size,
            totalMcqs = mcqs.size,
            studyNotes = notes,
            mcqQuestions = mcqs
        )
        return bundleAdapter.toJson(bundle)
    }

    fun shareJsonFile(
        context: Context,
        notes: List<StudyNoteEntity>,
        mcqs: List<McqQuestionEntity>
    ): Result<String> {
        return runCatching {
            val json = buildJson(notes, mcqs)
            val exportDir = File(context.cacheDir, "exports").apply { mkdirs() }
            val exportFile = File(exportDir, "studyData.json")
            exportFile.writeText(json, Charsets.UTF_8)

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                exportFile
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "application/json"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_SUBJECT, "Nepal Krishi Lok Sewa Prep — studyData.json")
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Updated studyData.json exported from Nepal Krishi Lok Sewa Prep (${notes.size} notes, ${mcqs.size} MCQs)."
                )
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            val chooser = Intent.createChooser(shareIntent, "Export studyData.json").apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(chooser)
            json
        }
    }
}
