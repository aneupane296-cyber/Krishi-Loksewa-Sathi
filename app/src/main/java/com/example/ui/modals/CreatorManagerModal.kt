package com.example.ui.modals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.data.CommissionOption
import com.example.data.NotePostScope
import com.example.ui.theme.OnWarmAmberContainer
import com.example.ui.theme.WarmAmberAccent
import com.example.ui.theme.WarmAmberContainer

@Composable
fun CreatorManagerModal(
    totalNotesCount: Int,
    totalMcqsCount: Int,
    onAddStudyNote: (
        title: String,
        commissionKey: String,
        postLevelScope: String,
        paper: String,
        tag: String,
        overview: String,
        bulletsRaw: String,
        quickFact: String
    ) -> Boolean,
    onAddMcq: (
        question: String,
        optionA: String,
        optionB: String,
        optionC: String,
        optionD: String,
        correctIndex: Int,
        explanation: String,
        commissionKey: String,
        postLevelScope: String,
        subjectTag: String
    ) -> Boolean,
    onExportShareJson: (android.content.Context) -> Unit,
    onCopyJson: (android.content.Context) -> Unit,
    onDismiss: () -> Unit
) {
    var activeTabIndex by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .heightIn(max = 700.dp)
                .testTag("creator_content_manager_modal")
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 18.dp, end = 8.dp, top = 12.dp, bottom = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Creator Content Manager",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Apar Neupane • $totalNotesCount Notes, $totalMcqsCount MCQs stored locally",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_creator_modal_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close Creator Content Manager"
                        )
                    }
                }

                // Tabs
                TabRow(
                    selectedTabIndex = activeTabIndex,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    Tab(
                        selected = activeTabIndex == 0,
                        onClick = { activeTabIndex = 0 },
                        text = { Text("+ Add Study Note", style = MaterialTheme.typography.labelLarge) },
                        modifier = Modifier.testTag("creator_tab_add_note")
                    )
                    Tab(
                        selected = activeTabIndex == 1,
                        onClick = { activeTabIndex = 1 },
                        text = { Text("+ Add MCQ", style = MaterialTheme.typography.labelLarge) },
                        modifier = Modifier.testTag("creator_tab_add_mcq")
                    )
                }

                // Scrollable Form Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (activeTabIndex == 0) {
                        AddStudyNoteForm(onSubmit = onAddStudyNote)
                    } else {
                        AddMcqForm(onSubmit = onAddMcq)
                    }
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                // Global Export Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { onExportShareJson(context) },
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("export_share_json_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = stringResource(R.string.export_json_button),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    OutlinedButton(
                        onClick = { onCopyJson(context) },
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
                        modifier = Modifier.testTag("copy_json_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ContentCopy,
                            contentDescription = "Copy JSON to clipboard",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AddStudyNoteForm(
    onSubmit: (
        title: String,
        commissionKey: String,
        postLevelScope: String,
        paper: String,
        tag: String,
        overview: String,
        bulletsRaw: String,
        quickFact: String
    ) -> Boolean
) {
    var title by remember { mutableStateOf("") }
    var commission by remember { mutableStateOf(CommissionOption.ALL) }
    var postScope by remember { mutableStateOf(NotePostScope.ALL) }
    var paper by remember { mutableStateOf("Paper I & II") }
    var tag by remember { mutableStateOf("Agronomy & Seed Tech") }
    var overview by remember { mutableStateOf("") }
    var bulletsRaw by remember { mutableStateOf("") }
    var quickFact by remember { mutableStateOf("") }

    // Mandatory Copyright-Safety Reminder
    Surface(
        color = WarmAmberContainer,
        border = BorderStroke(1.dp, WarmAmberAccent.copy(alpha = 0.5f)),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Outlined.Shield,
                contentDescription = null,
                tint = WarmAmberAccent,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = stringResource(R.string.creator_copyright_reminder),
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                color = OnWarmAmberContainer
            )
        }
    }

    OutlinedTextField(
        value = title,
        onValueChange = { title = it },
        label = { Text("Note Title *") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("creator_note_title_input")
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CompactDropdownSelector(
            label = "Commission / Province",
            selectedLabel = commission.shortName,
            options = CommissionOption.entries.map { it.displayName },
            onSelect = { idx -> commission = CommissionOption.entries[idx] },
            modifier = Modifier.weight(1f)
        )
        CompactDropdownSelector(
            label = "Post Level",
            selectedLabel = postScope.displayName,
            options = NotePostScope.entries.map { it.displayName },
            onSelect = { idx -> postScope = NotePostScope.entries[idx] },
            modifier = Modifier.weight(1f)
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = paper,
            onValueChange = { paper = it },
            label = { Text("Paper") },
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = tag,
            onValueChange = { tag = it },
            label = { Text("Tag / Topic") },
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
    }

    OutlinedTextField(
        value = overview,
        onValueChange = { overview = it },
        label = { Text("Overview Summary *") },
        minLines = 2,
        maxLines = 4,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("creator_note_overview_input")
    )

    OutlinedTextField(
        value = bulletsRaw,
        onValueChange = { bulletsRaw = it },
        label = { Text("Bullet Points (one per line)") },
        minLines = 3,
        maxLines = 6,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("creator_note_bullets_input")
    )

    OutlinedTextField(
        value = quickFact,
        onValueChange = { quickFact = it },
        label = { Text("Lok Sewa Quick Fact") },
        minLines = 2,
        maxLines = 3,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("creator_note_quickfact_input")
    )

    Spacer(modifier = Modifier.height(4.dp))

    Button(
        onClick = {
            val saved = onSubmit(
                title,
                commission.key,
                postScope.key,
                paper,
                tag,
                overview,
                bulletsRaw,
                quickFact
            )
            if (saved) {
                title = ""
                overview = ""
                bulletsRaw = ""
                quickFact = ""
            }
        },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("creator_save_note_button")
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text("Save Study Note Locally")
    }
}

@Composable
private fun AddMcqForm(
    onSubmit: (
        question: String,
        optionA: String,
        optionB: String,
        optionC: String,
        optionD: String,
        correctIndex: Int,
        explanation: String,
        commissionKey: String,
        postLevelScope: String,
        subjectTag: String
    ) -> Boolean
) {
    var question by remember { mutableStateOf("") }
    var optionA by remember { mutableStateOf("") }
    var optionB by remember { mutableStateOf("") }
    var optionC by remember { mutableStateOf("") }
    var optionD by remember { mutableStateOf("") }
    var correctIndex by remember { mutableIntStateOf(0) }
    var explanation by remember { mutableStateOf("") }
    var commission by remember { mutableStateOf(CommissionOption.ALL) }
    var postScope by remember { mutableStateOf(NotePostScope.ALL) }
    var subjectTag by remember { mutableStateOf("General Agriculture") }

    OutlinedTextField(
        value = question,
        onValueChange = { question = it },
        label = { Text("MCQ Question (English or नेपाली) *") },
        minLines = 2,
        maxLines = 4,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("creator_mcq_question_input")
    )

    OutlinedTextField(
        value = optionA,
        onValueChange = { optionA = it },
        label = { Text("Option A *") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    OutlinedTextField(
        value = optionB,
        onValueChange = { optionB = it },
        label = { Text("Option B *") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    OutlinedTextField(
        value = optionC,
        onValueChange = { optionC = it },
        label = { Text("Option C *") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    OutlinedTextField(
        value = optionD,
        onValueChange = { optionD = it },
        label = { Text("Option D *") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = "Correct Option",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("A", "B", "C", "D").forEachIndexed { idx, letter ->
                FilterChip(
                    selected = correctIndex == idx,
                    onClick = { correctIndex = idx },
                    label = { Text("Option $letter", style = MaterialTheme.typography.labelMedium) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CompactDropdownSelector(
            label = "Commission",
            selectedLabel = commission.shortName,
            options = CommissionOption.entries.map { it.displayName },
            onSelect = { idx -> commission = CommissionOption.entries[idx] },
            modifier = Modifier.weight(1f)
        )
        CompactDropdownSelector(
            label = "Post Level",
            selectedLabel = postScope.displayName,
            options = NotePostScope.entries.map { it.displayName },
            onSelect = { idx -> postScope = NotePostScope.entries[idx] },
            modifier = Modifier.weight(1f)
        )
    }

    OutlinedTextField(
        value = subjectTag,
        onValueChange = { subjectTag = it },
        label = { Text("Subject Tag") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = explanation,
        onValueChange = { explanation = it },
        label = { Text("Answer Explanation") },
        minLines = 2,
        maxLines = 4,
        modifier = Modifier.fillMaxWidth()
    )

    Button(
        onClick = {
            val saved = onSubmit(
                question,
                optionA,
                optionB,
                optionC,
                optionD,
                correctIndex,
                explanation,
                commission.key,
                postScope.key,
                subjectTag
            )
            if (saved) {
                question = ""
                optionA = ""
                optionB = ""
                optionC = ""
                optionD = ""
                correctIndex = 0
                explanation = ""
            }
        },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("creator_save_mcq_button")
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text("Save MCQ Locally")
    }
}

@Composable
private fun CompactDropdownSelector(
    label: String,
    selectedLabel: String,
    options: List<String>,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable { expanded = true }
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = selectedLabel,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1
                    )
                }
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = "Select $label",
                    modifier = Modifier.size(18.dp)
                )
            }
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEachIndexed { idx, item ->
                DropdownMenuItem(
                    text = { Text(item, style = MaterialTheme.typography.bodySmall) },
                    onClick = {
                        onSelect(idx)
                        expanded = false
                    }
                )
            }
        }
    }
}
