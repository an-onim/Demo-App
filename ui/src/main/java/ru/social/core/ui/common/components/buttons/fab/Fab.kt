package ru.social.core.ui.common.components.buttons.fab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import ru.social.core.ui.common.theme.SDTheme

@Composable
fun Fab(
    items: List<FabItem>,
    fabState: MutableState<FabState> = rememberFabState(),
    onStateChanged: (fabState: FabState) -> Unit = {}
) {
    val rotation by animateFloatAsState(
        if (fabState.value == FabState.Expanded) 45f else 0f
    )

    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.End
    ) {
        AnimatedVisibility(
            visible = fabState.value.isExpanded(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            LazyColumn(
                Modifier.wrapContentSize().padding(bottom = 8.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items) { i -> FabItemButton(i) }
            }
        }

        FloatingActionButton(
            modifier = Modifier.rotate(rotation),
            onClick = {
                fabState.value = fabState.value.toggle()
                onStateChanged(fabState.value)
            },
            shape = CircleShape,
            containerColor = SDTheme.colors.bgFab,
            contentColor = SDTheme.colors.fgOnColor
        ) {
            Icon(Icons.Filled.Add, "New")
        }
    }
}
