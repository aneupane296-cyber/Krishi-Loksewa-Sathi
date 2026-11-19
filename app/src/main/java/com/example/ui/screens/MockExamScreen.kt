package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Gavel
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.RemoveCircleOutline
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.CommissionOption
import com.example.data.ExamAttemptEntity
import com.example.data.ExamMode
import com.example.data.McqQuestionEntity
import com.example.data.PostLevelOption
import com.example.ui.theme.CorrectGreen
import com.example.ui.theme.CorrectGreenBg
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.theme.JetBrainsMonoFamily
import com.example.ui.theme.OnWarmAmberContainer
import com.example.ui.theme.SkippedSlate
import com.example.ui.theme.SkippedSlateBg
import com.example.ui.theme.WarmAmberAccent
import com.example.ui.theme.WarmAmberContainer
import com.example.ui.theme.WrongCrimson
import com.example.ui.theme.WrongCrimsonBg
import com.example.viewmodel.ExamPhase
import java.util.Locale

@Composable
fun MockExamScreen(
    examPhase: ExamPhase,
    selectedMode: ExamMode,
    selectedCommission: CommissionOption,
    selectedPostLevel: PostLevelOption,
    selectedPracticeSubject: String,
    availableSubjects: List<String>,
    totalQuestionsAvailable: Int,
    practiceQuestionsCount: Int,
    recentAttempts: List<ExamAttemptEntity>,
    onSelectMode: (ExamMode) -> Unit,
    onSelectPracticeSubject: (String) -> Unit,
    onStartExam: (ExamMode) -> Unit,
    onSelectAnswer: (String, Int) -> Unit,
    onClearAnswer: (String) -> Unit,
    onSubmitExam: () -> Unit,
    onExitExam: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (examPhase) {
        is ExamPhase.PreExam -> PreExamInstructionsView(
            selectedMode = selectedMode,
            selectedCommission = selectedCommission,
            selectedPostLevel = selectedPostLevel,
            selectedPracticeSubject = selectedPracticeSubject,
            availableSubjects = availableSubjects,
            totalQuestionsAvailable = totalQuestionsAvailable,
            practiceQuestionsCount = practiceQuestionsCount,
            recentAttempts = recentAttempts,
            onSelectMode = onSelectMode,
            onSelectPracticeSubject = onSelectPracticeSubject,
            onStartExam = onStartExam,
            modifier = modifier
        )

        is ExamPhase.InProgress -> ActiveExamView(
            state = examPhase,
            onSelectAnswer = onSelectAnswer,
            onClearAnswer = onClearAnswer,
            onSubmitExam = onSubmitExam,
            onAbandonExam = onExitExam,
            modifier = modifier
        )

        is ExamPhase.Completed -> ExamScorecardView(
            state = examPhase,
            onRetakeExam = { onStartExam(examPhase.mode) },
            onBackToSetup = onExitExam,
            modifier = modifier
        )
    }
}

