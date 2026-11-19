package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.CommissionOption
import com.example.data.NavTab
import com.example.data.PolicySection
import com.example.data.PostLevelOption
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.theme.JetBrainsMonoFamily
import com.example.ui.theme.OnWarmAmberContainer
import com.example.ui.theme.WarmAmberAccent
import com.example.ui.theme.WarmAmberBright
import com.example.ui.theme.WarmAmberContainer
import com.example.viewmodel.StudyProgressSummary
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    progress: StudyProgressSummary,
    selectedCommission: CommissionOption,
    selectedPostLevel: PostLevelOption,
    onNavigateTab: (NavTab) -> Unit,
    onOpenPolicy: (PolicySection) -> Unit,
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
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Editorial Hero Banner with Study Progress Tracker
            item {
                HeroProgressCard(
                    progress = progress,
                    selectedCommission = selectedCommission,
                    selectedPostLevel = selectedPostLevel,
                    onContinueStudy = { onNavigateTab(NavTab.NOTES) }
                )
            }

            // 2. Quick-Start Navigation Cards
            item {
                SectionHeading(
                    title = "Quick Start Preparation",
                    subtitle = "Jump directly into offline study modules, negative-marking mocks, or skill drills"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    QuickStartTile(
                        title = "Detailed Syllabus Notes",
                        subtitle = "${progress.totalNotes} original modules covering Acts, ADS, Agronomy, Soil, Horticulture, IPM & all 7 Provinces",
                        icon = Icons.Outlined.MenuBook,
                        accentColor = MaterialTheme.colorScheme.primary,
                        testTag = "quick_start_notes",
                        onClick = { onNavigateTab(NavTab.NOTES) }
                    )
                    QuickStartTile(
                        title = "Mock Exam (-20% Negative Marking)",
                        subtitle = "45-Min PSC Full Simulation & Topic Practice (${progress.totalMcqs} EN/नेपाली MCQs)",
                        icon = Icons.Outlined.Assignment,
                        accentColor = WarmAmberAccent,
                        testTag = "quick_start_exam",
                        onClick = { onNavigateTab(NavTab.EXAM) }
                    )
                    QuickStartTile(
                        title = "PSC Typing Simulator & GD Viva",
                        subtitle = "5-minute English & Devanagari Net WPM drill + GD/Interview checklist",
                        icon = Icons.Outlined.Keyboard,
                        accentColor = MaterialTheme.colorScheme.secondary,
                        testTag = "quick_start_skill",
                        onClick = { onNavigateTab(NavTab.SKILL_GD) }
                    )
                }
            }

            // 3. Nepal PSC Exam Scheme Overview Cards
            item {
                SectionHeading(
                    title = "Nepal PSC Agriculture Exam Scheme",
                    subtitle = "Standard examination blueprint across Federal & Provincial commissions"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    ExamSchemeCard(
                        stageBadge = "PAPER I",
                        marksText = "100 Marks",
                        title = "Objective Multiple Choice (MCQ)",
                        details = "Core Agriculture, Constitution & Acts, General Awareness / IQ. Strictly applies -20% negative marking for wrong answers; 0 deduction for skipped items.",
                        icon = Icons.Outlined.Verified,
                        highlightAmber = false
                    )
                    ExamSchemeCard(
                        stageBadge = "PAPER II",
                        marksText = "100 Marks",
                        title = "Subjective / Technical Analytical",
                        details = "Short & long technical answers (5 & 10 marks) covering Agronomy, Soil, Horticulture, Plant Protection, Extension, Economics, and Practical standards.",
                        icon = Icons.Outlined.EditNote,
                        highlightAmber = false
                    )
                    ExamSchemeCard(
                        stageBadge = "FINAL STAGE",
                        marksText = "Skill + Viva",
                        title = "Computer Skill Test, GD & Interview",
                        details = "English & Nepali Unicode typing speed test (5-min Net WPM formula), MS Office practicals, Officer Group Discussion (GD), and technical panel interview.",
                        icon = Icons.Outlined.Groups,
                        highlightAmber = true
                    )
                }
            }

            // 4. Legal & Creator Attribution Footer
            item {
                HomeLegalFooter(onOpenPolicy = onOpenPolicy)
            }
        }
    }
}

