package ru.social.core.ui.common.components.containers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.social.core.ui.common.theme.SDTheme

@Composable
fun RoundedContainer(
    parentWidth: Boolean = false,
    paddingHorizontal: Dp = 0.dp,
    paddingVertical: Dp = 0.dp,
    shape: Shape = RoundedCornerShape(0.dp),
    bgColor: Color = Color.Transparent,
    modifier: Modifier = Modifier,
    content: @Composable (BoxScope.() -> Unit)
) {
    val modifierr = if (parentWidth) modifier.fillMaxWidth() else modifier.wrapContentSize()

    Box(
        modifier = modifierr
            .clip(shape = shape)
            .background(color = bgColor)
            .padding(horizontal = paddingHorizontal, vertical = paddingVertical),
        contentAlignment = Alignment.TopCenter,
        content = content
    )
}

@Composable
fun DefaultRoundedContainer(
    parentWidth: Boolean = false,
    paddingHorizontal: Dp = 0.dp,
    paddingVertical: Dp = 0.dp,
    modifier: Modifier = Modifier,
    content: @Composable (BoxScope.() -> Unit)
) {
    RoundedContainer(
        parentWidth = parentWidth,
        paddingHorizontal = paddingHorizontal,
        paddingVertical = paddingVertical,
        shape = SDTheme.shapes.corners,
        modifier = modifier,
        content = content
    )
}