@Composable
private fun PreExamInstructionsView(
    selectedMode: ExamMode,
    selectedCommission: CommissionOption,
    selectedPostLevel: PostLevelOption,
    selectedPracticeSubject: String,
    availableSubjects: List<String>,
    totalQuestionsAvailable: Int,
    practiceQuestionsCount: Int,
    recentAttempts: List<ExamAttemptEntity>,
    onSelectMode: (ExamMode) -> Unit,
    onSelectPracticeSubject: (String) -> Unit,
    onStartExam: (ExamMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 680.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Mode Selection
            item {
                Text(
                    text = "Select Exam Mode",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    ExamModeOptionCard(
                        mode = ExamMode.PSC_FULL,
                        selected = selectedMode == ExamMode.PSC_FULL,
                        badge = "45:00 TIMED",
                        description = "Official Nepal PSC simulation across all $totalQuestionsAvailable bilingual EN/नेपाली MCQs with strict countdown timer and -20% negative marking.",
                        testTag = "exam_mode_psc_full",
                        onClick = { onSelectMode(ExamMode.PSC_FULL) }
                    )
                    ExamModeOptionCard(
                        mode = ExamMode.TOPIC_PRACTICE,
                        selected = selectedMode == ExamMode.TOPIC_PRACTICE,
                        badge = "UNTIMED • $practiceQuestionsCount Qs",
                        description = "Focused practice filtered by ${selectedCommission.shortName}, ${selectedPostLevel.shortName}, and subject below. Same exact -20% net-score analytics.",
                        testTag = "exam_mode_topic_practice",
                        onClick = { onSelectMode(ExamMode.TOPIC_PRACTICE) }
                    )
                }
            }

            // Topic Filter Chips when Topic Practice is active
            if (selectedMode == ExamMode.TOPIC_PRACTICE) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "Practice Subject Filter",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            availableSubjects.forEach { subject ->
                                val isSelected = subject == selectedPracticeSubject
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { onSelectPracticeSubject(subject) },
                                    label = {
                                        Text(
                                            text = if (subject == "ALL") "All Subjects" else subject,
                                            style = MaterialTheme.typography.labelMedium
                                        )
                                    },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    modifier = Modifier.testTag("practice_subject_${subject.lowercase().replace(" ", "_")}")
                                )
                            }
                        }
                    }
                }
            }

            // Official Nepal PSC Exam Rules Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmAmberContainer),
                    border = BorderStroke(1.dp, WarmAmberAccent.copy(alpha = 0.45f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Gavel,
                                contentDescription = null,
                                tint = WarmAmberAccent
                            )
                            Text(
                                text = "Nepal PSC Paper I Examination Rules",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = OnWarmAmberContainer
                            )
                        }

                        RuleRow(
                            icon = Icons.Outlined.WarningAmber,
                            text = "20% Negative Marking: Each wrong answer deducts 0.20 marks. Net Score = Correct - (Wrong × 0.20)."
                        )
                        RuleRow(
                            icon = Icons.Outlined.RemoveCircleOutline,
                            text = "Zero Deduction for Skipped Items: Leave uncertain questions blank (tap again to unselect) for 0 penalty."
                        )
                        RuleRow(
                            icon = Icons.Outlined.Calculate,
                            text = "Calculators & Electronic Aids Prohibited: Practice mental estimation as required in official Lok Sewa exam halls."
                        )
                    }
                }
            }

            // Start CTA Button
            item {
                Button(
                    onClick = { onStartExam(selectedMode) },
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(vertical = 15.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("start_mock_exam_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (selectedMode == ExamMode.PSC_FULL) {
                            "Start 45-Minute PSC Simulation"
                        } else {
                            "Start Topic Practice ($practiceQuestionsCount Questions)"
                        },
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }

            // Recent Attempts History
            if (recentAttempts.isNotEmpty()) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.History,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Recent Mock Attempts",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                itemsIndexed(recentAttempts.take(5)) { _, attempt ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.45f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text(
                                    text = if (attempt.modeKey == ExamMode.PSC_FULL.key) {
                                        "PSC 45-Min Simulation"
                                    } else {
                                        "Topic Practice (${attempt.commissionLabel})"
                                    },
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "✓ ${attempt.correctCount}  •  ✗ ${attempt.wrongCount}  •  – ${attempt.skippedCount} skipped",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = String.format(Locale.US, "%.2f / %d", attempt.netScore, attempt.totalQuestions),
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontFamily = JetBrainsMonoFamily,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ExamModeOptionCard(
    mode: ExamMode,
    selected: Boolean,
    badge: String,
    description: String,
    testTag: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = BorderStroke(
            width = if (selected) 2.dp else 1.dp,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag(testTag)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = mode.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Surface(
                    color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = badge,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontFamily = JetBrainsMonoFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun RuleRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = WarmAmberAccent,
            modifier = Modifier
                .padding(top = 2.dp)
                .size(16.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = OnWarmAmberContainer
        )
    }
}

@Composable
private fun ActiveExamView(
    state: ExamPhase.InProgress,
    onSelectAnswer: (String, Int) -> Unit,
    onClearAnswer: (String) -> Unit,
    onSubmitExam: () -> Unit,
    onAbandonExam: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showSubmitConfirm by remember { mutableStateOf(false) }
    var showExitConfirm by remember { mutableStateOf(false) }

    BackHandler { showExitConfirm = true }

    val answeredCount = state.selectedAnswers.size
    val totalCount = state.questions.size
    val progress = if (totalCount == 0) 0f else answeredCount.toFloat() / totalCount.toFloat()
    val displayTime = formatMmSs(if (state.isTimed) state.remainingSeconds else state.elapsedSeconds)
    val isLowTime = state.isTimed && state.remainingSeconds <= 300

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Sticky Countdown Bar
        Surface(
            color = if (isLowTime) WrongCrimsonBg else MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Timer,
                            contentDescription = "Exam timer",
                            tint = if (isLowTime) WrongCrimson else MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Column {
                            Text(
                                text = displayTime,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontFamily = JetBrainsMonoFamily,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = if (isLowTime) WrongCrimson else MaterialTheme.colorScheme.primary,
                                modifier = Modifier.testTag("exam_timer_text")
                            )
                            Text(
                                text = if (state.isTimed) "Remaining" else "Elapsed (Practice)",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "$answeredCount/$totalCount",
                            style = MaterialTheme.typography.labelLarge.copy(fontFamily = JetBrainsMonoFamily),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Button(
                            onClick = { showSubmitConfirm = true },
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                            modifier = Modifier.testTag("submit_exam_top_button")
                        ) {
                            Text("Submit", style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp),
                    color = WarmAmberAccent,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }

        // Scrollable MCQ List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .widthIn(max = 680.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            itemsIndexed(state.questions, key = { _, q -> q.id }) { index, question ->
                val selectedOption = state.selectedAnswers[question.id]
                ActiveMcqCard(
                    number = index + 1,
                    question = question,
                    selectedOption = selectedOption,
                    onSelectOption = { optIdx -> onSelectAnswer(question.id, optIdx) },
                    onClear = { onClearAnswer(question.id) }
                )
            }

            item {
                Button(
                    onClick = { showSubmitConfirm = true },
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(vertical = 15.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("submit_exam_bottom_button")
                ) {
                    Text(
                        text = "Submit Exam & Calculate Net Score",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }

    if (showSubmitConfirm) {
        val skipped = totalCount - answeredCount
        AlertDialog(
            onDismissRequest = { showSubmitConfirm = false },
            title = { Text("Submit Mock Exam?") },
            text = {
                Text(
                    "Answered: $answeredCount of $totalCount\nSkipped (0 deduction): $skipped\n\nWrong answers will incur -0.20 negative marking per Nepal PSC rules."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSubmitConfirm = false
                        onSubmitExam()
                    },
                    modifier = Modifier.testTag("confirm_submit_exam_button")
                ) {
                    Text("Submit Now")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSubmitConfirm = false }) {
                    Text("Review More")
                }
            }
        )
    }

    if (showExitConfirm) {
        AlertDialog(
            onDismissRequest = { showExitConfirm = false },
            title = { Text("Exit Active Exam?") },
            text = { Text("Your current exam attempt has not been submitted yet. Leave without saving?") },
            confirmButton = {
                Button(
                    onClick = {
                        showExitConfirm = false
                        onAbandonExam()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = WrongCrimson)
                ) {
                    Text("Exit Exam")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitConfirm = false }) {
                    Text("Continue Exam")
                }
            }
        )
    }
}

@Composable
private fun ActiveMcqCard(
    number: Int,
    question: McqQuestionEntity,
    selectedOption: Int?,
    onSelectOption: (Int) -> Unit,
    onClear: () -> Unit
) {
    val letters = listOf("A", "B", "C", "D")
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("mcq_card_${question.id}")
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Q$number",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontFamily = JetBrainsMonoFamily,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                    Text(
                        text = question.subjectTag,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (selectedOption != null) {
                    TextButton(
                        onClick = onClear,
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "Clear (Skip)",
                            style = MaterialTheme.typography.labelSmall,
                            color = SkippedSlate
                        )
                    }
                }
            }

            Text(
                text = question.question,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface
            )

            question.optionsList().forEachIndexed { idx, optionText ->
                val isSelected = selectedOption == idx
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.75f)
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                    },
                    border = BorderStroke(
                        width = if (isSelected) 1.6.dp else 1.dp,
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onSelectOption(idx) }
                        .testTag("mcq_${question.id}_opt_$idx")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(26.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surface
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = letters[idx],
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Text(
                            text = optionText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ExamScorecardView(
    state: ExamPhase.Completed,
    onRetakeExam: () -> Unit,
    onBackToSetup: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBackToSetup() }

    val penalty = state.wrongCount * 0.20

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 680.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Scorecard Hero Banner
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = ForestGreenPrimary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("exam_scorecard_summary")
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "PSC NEGATIVE MARKING SCORECARD",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = WarmAmberContainer
                        )
                        Text(
                            text = String.format(Locale.US, "%.2f", state.netScore),
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontFamily = JetBrainsMonoFamily,
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color.White,
                            modifier = Modifier.testTag("exam_net_score_value")
                        )
                        Text(
                            text = "Net Score = ${state.correctCount} − (${state.wrongCount} × 0.20)  •  Out of ${state.questions.size}",
                            style = MaterialTheme.typography.bodySmall.copy(fontFamily = JetBrainsMonoFamily),
                            color = Color.White.copy(alpha = 0.88f)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            ScoreBreakdownBox(
                                label = "Correct",
                                value = "+${state.correctCount}",
                                bg = CorrectGreenBg,
                                fg = CorrectGreen,
                                modifier = Modifier.weight(1f)
                            )
                            ScoreBreakdownBox(
                                label = "Wrong",
                                value = "${state.wrongCount}",
                                bg = WrongCrimsonBg,
                                fg = WrongCrimson,
                                modifier = Modifier.weight(1f)
                            )
                            ScoreBreakdownBox(
                                label = "Penalty",
                                value = String.format(Locale.US, "-%.2f", penalty),
                                bg = WarmAmberContainer,
                                fg = OnWarmAmberContainer,
                                modifier = Modifier.weight(1f)
                            )
                            ScoreBreakdownBox(
                                label = "Skipped",
                                value = "${state.skippedCount}",
                                bg = SkippedSlateBg,
                                fg = SkippedSlate,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = onRetakeExam,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = ForestGreenPrimary
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("retake_exam_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Refresh,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Retake", style = MaterialTheme.typography.labelLarge)
                            }
                            OutlinedButton(
                                onClick = onBackToSetup,
                                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.6f)),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("back_to_exam_setup_button")
                            ) {
                                Text("Change Mode", style = MaterialTheme.typography.labelLarge)
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Color-Coded Answer Review & Explanations",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            itemsIndexed(state.questions, key = { _, q -> q.id }) { index, question ->
                val userChoice = state.selectedAnswers[question.id]
                ReviewMcqCard(
                    number = index + 1,
                    question = question,
                    userChoice = userChoice
                )
            }
        }
    }
}

