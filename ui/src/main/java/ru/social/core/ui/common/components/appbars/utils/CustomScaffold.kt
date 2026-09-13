package ru.social.core.ui.common.components.appbars.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ru.social.core.ui.common.theme.SDTheme

@Composable
fun CustomScaffold(
    modifier: Modifier = Modifier,
    topInset: Boolean = true,
    topBar: @Composable CustomScaffoldTopBarScope.() -> Unit = {},
    content: @Composable (insets: PaddingValues) -> Unit
) {
    CustomScaffoldInternal(
        modifier = modifier,
        needTopInset = topInset,
        topBar = topBar,
        content = content
    )
}

@Composable
private fun CustomScaffoldInternal(
    modifier: Modifier = Modifier,
    needTopInset: Boolean,
    topBar: @Composable CustomScaffoldTopBarScope.() -> Unit = {},
    content: @Composable (insets: PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent
    ) { insets ->
        val topInset = if (needTopInset) insets.calculateTopPadding() else 0.dp

        Box (
            Modifier.background(color = SDTheme.colors.bgPrimary)
        ) {
            Box(
                modifier = Modifier.padding(top = CollapsibleTopAppBarDefaults.minHeight)
            ) {
                content(
                    PaddingValues(
                        top = topInset + 20.dp,
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 20.dp
                    )
                )
            }
            CompositionLocalProvider(
                TopAppBarInsets provides PaddingValues(
                    top = topInset,
                    bottom = insets.calculateBottomPadding(),
                    start = insets.calculateStartPadding(LayoutDirection.Ltr),
                    end = insets.calculateEndPadding(LayoutDirection.Ltr),
                )
            ) {
                CustomScaffoldTopBarScope.topBar()
            }
        }
    }
}