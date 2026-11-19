package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.data.GdChecklistEntity
import com.example.data.TypingLanguage
import com.example.data.TypingPassage
import com.example.ui.theme.CorrectGreen
import com.example.ui.theme.CorrectGreenBg
import com.example.ui.theme.JetBrainsMonoFamily
import com.example.ui.theme.OnWarmAmberContainer
import com.example.ui.theme.WarmAmberAccent
import com.example.ui.theme.WarmAmberContainer
import com.example.ui.theme.WrongCrimson
import com.example.ui.theme.WrongCrimsonBg
import com.example.viewmodel.TypingUiState
import java.util.Locale

@Composable
fun SkillGdScreen(
    typingState: TypingUiState,
    availablePassages: List<TypingPassage>,
    currentPassage: TypingPassage,
    checklist: List<GdChecklistEntity>,
    onSelectLanguage: (TypingLanguage) -> Unit,
    onSelectPassage: (Int) -> Unit,
    onStartTyping: () -> Unit,
    onTypingInputChange: (String) -> Unit,
    onFinishTyping: () -> Unit,
    onResetTyping: () -> Unit,
    onToggleChecklistItem: (GdChecklistEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val completedChecklist = checklist.count { it.isChecked }
    val checklistFraction = if (checklist.isEmpty()) 0f else completedChecklist.toFloat() / checklist.size.toFloat()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 680.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. PSC Typing Speed Simulator Card
            item {
                PscTypingSimulatorCard(
                    state = typingState,
                    availablePassages = availablePassages,
                    currentPassage = currentPassage,
                    onSelectLanguage = onSelectLanguage,
                    onSelectPassage = onSelectPassage,
                    onStartTyping = onStartTyping,
                    onTypingInputChange = onTypingInputChange,
                    onFinishTyping = onFinishTyping,
                    onResetTyping = onResetTyping
                )
            }

            // 2. GD & Interview Checklist Header
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)),
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Groups,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "GD & Interview Readiness Checklist",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Text(
                                text = "$completedChecklist/${checklist.size}",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontFamily = JetBrainsMonoFamily,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        LinearProgressIndicator(
                            progress = { checklistFraction },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(999.dp)),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.surface,
                            strokeCap = StrokeCap.Round
                        )
                    }
                }
            }

            items(checklist, key = { it.id }) { item ->
                GdChecklistItemCard(
                    item = item,
                    onToggle = { onToggleChecklistItem(item) }
                )
            }
        }
    }
}

