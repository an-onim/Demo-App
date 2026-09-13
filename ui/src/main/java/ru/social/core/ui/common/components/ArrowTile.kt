package ru.social.core.ui.common.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.social.core.ui.R
import ru.social.core.ui.common.theme.SDTheme

@Composable
fun ArrowTile(
    title: String,
    description: String? = null,
    @DrawableRes iconId: Int? = null,
    onClick: () -> Unit = {}
) {
    val image = if (iconId == null) {
        ArrowTileInternal(
            title = title,
            description = description,
            onClick = onClick
        )
    } else {
        ArrowTileInternal(
            title = title,
            description = description,
            icon = {
                Image(
                    painterResource(iconId),
                    null,
                    Modifier.size(24.dp)
                )
            },
            onClick = onClick
        )
    }
}

@Composable
fun ArrowTile(
    title: String,
    description: String? = null,
    icon: (@Composable () -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    ArrowTileInternal(
        title = title,
        description = description,
        icon = icon,
        onClick = onClick
    )
}

@Composable
private fun ArrowTileInternal(
    title: String,
    description: String? = null,
    icon: (@Composable () -> Unit)? = null,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        icon?.let {
            it.invoke()
            Spacer(modifier = Modifier.size(12.dp))
        }

        Column {
            Text(
                title,
                style = SDTheme.typography.bodyBoldL,
                color = SDTheme.colors.fgPrimary
            )
            description?.let {
                Text(
                    it,
                    style = SDTheme.typography.bodyMediumS,
                    color = SDTheme.colors.fgSecondary
                )
            }

        }

        Spacer(modifier = Modifier.weight(1f))
        Image(
            painterResource(R.drawable.ic_arrow_right),
            null,
            colorFilter = ColorFilter.tint(color = SDTheme.colors.fgSecondary)
        )
    }

}