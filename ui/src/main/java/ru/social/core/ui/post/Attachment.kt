package ru.social.core.ui.post

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.social.core.ui.common.components.buttons.CIconButtonFilled
import ru.social.core.ui.common.components.containers.DefaultRoundedContainer
import ru.social.core.ui.common.theme.SDTheme
import ru.social.demo.R
import ru.social.demo.ui.components.buttons.CIconButtonFilled
import ru.social.demo.ui.components.containers.DefaultRoundedContainer
import ru.social.demo.ui.theme.SDTheme

@Composable
fun AttachmentMedia(
    uri: Uri,
    onClose: () -> Unit
) {
    AttachmentInternal(
        model = uri,
        onClose = onClose
    )
}

@Composable
fun AttachmentMedia(
    url: String,
    onClose: () -> Unit
) {
    AttachmentInternal(
        model = url,
        contentDescription = url,
        onClose = onClose
    )
}

@Composable
private fun AttachmentInternal(
    onClose: () -> Unit,
    desc: String? = null,
    model: Any?,
    contentDescription: String? = null
) {
    DefaultRoundedContainer(
        modifier = Modifier.size(84.dp).aspectRatio(1f)
    ) {
        AsyncImage(
            model = model,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.img_placeholder)
        )

        Box(Modifier.padding(6.dp).fillMaxSize()) {
            CIconButtonFilled (
                modifier = Modifier.align(Alignment.TopEnd),
                iconId = R.drawable.ic_close,
                size = 16.dp,
                bgColor = SDTheme.colors.fgSecondary,
                contentColor = SDTheme.colors.fgOnColor,
                onClick = onClose
            )

            desc?.let {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .wrapContentSize()
                        .clip(SDTheme.shapes.buttonCorners)
                        .background(color = SDTheme.colors.fgSecondary),
                    text = desc,
                    style = SDTheme.typography.bodyBoldS,
                    color = SDTheme.colors.fgOnColor
                )
            }
        }
    }
}
