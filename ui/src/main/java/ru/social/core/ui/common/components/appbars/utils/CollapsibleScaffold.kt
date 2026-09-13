package ru.social.core.ui.common.components.appbars.utils

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ru.social.core.ui.common.theme.SDTheme
import kotlin.math.absoluteValue

@Composable
fun CollapsibleScaffold(
    state: ScrollState,
    modifier: Modifier = Modifier,
    topInset: Boolean = true,
    topBarMaxHeight: Dp = CollapsibleTopAppBarDefaults.maxHeightLarge,
    topBar: @Composable CollapsibleScaffoldTopBarScope.() -> Unit = {},
    content: @Composable (insets: PaddingValues) -> Unit
) {
    CollapsibleScaffoldInternal(
        offsetState = rememberOffsetScrollState(state),
        modifier = modifier,
        topBarMaxHeight = topBarMaxHeight,
        needTopInset = topInset,
        topBar = topBar,
        content = content
    )
}

@Composable
fun CollapsibleScaffold(
    state: LazyListState,
    modifier: Modifier = Modifier,
    topInset: Boolean = true,
    topBarMaxHeight: Dp = CollapsibleTopAppBarDefaults.maxHeightLarge,
    topBar: @Composable CollapsibleScaffoldTopBarScope.() -> Unit = {},
    content: @Composable (insets: PaddingValues) -> Unit
) {
    CollapsibleScaffoldInternal(
        offsetState = rememberOffsetScrollState(state),
        modifier = modifier,
        topBarMaxHeight = topBarMaxHeight,
        needTopInset = topInset,
        topBar = topBar,
        content = content
    )
}


@Composable
private fun CollapsibleScaffoldInternal(
    offsetState: State<Int?>,
    modifier: Modifier = Modifier,
    topBarMaxHeight: Dp,
    needTopInset: Boolean,
    topBar: @Composable CollapsibleScaffoldTopBarScope.() -> Unit = {},
    content: @Composable (insets: PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent
    ) { insets ->
        val topInset = if (needTopInset) insets.calculateTopPadding() else 0.dp
        Box(
            Modifier.background(color = SDTheme.colors.bgPrimary)
        ) {
            Box(
                modifier = Modifier.padding(top = CollapsibleTopAppBarDefaults.minHeight)
            ) {
                content(
                    PaddingValues(
                        top = topBarMaxHeight - CollapsibleTopAppBarDefaults.minHeight + topInset + 20.dp,
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 20.dp
                    )
                )
            }
            CompositionLocalProvider(
                TopAppBarScrollOffset provides offsetState,
                TopAppBarMaxHeight provides topBarMaxHeight,
                TopAppBarInsets provides PaddingValues(
                    top = topInset,
                    bottom = insets.calculateBottomPadding(),
                    start = insets.calculateStartPadding(LayoutDirection.Ltr),
                    end = insets.calculateEndPadding(LayoutDirection.Ltr),
                )
            ) {
                CollapsibleScaffoldTopBarScope.topBar()
            }
        }
    }
}


private val TopAppBarScrollOffset = compositionLocalOf<State<Int?>> {
    mutableStateOf(null)
}

enum class CollapsibleAppBarMode {
    DEFAULT,
    COLLAPSED,
    EXPANDED;

    @Composable
    internal fun offset(): Int = when (this) {
        DEFAULT -> TopAppBarScrollOffset.current.value ?: Int.MAX_VALUE
        COLLAPSED -> Int.MAX_VALUE
        EXPANDED -> 0
    }

    @Composable
    internal fun insets(): PaddingValues = when (this) {
        DEFAULT -> TopAppBarInsets.current
        COLLAPSED,
        EXPANDED -> remember { PaddingValues(0.dp) }
    }
}


@Composable
private fun rememberOffsetScrollState(state: ScrollState): MutableState<Int?> {
    val offsetState = rememberSaveable { mutableStateOf<Int?>(null) }
    offsetState.value = state.value
    return offsetState
}

@Composable
private fun rememberOffsetScrollState(state: LazyListState): MutableState<Int?> {
    val offsetState = rememberSaveable { mutableStateOf<Int?>(null) }
    val firstItem = remember(state) {
        derivedStateOf {
            val firstItem = state.layoutInfo.visibleItemsInfo.firstOrNull { it.index == 0 }
            firstItem?.offset?.absoluteValue
        }
    }
    offsetState.value = firstItem.value
    return offsetState
}