@Composable
private fun HeroProgressCard(
    progress: StudyProgressSummary,
    selectedCommission: CommissionOption,
    selectedPostLevel: PostLevelOption,
    onContinueStudy: () -> Unit
) {
    val pct = (progress.completionFraction * 100f).roundToInt()

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = ForestGreenPrimary),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("home_hero_progress_card")
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.img_hero_terrace_fields_1795072365414),
                contentDescription = "Nepal terraced agriculture fields illustration",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                ForestGreenDark.copy(alpha = 0.86f),
                                ForestGreenPrimary.copy(alpha = 0.92f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = WarmAmberContainer.copy(alpha = 0.95f),
                        contentColor = OnWarmAmberContainer,
                        shape = RoundedCornerShape(999.dp)
                    ) {
                        Text(
                            text = "${selectedCommission.shortName} • ${selectedPostLevel.shortName}",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }

                    Text(
                        text = "$pct%",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontFamily = JetBrainsMonoFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        color = WarmAmberBright
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Study Progress Tracker",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    Text(
                        text = "${progress.completedNotes} of ${progress.totalNotes} syllabus modules completed (saved offline on-device)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }

                LinearProgressIndicator(
                    progress = { progress.completionFraction.coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(999.dp)),
                    color = WarmAmberBright,
                    trackColor = Color.White.copy(alpha = 0.22f),
                    strokeCap = StrokeCap.Round
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    HeroStatPill(
                        icon = Icons.Outlined.CheckCircle,
                        label = "Completed",
                        value = "${progress.completedNotes}/${progress.totalNotes}",
                        modifier = Modifier.weight(1f)
                    )
                    HeroStatPill(
                        icon = Icons.Outlined.BookmarkBorder,
                        label = "Bookmarks",
                        value = "${progress.bookmarkedNotes}",
                        modifier = Modifier.weight(1f)
                    )
                    HeroStatPill(
                        icon = Icons.Outlined.Assignment,
                        label = "Best Net",
                        value = progress.bestNetScore?.let {
                            String.format(Locale.US, "%.2f", it)
                        } ?: "—",
                        modifier = Modifier.weight(1f)
                    )
                }

                Surface(
                    color = Color.White,
                    contentColor = ForestGreenPrimary,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(onClick = onContinueStudy)
                        .testTag("hero_continue_study_button")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Open Filtered Study Notes",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HeroStatPill(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.White.copy(alpha = 0.13f),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.18f)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = WarmAmberBright,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.80f)
                )
            }
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontFamily = JetBrainsMonoFamily,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White
            )
        }
    }
}

@Composable
private fun SectionHeading(
    title: String,
    subtitle: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun QuickStartTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    testTag: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(accentColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Open $title",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun ExamSchemeCard(
    stageBadge: String,
    marksText: String,
    title: String,
    details: String,
    icon: ImageVector,
    highlightAmber: Boolean
) {
    val badgeBg = if (highlightAmber) {
        MaterialTheme.colorScheme.tertiaryContainer
    } else {
        MaterialTheme.colorScheme.primaryContainer
    }
    val badgeFg = if (highlightAmber) {
        MaterialTheme.colorScheme.onTertiaryContainer
    } else {
        MaterialTheme.colorScheme.onPrimaryContainer
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.45f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
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
                    Surface(
                        color = badgeBg,
                        contentColor = badgeFg,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = stageBadge,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = marksText,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = JetBrainsMonoFamily,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(badgeBg.copy(alpha = 0.7f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = badgeFg,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = details,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun HomeLegalFooter(
    onOpenPolicy: (PolicySection) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("home_legal_footer")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(R.string.creator_attribution),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "100% Free, Ad-Free & Offline Public-Domain Educational Resource. Not affiliated with PSC Nepal or the Government of Nepal.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FooterLinkButton(
                    text = stringResource(R.string.footer_about),
                    testTag = "footer_about_link",
                    onClick = { onOpenPolicy(PolicySection.ABOUT) }
                )
                FooterLinkButton(
                    text = stringResource(R.string.footer_contact),
                    testTag = "footer_contact_link",
                    onClick = { onOpenPolicy(PolicySection.CONTACT) }
                )
                FooterLinkButton(
                    text = stringResource(R.string.footer_privacy),
                    testTag = "footer_privacy_link",
                    onClick = { onOpenPolicy(PolicySection.PRIVACY) }
                )
                FooterLinkButton(
                    text = "Terms",
                    testTag = "footer_terms_link",
                    onClick = { onOpenPolicy(PolicySection.TERMS) }
                )
            }
        }
    }
}

@Composable
private fun FooterLinkButton(
    text: String,
    testTag: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 4.dp),
        modifier = Modifier.testTag(testTag)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.primary
        )
    }
}
