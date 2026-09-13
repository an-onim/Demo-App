package ru.social.core.ui.common.components.appbars.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.social.core.ui.common.components.buttons.BackButton
import ru.social.core.ui.common.theme.SDTheme


object CustomScaffoldTopBarScope

@Composable
fun CustomScaffoldTopBarScope.TopBar(
    modifier: Modifier = Modifier,
    mode: CollapsibleAppBarMode = CollapsibleAppBarMode.DEFAULT,
    title: String = "",
    onBack: (() -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null
) {
    TopBarInternal(
        insets = mode.insets(),
        modifier = modifier.background(Color.Transparent),
        title = title,
        navigationIcon = onBack?.let { { BackButton(it) } },
        actions = actions
    )
}

@Composable
private fun TopBarInternal(
    insets: PaddingValues,
    modifier: Modifier = Modifier,
    title: String = "",
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
) {

    BoxWithConstraints(
        modifier = modifier.padding(top = insets.calculateTopPadding())
    ) {

        val maxWidth = maxWidth
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .height(CollapsibleTopAppBarDefaults.minHeight)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Box {
                if (navigationIcon != null) {
                    navigationIcon()
                }
            }

            if (navigationIcon != null) {
                Spacer(modifier = Modifier.weight(1f))
            }

            Text(
                text = title,
                style = SDTheme.typography.headingS
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                modifier = Modifier.widthIn(0.dp, maxWidth / 2)
            ) {
                if (actions != null) {
                    actions()
                }
            }
        }

    }
}
