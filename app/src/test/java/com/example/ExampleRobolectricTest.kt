package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.JsonExportManager
import com.example.data.SeedExamAndSkillData
import com.example.data.SeedNotesData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `app name matches Nepal Krishi Lok Sewa Prep`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Nepal Krishi Lok Sewa Prep", appName)
    }

    @Test
    fun `seed data includes all provinces and exports valid json`() {
        val notes = SeedNotesData.initialNotes()
        val mcqs = SeedExamAndSkillData.initialMcqs()

        assertTrue("Expected at least 15 study notes", notes.size >= 15)
        assertTrue("Expected at least 10 MCQs", mcqs.size >= 10)

        val provinces = setOf("KOSHI", "MADHESH", "BAGMATI", "GANDAKI", "LUMBINI", "KARNALI", "SUDURPASHCHIM")
        val coveredProvinces = notes.map { it.commissionKey }.toSet()
        assertTrue("All 7 provinces should have flagship notes", coveredProvinces.containsAll(provinces))

        val json = JsonExportManager.buildJson(notes, mcqs)
        assertTrue(json.contains("Apar Neupane"))
        assertTrue(json.contains("Nepal Krishi Lok Sewa Prep"))
    }
}
