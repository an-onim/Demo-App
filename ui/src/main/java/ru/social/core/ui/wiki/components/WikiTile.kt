package ru.social.core.ui.wiki.components

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import ru.social.demo.R
import ru.social.demo.ui.components.containers.OutlinedContainer
import ru.social.demo.ui.theme.SDTheme


enum class WikiTypeRes(
    @ColorRes val color: Int,
    @DrawableRes val bgImage: Int,
    @StringRes val titleId: Int,
    @StringRes val descriptionId: Int?
) {
    COMPENDIUM(R.color.wiki_compendium, R.drawable.bg_image_1, R.string.wiki_compendium, R.string.wiki_compendium_desc),
    EVENT(R.color.wiki_event, R.drawable.bg_image_7, R.string.events, R.string.wiki_event_desc),
    WORLD(R.color.wiki_world, R.drawable.bg_image_6, R.string.worlds, R.string.wiki_world_desc),
    CHARACTER(R.color.wiki_character, R.drawable.bg_image_1, R.string.characters, R.string.wiki_character_desc),
    TALE(R.color.wiki_tale, R.drawable.bg_image_3, R.string.wiki_tales, R.string.wiki_tales_desc)
}

@Composable
fun WikiTile(
    type: WikiTypeRes,
    onClick: () -> Unit = {}
) {
    OutlinedContainer(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
    ) {

        val tileHeight = 108.dp
        val bgSize = 160.dp
        val imgSize = 0.56*bgSize

        Box(
            modifier = Modifier
                .height(tileHeight)
                .fillMaxWidth()
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                    clip = true
                }
                .drawWithCache {
                    val path = Path()
                    path.addRect(
                        Rect(
                            topLeft = Offset.Zero,
                            bottomRight = Offset(this.size.width, this.size.height)
                        )
                    )
                    onDrawWithContent {
                        clipPath(path) { this@onDrawWithContent.drawContent() }
                    }
                }
        ) {
            Image(
                painter = painterResource(type.bgImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = bgSize, height = bgSize)
                    .align(Alignment.CenterStart)
                    .offset(x = -0.4*bgSize),
                colorFilter = ColorFilter.tint(colorResource(type.color))
            )
        }

        Row(
            modifier = Modifier.matchParentSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painterResource(R.drawable.book),
                null,
                modifier = Modifier.size(imgSize).padding(horizontal = 12.dp)
            )
            Row(
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp, start = 12.dp, end = 20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.weight(9f)) {
                    Text(
                        stringResource(type.titleId),
                        style = SDTheme.typography.headingS,
                        color = SDTheme.colors.fgPrimary
                    )
                    type.descriptionId?.let {
                        Spacer(Modifier.height(4.dp))
                        Text(
                            stringResource(it),
                            style = SDTheme.typography.bodyMediumM,
                            color = SDTheme.colors.fgSecondary,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Image(
                    painterResource(R.drawable.ic_arrow_right),
                    null,
                    colorFilter = ColorFilter.tint(color = SDTheme.colors.fgSecondary),
                    modifier = Modifier.weight(1f)
                )
            }
        }

    }
}
