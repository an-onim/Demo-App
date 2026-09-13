package ru.social.core.ui.common.components.buttons

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import ru.social.core.ui.R
import ru.social.core.ui.common.theme.SDTheme

@Composable
fun BackButton(
    onClick: () -> Unit,
) {
    CIconButton(
        iconId = R.drawable.ic_arrow_left,
        contentColor = SDTheme.colors.fgSecondary,
        size = 44.dp,
        onClick = onClick
    )
}

@Composable
fun ShareButton(
    context: Context,
    extras: Bundle = Bundle()
) {
    CIconButton(
        iconId = R.drawable.ic_share,
        contentColor = SDTheme.colors.fgSecondary,
        onClick = {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtras(extras)
            }
            context.startActivity(
                Intent.createChooser(
                    intent, context.getString(R.string.share_profile)
                )
            )
        }
    )
}

@Composable
fun FilterButton(
    onClick: () -> Unit,
) {
    CIconButton(
        iconId = R.drawable.ic_filter,
        contentColor = SDTheme.colors.fgSecondary,
        onClick = onClick
    )
}

@Composable
fun UserEditButton(
    onClick: () -> Unit,
) {
    CIconButton(
        iconId = R.drawable.ic_user_edit,
        contentColor = SDTheme.colors.fgSecondary,
        onClick = onClick
    )
}
