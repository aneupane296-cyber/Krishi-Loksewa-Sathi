package com.example.data

import com.squareup.moshi.JsonClass

enum class NavTab(val route: String, val label: String) {
    HOME("home", "Home"),
    NOTES("notes", "Notes"),
    EXAM("mock_exam", "Mock Exam"),
    SKILL_GD("skill_gd", "Skill & GD")
}

enum class CommissionOption(val key: String, val displayName: String, val shortName: String) {
    ALL("ALL", "All Federal & Provinces", "All Nepal"),
    FEDERAL("FEDERAL", "Federal PSC (संघीय लोक सेवा)", "Federal PSC"),
    KOSHI("KOSHI", "Koshi Province", "Koshi"),
    MADHESH("MADHESH", "Madhesh Province", "Madhesh"),
    BAGMATI("BAGMATI", "Bagmati Province", "Bagmati"),
    GANDAKI("GANDAKI", "Gandaki Province", "Gandaki"),
    LUMBINI("LUMBINI", "Lumbini Province", "Lumbini"),
    KARNALI("KARNALI", "Karnali Province", "Karnali"),
    SUDURPASHCHIM("SUDURPASHCHIM", "Sudurpashchim Province", "Sudurpashchim");

    companion object {
        fun fromKey(key: String): CommissionOption =
            entries.firstOrNull { it.key == key } ?: ALL
    }
}

enum class PostLevelOption(val key: String, val displayName: String, val shortName: String) {
    OFFICER("OFFICER", "Officer (7th / 8th Level)", "Officer 7/8"),
    JT_JTA("JT_JTA", "JT / JTA (4th / 5th Level)", "JT / JTA 4/5");

    companion object {
        fun fromKey(key: String): PostLevelOption =
            entries.firstOrNull { it.key == key } ?: OFFICER
    }
}

enum class NotePostScope(val key: String, val displayName: String) {
    ALL("ALL", "Both Officer & JT/JTA"),
    OFFICER("OFFICER", "Officer (7th / 8th Level)"),
    JT_JTA("JT_JTA", "JT / JTA (4th / 5th Level)");

    companion object {
        fun fromKey(key: String): NotePostScope =
            entries.firstOrNull { it.key == key } ?: ALL
    }
}

enum class ExamMode(val key: String, val title: String) {
    PSC_FULL("PSC_FULL", "45-Min PSC Full Simulation"),
    TOPIC_PRACTICE("TOPIC_PRACTICE", "Topic & Jurisdiction Practice")
}

enum class TypingLanguage(val label: String, val badge: String) {
    ENGLISH("English", "EN"),
    NEPALI("नेपाली (Devanagari)", "ने")
}

enum class PolicySection(val hash: String, val title: String) {
    ABOUT("#about", "About"),
    CONTACT("#contact", "Contact"),
    PRIVACY("#privacy", "Privacy"),
    TERMS("#terms", "Terms")
}

@JsonClass(generateAdapter = true)
data class NoteSection(
    val subheading: String,
    val bullets: List<String>
)

data class TypingPassage(
    val id: String,
    val title: String,
    val language: TypingLanguage,
    val content: String
)
