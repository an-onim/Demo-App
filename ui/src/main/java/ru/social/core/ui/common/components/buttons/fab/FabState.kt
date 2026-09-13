package ru.social.core.ui.common.components.buttons.fab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

sealed class FabState {
    object Collapsed: FabState()
    object Expanded: FabState()

    fun isExpanded() = this == Expanded

    fun toggle() = if (isExpanded()) Collapsed else Expanded

}

@Composable
fun rememberFabState() = remember { mutableStateOf<FabState>(FabState.Collapsed) }