package ru.social.core.ui.common.components.buttons

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ChipGroup(
    items: List<String>,
    selected: String,
    onItemChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        for (item in items) {
            ChipButton(
                modifier = Modifier.weight(1f),
                label = item,
                selected = item == selected,
                onClick = { onItemChanged(item) }
            )
        }
    }
}
