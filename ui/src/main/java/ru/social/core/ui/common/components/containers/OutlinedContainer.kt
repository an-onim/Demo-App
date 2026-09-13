package ru.social.core.ui.common.components.containers

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.social.core.ui.common.theme.SDTheme

@Composable
fun OutlinedContainer(
    parentWidth: Boolean = false,
    paddingHorizontal: Dp = 0.dp,
    paddingVertical: Dp = 0.dp,
    modifier: Modifier = Modifier,
    borderColor: Color = SDTheme.colors.borderColor,
    content: @Composable (BoxScope.() -> Unit)
) {

    RoundedContainer(
        parentWidth = parentWidth,
        paddingHorizontal = paddingHorizontal,
        paddingVertical = paddingVertical,
        shape = SDTheme.shapes.corners,
        bgColor = SDTheme.colors.bgPrimary,
        modifier = modifier.border(
            1.5.dp,
            borderColor,
            shape = SDTheme.shapes.corners
        ),
        content = content
    )

}


@Composable
fun ChipContainer(
    parentWidth: Boolean = false,
    paddingHorizontal: Dp = 0.dp,
    paddingVertical: Dp = 0.dp,
    modifier: Modifier = Modifier,
    borderColor: Color = SDTheme.colors.borderColor,
    content: @Composable (BoxScope.() -> Unit)
) {

    RoundedContainer(
        parentWidth = parentWidth,
        paddingHorizontal = paddingHorizontal,
        paddingVertical = paddingVertical,
        shape = SDTheme.shapes.textCorners,
        bgColor = SDTheme.colors.bgPrimary,
        modifier = modifier.border(
            1.5.dp,
            borderColor,
            shape = SDTheme.shapes.corners
        ),
        content = content
    )

}
