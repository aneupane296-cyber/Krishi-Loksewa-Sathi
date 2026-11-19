package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.CommissionOption
import com.example.data.StudyNoteEntity
import com.example.ui.theme.CorrectGreen
import com.example.ui.theme.CorrectGreenBg
import com.example.ui.theme.OnWarmAmberContainer
import com.example.ui.theme.WarmAmberAccent
import com.example.ui.theme.WarmAmberContainer

@Composable
fun NotesScreen(
    notes: List<StudyNoteEntity>,
    searchQuery: String,
    bookmarksOnly: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onToggleBookmarksOnly: () -> Unit,
    onResetFilters: () -> Unit,
    onToggleCompleted: (StudyNoteEntity) -> Unit,
    onToggleBookmarked: (StudyNoteEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val expandedState = remember { mutableStateMapOf<String, Boolean>() }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 680.dp)
        ) {
            // Search Bar & Bookmarks Filter Row
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = {
                        Text(
                            text = "Search acts, crops, soil, pests, provinces...",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search notes"
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = { onSearchQueryChange("") },
                                modifier = Modifier.testTag("clear_search_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Clear,
                                    contentDescription = "Clear search"
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("notes_search_input")
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilterChip(
                        selected = bookmarksOnly,
                        onClick = onToggleBookmarksOnly,
                        label = {
                            Text(
                                text = "★ Bookmarks",
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = if (bookmarksOnly) Icons.Filled.Star else Icons.Outlined.StarOutline,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = WarmAmberContainer,
                            selectedLabelColor = OnWarmAmberContainer,
                            selectedLeadingIconColor = WarmAmberAccent
                        ),
                        modifier = Modifier.testTag("notes_bookmarks_filter")
                    )

                    Text(
                        text = "${notes.size} module${if (notes.size == 1) "" else "s"} matched",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (notes.isEmpty()) {
                EmptyNotesState(
                    bookmarksOnly = bookmarksOnly,
                    onResetFilters = onResetFilters,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(notes, key = { it.id }) { note ->
                        val isExpanded = expandedState[note.id] ?: false
                        CollapsibleStudyNoteCard(
                            note = note,
                            isExpanded = isExpanded,
                            onToggleExpand = { expandedState[note.id] = !isExpanded },
                            onToggleCompleted = { onToggleCompleted(note) },
                            onToggleBookmarked = { onToggleBookmarked(note) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CollapsibleStudyNoteCard(
    note: StudyNoteEntity,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    onToggleCompleted: () -> Unit,
    onToggleBookmarked: () -> Unit
) {
    val borderColor = when {
        note.isCompleted -> CorrectGreen.copy(alpha = 0.55f)
        isExpanded -> MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(if (isExpanded || note.isCompleted) 1.4.dp else 1.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isExpanded) 2.dp else 0.5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("note_card_${note.id}")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Card Header (Always visible & tappable to expand/collapse)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onToggleExpand)
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        NoteMetaPill(
                            text = note.tag,
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        NoteMetaPill(
                            text = note.paper,
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (note.commissionKey != "ALL") {
                            NoteMetaPill(
                                text = CommissionOption.fromKey(note.commissionKey).shortName,
                                containerColor = WarmAmberContainer,
                                contentColor = OnWarmAmberContainer
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (note.isCompleted) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = "Completed",
                                tint = CorrectGreen,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        if (note.isBookmarked) {
                            Icon(
                                imageVector = Icons.Filled.Bookmark,
                                contentDescription = "Bookmarked",
                                tint = WarmAmberAccent,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        Icon(
                            imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = if (isExpanded) "Collapse note" else "Expand note",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (!isExpanded) {
                    Text(
                        text = note.overview,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )
                }
            }

            // Expanded Body
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 14.dp, end = 14.dp, bottom = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                    // Overview Summary
                    Text(
                        text = note.overview,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // Structured Subheadings with Bullet Points
                    note.sections.forEach { section ->
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = section.subheading,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                            section.bullets.forEach { bullet ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = "•",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                        color = WarmAmberAccent
                                    )
                                    Text(
                                        text = bullet,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }

                    // Lok Sewa Quick Fact Callout Box
                    if (note.quickFact.isNotBlank()) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = WarmAmberContainer,
                            border = BorderStroke(1.dp, WarmAmberAccent.copy(alpha = 0.45f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Lightbulb,
                                    contentDescription = "Lok Sewa Quick Fact",
                                    tint = WarmAmberAccent,
                                    modifier = Modifier.size(20.dp)
                                )
                                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Text(
                                        text = "Lok Sewa Quick Fact",
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                        color = OnWarmAmberContainer
                                    )
                                    Text(
                                        text = note.quickFact,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = OnWarmAmberContainer
                                    )
                                }
                            }
                        }
                    }

                    // Action Buttons: Mark Completed & Bookmark
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = onToggleCompleted,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (note.isCompleted) CorrectGreenBg else MaterialTheme.colorScheme.primary,
                                contentColor = if (note.isCompleted) CorrectGreen else MaterialTheme.colorScheme.onPrimary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("note_complete_btn_${note.id}")
                        ) {
                            Icon(
                                imageVector = if (note.isCompleted) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (note.isCompleted) "Completed" else "Mark Completed",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }

                        OutlinedButton(
                            onClick = onToggleBookmarked,
                            border = BorderStroke(
                                1.dp,
                                if (note.isBookmarked) WarmAmberAccent else MaterialTheme.colorScheme.outline
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("note_bookmark_btn_${note.id}")
                        ) {
                            Icon(
                                imageVector = if (note.isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                                contentDescription = if (note.isBookmarked) "Remove bookmark" else "Bookmark note",
                                tint = if (note.isBookmarked) WarmAmberAccent else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (note.isBookmarked) "Saved" else "Bookmark",
                                style = MaterialTheme.typography.labelLarge,
                                color = if (note.isBookmarked) WarmAmberAccent else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NoteMetaPill(
    text: String,
    containerColor: androidx.compose.ui.graphics.Color,
    contentColor: androidx.compose.ui.graphics.Color
) {
    Surface(
        color = containerColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(7.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
        )
    }
}

@Composable
private fun EmptyNotesState(
    bookmarksOnly: Boolean,
    onResetFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.MenuBook,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
            }
            Text(
                text = if (bookmarksOnly) "No Bookmarked Notes Yet" else "No Matching Study Modules",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = if (bookmarksOnly) {
                    "Tap the Bookmark button inside any study card to pin quick revision notes here."
                } else {
                    "Try clearing your search term or switching the Commission/Province filter."
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedButton(
                onClick = onResetFilters,
                modifier = Modifier.testTag("reset_notes_filters_button")
            ) {
                Text("Reset Filters")
            }
        }
    }
}
