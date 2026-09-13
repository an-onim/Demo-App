package ru.social.core.ui.common.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.social.core.ui.common.theme.SDTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CTabRow(
    tabs: List<T>,
    tabTitle: (T) -> String = { it.toString() },
    onClick: (Int, T) -> Unit = { _, _ -> }
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    SecondaryTabRow(
        selectedTabIndex = selectedTab,
        containerColor = SDTheme.colors.bgPrimary,
        contentColor = SDTheme.colors.fgPrimary,
        indicator = @Composable {
            TabRowDefaults.SecondaryIndicator(
                Modifier.tabIndicatorOffset(selectedTab, matchContentSize = false),
                height = 2.dp,
                color = SDTheme.colors.bgActionPrimary
            )
        }
    ) {
        tabs.forEachIndexed { index, item ->
            Tab(
                modifier = Modifier.wrapContentWidth(),
                selected = selectedTab == index,
                unselectedContentColor = SDTheme.colors.fgTertiary,
                onClick = {
                    onClick.invoke(index, item)
                    selectedTab = index
                }
            ) {
                Text(
                    tabTitle.invoke(item),
                    style = SDTheme.typography.bodyBoldL,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            }
        }
    }
}