package com.example.ui.modals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Gavel
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.data.PolicySection
import com.example.ui.theme.OnWarmAmberContainer
import com.example.ui.theme.WarmAmberAccent
import com.example.ui.theme.WarmAmberContainer

@Composable
fun PolicyLegalModal(
    initialSection: PolicySection,
    onDismiss: () -> Unit
) {
    var selectedSection by remember(initialSection) { mutableStateOf(initialSection) }
    val uriHandler = LocalUriHandler.current
    val emailAddress = stringResource(R.string.creator_email)
    val pscUrl = stringResource(R.string.psc_official_url)
    val lawCommissionUrl = stringResource(R.string.law_commission_url)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .heightIn(max = 620.dp)
                .testTag("policy_legal_modal")
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
                            text = "Info, Privacy & Legal Policy",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = stringResource(R.string.creator_attribution),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_policy_modal_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close policy dialog"
                        )
                    }
                }

                ScrollableTabRow(
                    selectedTabIndex = PolicySection.entries.indexOf(selectedSection),
                    edgePadding = 12.dp,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    PolicySection.entries.forEach { section ->
                        Tab(
                            selected = section == selectedSection,
                            onClick = { selectedSection = section },
                            text = {
                                Text(
                                    text = section.title,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            },
                            modifier = Modifier.testTag("policy_tab_${section.name.lowercase()}")
                        )
                    }
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = false)
                        .verticalScroll(rememberScrollState())
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    when (selectedSection) {
                        PolicySection.ABOUT -> {
                            PolicyHeaderRow(Icons.Outlined.Info, "About & Creator Attribution")
                            Text(
                                text = "Nepal Krishi Lok Sewa Prep is built and curated by Apar Neupane as a 100% free, ad-free, offline-first educational study companion for Nepali agriculture students and graduates preparing for Federal and Provincial Public Service Commission (Lok Sewa) Officer (7th/8th Level) and JT/JTA (4th/5th Level) examinations.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "All syllabus modules, bilingual MCQs, typing drills, and practical guides are structured around official public-domain Nepal syllabi, the Constitution of Nepal (2072), and enacted agricultural statutes.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }

                        PolicySection.CONTACT -> {
                            PolicyHeaderRow(Icons.Outlined.Email, "Copyright, DMCA & Contact")
                            Text(
                                text = "All study notes and practice MCQs bundled in this application are original summaries compiled from public-domain Nepal laws, constitutional schedules, and publicly released PSC syllabi. No copyrighted private textbooks or proprietary test banks are used.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "For copyright inquiries, DMCA requests, syllabus errata, or suggestions, please contact the creator directly:",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            ExternalLinkTile(
                                title = "Email Creator: Apar Neupane",
                                subtitle = emailAddress,
                                onClick = {
                                    runCatching { uriHandler.openUri("mailto:$emailAddress") }
                                }
                            )
                        }

                        PolicySection.PRIVACY -> {
                            PolicyHeaderRow(Icons.Outlined.Lock, "Privacy Policy (Offline On-Device)")
                            Text(
                                text = "Your privacy is fully respected. This Android app operates 100% offline with zero third-party advertising SDKs, zero analytics trackers, and no mandatory account login.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "• Local Storage Only: Your study completion checkmarks, bookmarks, mock exam scorecards, GD checklist progress, and any custom notes/MCQs added via the Creator Content Manager remain strictly on your device inside its private Room SQLite database.\n• Zero Data Sharing: No personal study data is transmitted to external servers. JSON export occurs strictly when you explicitly tap Export/Share.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        PolicySection.TERMS -> {
                            PolicyHeaderRow(Icons.Outlined.Gavel, "Terms & Government Non-Affiliation Disclaimer")
                            Surface(
                                color = WarmAmberContainer,
                                border = BorderStroke(1.dp, WarmAmberAccent.copy(alpha = 0.5f)),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "IMPORTANT DISCLAIMER: Nepal Krishi Lok Sewa Prep is an independent educational resource created by Apar Neupane. This application is NOT affiliated with, endorsed by, authorized by, or representing the Public Service Commission (PSC / Lok Sewa Aayog) Nepal, any Provincial Public Service Commission, or the Government of Nepal.",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = OnWarmAmberContainer,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                            Text(
                                text = "Always verify official vacancy notices, syllabi updates, examination schedules, and enacted statutes directly through official Government of Nepal portals:",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            ExternalLinkTile(
                                title = "Public Service Commission Nepal (PSC)",
                                subtitle = pscUrl,
                                onClick = { runCatching { uriHandler.openUri(pscUrl) } }
                            )
                            ExternalLinkTile(
                                title = "Nepal Law Commission (Official Acts)",
                                subtitle = lawCommissionUrl,
                                onClick = { runCatching { uriHandler.openUri(lawCommissionUrl) } }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Composable
private fun PolicyHeaderRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ExternalLinkTile(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                contentDescription = "Open external link",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
