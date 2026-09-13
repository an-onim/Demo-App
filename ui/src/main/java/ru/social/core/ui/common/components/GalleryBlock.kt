package ru.social.core.ui.common.components

import androidx.compose.foundation.layout.Arrangement
import ru.social.core.ui.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.social.core.ui.common.components.containers.DefaultRoundedContainer
import ru.social.core.ui.common.theme.SDTheme
import ru.social.core.ui.common.utils.conditional


@Composable
fun GalleryBlock(urls: List<String>) {
    val spacer = Arrangement.spacedBy(2.dp)
    val fgImageWithCount = if (urls.size > 4) {
        ColorFilter.tint(color = Color(0x66000000), blendMode = BlendMode.Saturation)
    } else {
        null
    }

    DefaultRoundedContainer(
        modifier = Modifier.height(200.dp),
        parentWidth = true
    ) {
        Row(
            horizontalArrangement = spacer
        ) {
            Column(
                Modifier.weight(1f),
                verticalArrangement = spacer
            ) {
                GalleryImage(urls[0], Modifier.weight(1f))

                if (urls.size > 2) {
                    GalleryImage(urls[2], Modifier.weight(1f))
                }
            }

            Column(
                Modifier.conditional(urls.size > 1) { weight(1f) },
                verticalArrangement = spacer
            ) {
                if (urls.size > 1) {
                    GalleryImage(urls[1], Modifier.weight(1f))
                }

                if (urls.size > 3) {
                    Box(Modifier.weight(1f)) {

                        GalleryImage(urls[3], colorFilter = fgImageWithCount)

                        if (urls.size > 4) {
                            Text(
                                "+${urls.size - 4}",
                                style = SDTheme.typography.headingM,
                                color = SDTheme.colors.fgOnColor,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
private fun GalleryImage(
    url: String,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null
) {
    AsyncImage(
        modifier = modifier,
        model = url,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        colorFilter = colorFilter,
        placeholder = painterResource(R.drawable.img_placeholder),
        error = painterResource(R.drawable.img_placeholder)
    )
}
