package ru.social.core.ui.common.components.appbars.utils

import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp


object CollapsibleTopAppBarDefaults {
    // replicating the value in androidx.compose.material.AppBar.AppBarHeight which is private
    val minHeight = 72.dp
    val maxHeightSmall = 198.dp
    val maxHeightLarge = 316.dp
}


internal val TopAppBarInsets = compositionLocalOf {
    PaddingValues(0.dp)
}

internal val TopAppBarMaxHeight = compositionLocalOf {
    CollapsibleTopAppBarDefaults.maxHeightLarge
}


class CollapsibleTopAppBarScope(
    val fraction: Float,
    scope: BoxWithConstraintsScope
) : BoxWithConstraintsScope by scope

