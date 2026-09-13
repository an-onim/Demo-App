package ru.social.core.ui.common.components

import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.social.core.ui.R

enum class AvatarRes(@ColorRes val color: Int, val placeholder: Int) {
    BLUE(R.color.avatar_blue, R.drawable.bg_image_6),
    PURPLE(R.color.avatar_purple, R.drawable.bg_image_3),
    GREEN(R.color.avatar_green, R.drawable.bg_image_9)
}

@Composable
fun Avatar(
    size: Dp,
    imgUrl: String? = null,
    char: Char? = null,
    inactive: Boolean? = null,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val statusInactive = colorResource(R.color.status_inactive)
    val statusActive = colorResource(R.color.status_active)

    Box(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .size(size)
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
                clip = true
            }
            .drawWithCache {
                val path = Path()
                path.addOval(
                    Rect(
                        topLeft = Offset.Zero,
                        bottomRight = Offset(this.size.width, this.size.height)
                    )
                )

                onDrawWithContent {
                    clipPath(path) { this@onDrawWithContent.drawContent() }

                    if (inactive != null) {
                        val dotSize = this.size.width / 6f
                        drawCircle(
                            Color.Black,
                            radius = dotSize,
                            center = Offset(
                                x = this.size.width - dotSize,
                                y = this.size.height - dotSize
                            ),
                            blendMode = BlendMode.Clear
                        )
                        drawCircle(
                            if (inactive) statusInactive else statusActive,
                            radius = 0.8f * dotSize,
                            center = Offset(
                                x = this.size.width - dotSize,
                                y = this.size.height - dotSize
                            )
                        )
                    }
                }
            }
    ) {

        if (imgUrl != null) {
            AsyncImage(
                model = imgUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        } else {
            Placeholder(size, char ?: 'U')
        }

    }
}

@Composable
private fun Placeholder(size: Dp, char: Char) {
    val resources = AvatarRes.entries[char.hashCode() % 3]

    Box {
        Image(
            painter = painterResource(resources.placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .background(color = colorResource(resources.color))
                .offset(x = -size/8, y = size/6),
            colorFilter = ColorFilter.tint(colorResource(R.color.grey_filter))
        )
        Text(
            "$char",
            fontWeight = FontWeight.ExtraBold,
            fontSize = (size.value/3).sp,
            textAlign = TextAlign.Center,
            color = colorResource(R.color.white),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}