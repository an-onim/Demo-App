package ru.social.core.ui.common.components.appbars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.social.core.ui.R
import ru.social.core.ui.common.components.buttons.CTextButton
import ru.social.core.ui.common.theme.SDTheme

/**
 * TopBar for BottomSheet
 *
 * @param title String, name of the page
 * @param onBack on back button pressure function
 * @param actions list of actions
 */
@Composable
fun BSTopBar(
    title: String?,
    onBack: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
) {
    BoxWithConstraints {
        val maxWidth = maxWidth
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (onBack == null) {
                Box { }
            } else {
                CTextButton(
                    label = stringResource(R.string.cancel),
                    contentColor = SDTheme.colors.fgSecondary,
                    onClick = { onBack.invoke() }
                )
            }

            if (title == null) {
                Box { }
            } else {
                Text(
                    title,
                    style = SDTheme.typography.bodyBoldL,
                    color = SDTheme.colors.fgPrimary
                )
            }

            if (actions == null) {
                Box { }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(0.dp),
                    modifier = Modifier.widthIn(0.dp, maxWidth / 2)
                ) {
                    actions()
                }
            }

        }
    }
}