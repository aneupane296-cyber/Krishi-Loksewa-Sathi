package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.CommissionOption
import com.example.data.NavTab
import com.example.data.PostLevelOption
import com.example.ui.theme.WarmAmberBright
import com.example.ui.theme.WarmAmberContainer
import com.example.ui.theme.OnWarmAmberContainer

@Composable
fun StickyKrishiTopHeader(
    onCreatorTap: () -> Unit,
    onOpenPolicy: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shadowElevation = 2.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(R.string.creator_attribution),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.86f),
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .clickable(onClick = onCreatorTap)
                        .padding(vertical = 3.dp, horizontal = 2.dp)
                        .testTag("creator_attribution_header")
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    color = WarmAmberContainer,
                    contentColor = OnWarmAmberContainer,
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text(
                        text = stringResource(R.string.badge_free_resource),
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp)
                    )
                }

                Surface(
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.14f),
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.25f)),
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(onClick = onOpenPolicy)
                        .testTag("info_policy_button")
                        .semantics { contentDescription = "Open Info and Policy Modal" }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Info",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun JurisdictionFilterBar(
    selectedCommission: CommissionOption,
    selectedPostLevel: PostLevelOption,
    onSelectCommission: (CommissionOption) -> Unit,
    onSelectPostLevel: (PostLevelOption) -> Unit,
    modifier: Modifier = Modifier
) {
    var commissionExpanded by remember { mutableStateOf(false) }
    var levelExpanded by remember { mutableStateOf(false) }

    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Dropdown 1: Commission / Province
                Box(modifier = Modifier.weight(1.15f)) {
                    FilterSelectorButton(
                        label = stringResource(R.string.filter_commission_label),
                        value = selectedCommission.displayName,
                        onClick = { commissionExpanded = true },
                        testTag = "commission_filter_dropdown"
                    )
                    DropdownMenu(
                        expanded = commissionExpanded,
                        onDismissRequest = { commissionExpanded = false }
                    ) {
                        CommissionOption.entries.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = option.displayName,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = if (option == selectedCommission) FontWeight.Bold else FontWeight.Normal
                                        ),
                                        color = if (option == selectedCommission) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.onSurface
                                        }
                                    )
                                },
                                onClick = {
                                    onSelectCommission(option)
                                    commissionExpanded = false
                                }
                            )
                        }
                    }
                }

                // Dropdown 2: Service Post Level
                Box(modifier = Modifier.weight(0.85f)) {
                    FilterSelectorButton(
                        label = stringResource(R.string.filter_post_level_label),
                        value = selectedPostLevel.shortName,
                        onClick = { levelExpanded = true },
                        testTag = "post_level_filter_dropdown"
                    )
                    DropdownMenu(
                        expanded = levelExpanded,
                        onDismissRequest = { levelExpanded = false }
                    ) {
                        PostLevelOption.entries.forEach { level ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = level.displayName,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = if (level == selectedPostLevel) FontWeight.Bold else FontWeight.Normal
                                        ),
                                        color = if (level == selectedPostLevel) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.onSurface
                                        }
                                    )
                                },
                                onClick = {
                                    onSelectPostLevel(level)
                                    levelExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }
    }
}

@Composable
private fun FilterSelectorButton(
    label: String,
    value: String,
    onClick: () -> Unit,
    testTag: String
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.65f)),
        modifier = Modifier
            .fillMaxWidth()
            .minimumInteractiveComponentSize()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowDown,
                contentDescription = "Select $label",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun KrishiBottomNavigationBar(
    activeTab: NavTab,
    onSelectTab: (NavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp,
        windowInsets = WindowInsets.navigationBars,
        modifier = modifier
    ) {
        NavTab.entries.forEach { tab ->
            val selected = tab == activeTab
            NavigationBarItem(
                selected = selected,
                onClick = { onSelectTab(tab) },
                modifier = Modifier.testTag("nav_tab_${tab.route}"),
                icon = {
                    Icon(
                        imageVector = tabIcon(tab, selected),
                        contentDescription = tab.label
                    )
                },
                label = {
                    Text(
                        text = tab.label,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

@Composable
fun KrishiNavigationRail(
    activeTab: NavTab,
    onSelectTab: (NavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        NavTab.entries.forEach { tab ->
            val selected = tab == activeTab
            NavigationRailItem(
                selected = selected,
                onClick = { onSelectTab(tab) },
                modifier = Modifier.testTag("rail_tab_${tab.route}"),
                icon = {
                    Icon(
                        imageVector = tabIcon(tab, selected),
                        contentDescription = tab.label
                    )
                },
                label = {
                    Text(
                        text = tab.label,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

private fun tabIcon(tab: NavTab, selected: Boolean) = when (tab) {
    NavTab.HOME -> if (selected) Icons.Filled.Home else Icons.Outlined.Home
    NavTab.NOTES -> if (selected) Icons.Filled.MenuBook else Icons.Outlined.MenuBook
    NavTab.EXAM -> if (selected) Icons.Filled.Assignment else Icons.Outlined.Assignment
    NavTab.SKILL_GD -> if (selected) Icons.Filled.Keyboard else Icons.Outlined.Keyboard
}
