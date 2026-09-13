package ru.social.core.ui.common.components.buttons.fab

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.social.core.ui.common.components.buttons.CIconButtonFilled
import ru.social.core.ui.common.theme.SDTheme

abstract class FabItem(
    val id: String,
    @DrawableRes val iconId: Int,
    @StringRes val label: Int?
) {
    abstract fun onClick()
}

@Composable
fun FabItemButton(item: FabItem) {
    Row(
        modifier = Modifier.wrapContentSize().padding(end = 9.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item.label?.let {
            Text(
                text = stringResource(it),
                style = SDTheme.typography.bodyMediumS,
                modifier = Modifier
                    .clip(shape = SDTheme.shapes.corners)
                    .background(SDTheme.colors.bgSecondary)
                    .padding(4.dp)
            )
        }

        CIconButtonFilled(
            item.iconId,
            bgColor = SDTheme.colors.bgFab,
            contentColor = SDTheme.colors.fgOnColor,
            onClick = { item.onClick() }
        )
    }
}
