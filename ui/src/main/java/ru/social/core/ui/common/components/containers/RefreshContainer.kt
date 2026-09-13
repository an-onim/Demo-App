package ru.social.core.ui.common.components.containers

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import ru.social.core.ui.common.theme.SDTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefreshContainer(
    onRefresh: () -> Unit = {},
    content: @Composable (() -> Unit)
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val state = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()

    Box(
        Modifier.pullToRefresh(
            isRefreshing = isRefreshing,
            state = state,
            onRefresh = {
                coroutineScope.launch {
                    isRefreshing = true
                    onRefresh.invoke()
                    isRefreshing = false
                }
            }
        )
    ) {
        content.invoke()

        PullToRefreshDefaults.Indicator(
            state = state,
            isRefreshing = isRefreshing,
            modifier = Modifier.align(Alignment.TopCenter),
            containerColor = SDTheme.colors.bgPrimary,
            color = SDTheme.colors.fgActionEmphasis
        )
    }
}
