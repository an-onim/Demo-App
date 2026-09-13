package ru.social.core.ui.common.components.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.social.core.ui.common.components.containers.ChipContainer
import ru.social.core.ui.common.theme.SDTheme

@Composable
fun ChipButton(
    label: String,
    selected: Boolean,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    ChipContainer(
        parentWidth = true,
        paddingVertical = 14.dp,
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        ),
        borderColor = if (selected) SDTheme.colors.borderColor else Color.Transparent
    ) {
        Text(
            label,
            style = SDTheme.typography.bodyBoldM,
            color = if (selected) SDTheme.colors.fgPrimary else SDTheme.colors.fgTertiary
        )
    }
}