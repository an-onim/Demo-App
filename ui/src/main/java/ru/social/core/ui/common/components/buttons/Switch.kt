package ru.social.core.ui.common.components.buttons

import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.social.core.ui.common.theme.SDTheme

@Composable
fun CSwitch(
    enabled: Boolean = true,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val checkedState = remember { mutableStateOf(true) }
    val interactionSource = remember { MutableInteractionSource() }
    Switch(
        modifier = modifier.indication(
            interactionSource,
            indication = ripple(
                bounded = true,
                color = SDTheme.colors.fgTertiary,
                radius = 24f.dp
            )
        ),
        interactionSource = interactionSource,
        checked = checkedState.value,
        enabled = enabled,
        onCheckedChange = {
            onCheckedChange?.invoke(it)
            checkedState.value = it
        },
        colors = SwitchDefaults.colors().copy(
            checkedThumbColor = SDTheme.colors.fgOnColor,
            checkedTrackColor = SDTheme.colors.fgPrimary,
            checkedBorderColor = Color.Transparent,
            uncheckedThumbColor = SDTheme.colors.fgOnColor,
            uncheckedTrackColor = SDTheme.colors.fgPrimary,
            uncheckedBorderColor = Color.Transparent
        )
    )
}