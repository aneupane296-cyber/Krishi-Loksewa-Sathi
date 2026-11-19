package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        StudyNoteEntity::class,
        McqQuestionEntity::class,
        ExamAttemptEntity::class,
        GdChecklistEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(KrishiTypeConverters::class)
abstract class KrishiDatabase : RoomDatabase() {
    abstract fun studyNoteDao(): StudyNoteDao
    abstract fun mcqQuestionDao(): McqQuestionDao
    abstract fun examAttemptDao(): ExamAttemptDao
    abstract fun gdChecklistDao(): GdChecklistDao

    companion object {
        @Volatile
        private var INSTANCE: KrishiDatabase? = null

        fun getInstance(context: Context): KrishiDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KrishiDatabase::class.java,
                    "nepal_krishi_lok_sewa_prep.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