@Composable
private fun PscTypingSimulatorCard(
    state: TypingUiState,
    availablePassages: List<TypingPassage>,
    currentPassage: TypingPassage,
    onSelectLanguage: (TypingLanguage) -> Unit,
    onSelectPassage: (Int) -> Unit,
    onStartTyping: () -> Unit,
    onTypingInputChange: (String) -> Unit,
    onFinishTyping: () -> Unit,
    onResetTyping: () -> Unit
) {
    val timerFormatted = remember(state.remainingSeconds) {
        val m = state.remainingSeconds / 60
        val s = state.remainingSeconds % 60
        String.format(Locale.US, "%02d:%02d", m, s)
    }

    val highlightedPassage = remember(currentPassage.content, state.typedText) {
        val refWords = currentPassage.content.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }
        val typedWords = state.typedText.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }
        buildAnnotatedString {
            refWords.forEachIndexed { idx, word ->
                when {
                    idx < typedWords.size -> {
                        val matched = typedWords[idx] == word
                        withStyle(
                            SpanStyle(
                                color = if (matched) CorrectGreen else WrongCrimson,
                                background = if (matched) CorrectGreenBg else WrongCrimsonBg,
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append(word)
                        }
                    }

                    idx == typedWords.size -> {
                        withStyle(
                            SpanStyle(
                                color = OnWarmAmberContainer,
                                background = WarmAmberContainer,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(word)
                        }
                    }

                    else -> append(word)
                }
                if (idx < refWords.lastIndex) append(" ")
            }
        }
    }

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.55f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("psc_typing_simulator_card")
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header & Countdown Timer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Keyboard,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Column {
                        Text(
                            text = "PSC Typing Speed Simulator",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "5-Min Formula: (Total − Wrong) ÷ 5",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Surface(
                    color = if (state.remainingSeconds <= 60) WrongCrimsonBg else MaterialTheme.colorScheme.primaryContainer,
                    contentColor = if (state.remainingSeconds <= 60) WrongCrimson else MaterialTheme.colorScheme.onPrimaryContainer,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Timer,
                            contentDescription = "Typing countdown timer",
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = timerFormatted,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontFamily = JetBrainsMonoFamily,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.testTag("typing_timer_text")
                        )
                    }
                }
            }

            // Language Switcher: English vs Devanagari (Nepali Unicode)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TypingLanguage.entries.forEach { lang ->
                    val selected = state.language == lang
                    FilterChip(
                        selected = selected,
                        onClick = { onSelectLanguage(lang) },
                        label = {
                            Text(
                                text = lang.label,
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("typing_lang_${lang.name.lowercase()}")
                    )
                }
            }

            // Passage Picker Row
            if (availablePassages.size > 1) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    availablePassages.forEachIndexed { idx, passage ->
                        FilterChip(
                            selected = idx == state.passageIndex,
                            onClick = { onSelectPassage(idx) },
                            label = {
                                Text(
                                    text = "Passage ${idx + 1}: ${passage.title.substringBefore("(").trim()}",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        )
                    }
                }
            }

            // Reference Passage Box
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = highlightedPassage,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(12.dp)
                )
            }

            // Typing Input Field
            OutlinedTextField(
                value = state.typedText,
                onValueChange = onTypingInputChange,
                enabled = !state.isFinished,
                placeholder = {
                    Text(
                        text = if (state.language == TypingLanguage.ENGLISH) {
                            "Start typing the passage above — 05:00 timer begins automatically..."
                        } else {
                            "माथिको अनुच्छेद नेपाली युनिकोडमा टाइप गर्न सुरु गर्नुहोस्..."
                        },
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                minLines = 3,
                maxLines = 5,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("typing_input_field")
            )

            // Live PSC Formula Metrics Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TypingMetricChip(
                    label = "Net WPM",
                    value = String.format(Locale.US, "%.1f", state.netWpm),
                    highlighted = true,
                    modifier = Modifier
                        .weight(1.2f)
                        .testTag("typing_net_wpm_value")
                )
                TypingMetricChip(
                    label = "Typed",
                    value = "${state.totalWordsTyped}",
                    highlighted = false,
                    modifier = Modifier.weight(0.9f)
                )
                TypingMetricChip(
                    label = "Wrong",
                    value = "${state.wrongWords}",
                    highlighted = false,
                    isError = state.wrongWords > 0,
                    modifier = Modifier.weight(0.9f)
                )
            }

            // Controls: Start / Finish / Reset
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (!state.isRunning && !state.isFinished) {
                    Button(
                        onClick = onStartTyping,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("typing_start_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Start 5-Min Test")
                    }
                } else if (state.isRunning) {
                    Button(
                        onClick = onFinishTyping,
                        colors = ButtonDefaults.buttonColors(containerColor = WarmAmberAccent),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("typing_finish_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Flag,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Finish & Score")
                    }
                }

                OutlinedButton(
                    onClick = onResetTyping,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("typing_reset_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Reset typing test",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Reset")
                }
            }

            if (state.isFinished) {
                Surface(
                    color = CorrectGreenBg,
                    contentColor = CorrectGreen,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = String.format(
                            Locale.US,
                            "Final PSC Result: %.1f Net WPM  •  Formula: max(0, (%d − %d) / 5)",
                            state.netWpm,
                            state.totalWordsTyped,
                            state.wrongWords
                        ),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontFamily = JetBrainsMonoFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun TypingMetricChip(
    label: String,
    value: String,
    highlighted: Boolean,
    isError: Boolean = false,
    modifier: Modifier = Modifier
) {
    val bg = when {
        highlighted -> WarmAmberContainer
        isError -> WrongCrimsonBg
        else -> MaterialTheme.colorScheme.surfaceVariant
    }
    val fg = when {
        highlighted -> OnWarmAmberContainer
        isError -> WrongCrimson
        else -> MaterialTheme.colorScheme.onSurface
    }
    Surface(
        color = bg,
        contentColor = fg,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
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
private fun GdChecklistItemCard(
    item: GdChecklistEntity,
    onToggle: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (item.isChecked) {
                CorrectGreenBg.copy(alpha = 0.45f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = BorderStroke(
            1.dp,
            if (item.isChecked) CorrectGreen.copy(alpha = 0.45f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.45f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onToggle)
            .testTag("gd_checklist_${item.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Checkbox(
                checked = item.isChecked,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(checkedColor = CorrectGreen)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = item.category,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = WarmAmberAccent
                )
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = item.guidance,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
