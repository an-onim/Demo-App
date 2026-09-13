package ru.social.core.ui.common.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.social.core.ui.common.theme.SDTheme

enum class LabelType {
    SMALL, MEDIUM, LARGE
}

@Composable
fun LabelTile(
    label: String,
    @DrawableRes iconId: Int? = null,
    type: LabelType = LabelType.MEDIUM,
    onClick: (() -> Unit)? = null
) {
    val iconSize = when(type) {
        LabelType.SMALL -> 16.dp
        LabelType.MEDIUM -> 20.dp
        LabelType.LARGE -> 24.dp
    }

    val textStyle = when(type) {
        LabelType.SMALL -> SDTheme.typography.bodyMediumS
        LabelType.MEDIUM -> SDTheme.typography.bodyMediumM
        LabelType.LARGE -> SDTheme.typography.bodyMediumL
    }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onClick?.invoke() }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        iconId?.let {
            Image(
                painterResource(it),
                null,
                Modifier.size(iconSize)
            )
            Spacer(modifier = Modifier.size(12.dp))
        }

        Text(
            label,
            style = textStyle,
            color = SDTheme.colors.fgPrimary
        )
    }
}
