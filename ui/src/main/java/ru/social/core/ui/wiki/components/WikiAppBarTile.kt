package ru.social.core.ui.wiki.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.social.demo.ui.components.CIcon
import ru.social.demo.ui.components.containers.OutlinedContainer
import ru.social.demo.ui.theme.SDTheme

@Composable
fun WikiAppBarTile(
    @DrawableRes imageId: Int,
    @StringRes titleId: Int,
    @StringRes descriptionId: Int,
    modifier: Modifier = Modifier
) {
    OutlinedContainer(
        modifier = modifier,
        paddingVertical = 20.dp,
        paddingHorizontal = 20.dp
    ) {
        Column {
            CIcon(
                size = 68.dp,
                bgColor = SDTheme.colors.bgHighlight,
                imageId = imageId,
                imageColor = SDTheme.colors.fgActionEmphasis
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                stringResource(titleId),
                style = SDTheme.typography.headingS,
                color = SDTheme.colors.fgPrimary
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                stringResource(descriptionId),
                style = SDTheme.typography.bodyMediumM,
                color = SDTheme.colors.fgSecondary
            )
        }
    }
}
