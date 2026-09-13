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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import ru.social.core.ui.common.components.buttons.BackButton
import ru.social.core.ui.common.theme.SDTheme
import kotlin.math.min


object CollapsibleScaffoldTopBarScope

@Composable
fun CollapsibleScaffoldTopBarScope.TopBar(
    modifier: Modifier = Modifier,
    mode: CollapsibleAppBarMode = CollapsibleAppBarMode.DEFAULT,
    title: String = "",
    onBack: (() -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
    content: (@Composable CollapsibleTopAppBarScope.() -> Unit) = { }
) {
    TopBar(
        modifier = modifier,
        mode = mode,
        title = title,
        actions = actions,
        content = content,
        navigationIcon = onBack?.let { { BackButton(it) } }
    )
}

@Composable
fun CollapsibleScaffoldTopBarScope.TopBar(
    modifier: Modifier = Modifier,
    mode: CollapsibleAppBarMode = CollapsibleAppBarMode.DEFAULT,
    actions: (@Composable RowScope.() -> Unit)? = null,
    title: String = "",
    navigationIcon: (@Composable () -> Unit)? = null,
    content: (@Composable CollapsibleTopAppBarScope.() -> Unit) = { }
) {
    TopBarInternal(
        scrollOffset = mode.offset(),
        insets = mode.insets(),
        modifier = modifier.background(Color.Transparent),
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        content = content
    )
}


@Composable
private fun TopBarInternal(
    scrollOffset: Int,
    insets: PaddingValues,
    modifier: Modifier = Modifier,
    title: String = "",
    navigationIcon: (@Composable () -> Unit)? = null,
    maxHeight: Dp = TopAppBarMaxHeight.current,
    actions: (@Composable RowScope.() -> Unit)? = null,
    content: @Composable CollapsibleTopAppBarScope.() -> Unit
) {

    val density = LocalDensity.current
    val bodyHeight = maxHeight - CollapsibleTopAppBarDefaults.minHeight
    val maxOffset = with(density) { bodyHeight.roundToPx() - insets.calculateTopPadding().roundToPx() }
    val offset = min(scrollOffset, maxOffset)
    val fraction = 1f - kotlin.math.max(0f, offset.toFloat()) / maxOffset
    val currentMaxHeight = bodyHeight * fraction

    BoxWithConstraints(
        modifier = modifier.padding(top = insets.calculateTopPadding())
    ) {

        val maxWidth = maxWidth
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .height(CollapsibleTopAppBarDefaults.minHeight)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box {
                if (navigationIcon != null) {
                    navigationIcon()
                }
            }

            if (navigationIcon != null) {
                Spacer(modifier = Modifier.size(12.dp))
            }

            Text(
                text = title,
                style = SDTheme.typography.headingL
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.widthIn(0.dp, maxWidth / 2)
            ) {
                if (actions != null) {
                    actions()
                }
            }
        }

        BoxWithConstraints(
            modifier = Modifier
                .padding(top = CollapsibleTopAppBarDefaults.minHeight)
                .height(max(0.dp, currentMaxHeight))
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .alpha(alpha = (currentMaxHeight / bodyHeight))
        ) {
            val scope = remember(fraction, this) {
                CollapsibleTopAppBarScope(fraction = fraction, scope = this)
            }
            content(scope)
        }

    }
}