@Composable
private fun ScoreBreakdownBox(
    label: String,
    value: String,
    bg: Color,
    fg: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color = bg,
        contentColor = fg,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontFamily = JetBrainsMonoFamily,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
private fun ReviewMcqCard(
    number: Int,
    question: McqQuestionEntity,
    userChoice: Int?
) {
    val isCorrect = userChoice == question.correctIndex
    val isSkipped = userChoice == null
    val statusColor = when {
        isSkipped -> SkippedSlate
        isCorrect -> CorrectGreen
        else -> WrongCrimson
    }
    val statusBg = when {
        isSkipped -> SkippedSlateBg
        isCorrect -> CorrectGreenBg
        else -> WrongCrimsonBg
    }
    val statusText = when {
        isSkipped -> "Skipped (0.00)"
        isCorrect -> "Correct (+1.00)"
        else -> "Wrong (-0.20)"
    }
    val letters = listOf("A", "B", "C", "D")

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.3.dp, statusColor.copy(alpha = 0.55f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Q$number • ${question.subjectTag}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Surface(
                    color = statusBg,
                    contentColor = statusColor,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = when {
                                isSkipped -> Icons.Outlined.RemoveCircleOutline
                                isCorrect -> Icons.Filled.CheckCircle
                                else -> Icons.Filled.Cancel
                            },
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = statusText,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            Text(
                text = question.question,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface
            )

            question.optionsList().forEachIndexed { idx, opt ->
                val isRightOption = idx == question.correctIndex
                val isWrongUserOption = userChoice == idx && !isRightOption
                val bg = when {
                    isRightOption -> CorrectGreenBg
                    isWrongUserOption -> WrongCrimsonBg
                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                }
                val fg = when {
                    isRightOption -> CorrectGreen
                    isWrongUserOption -> WrongCrimson
                    else -> MaterialTheme.colorScheme.onSurface
                }

                Surface(
                    color = bg,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "${letters[idx]}.",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            color = fg
                        )
                        Text(
                            text = opt,
                            style = MaterialTheme.typography.bodySmall,
                            color = fg,
                            modifier = Modifier.weight(1f)
                        )
                        if (isRightOption) {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = "Correct option",
                                tint = CorrectGreen,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            Text(
                text = "Explanation: ${question.explanation}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun formatMmSs(totalSeconds: Int): String {
    val safe = totalSeconds.coerceAtLeast(0)
    val mins = safe / 60
    val secs = safe % 60
    return String.format(Locale.US, "%02d:%02d", mins, secs)
}
