package ru.social.core.ui.common.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CIcon(
    size: Dp,
    bgColor: Color,
    @DrawableRes imageId: Int,
    imageColor: Color,
    padding: Dp = 10.dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .aspectRatio(1f)
            .background(color = bgColor, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(imageId),
            null,
            modifier = Modifier.padding(padding),
            colorFilter = ColorFilter.tint(color = imageColor)
        )
    }
}