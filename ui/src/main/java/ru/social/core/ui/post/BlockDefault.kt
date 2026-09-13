package ru.social.core.ui.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import ru.social.demo.R
import ru.social.core.base.data.model.User
import ru.social.core.network.utils.parseDate
import ru.social.demo.ui.components.Avatar
import ru.social.demo.ui.components.text.ExpandableText
import ru.social.demo.ui.components.LabelTile
import ru.social.demo.ui.components.LabelType
import ru.social.demo.ui.components.buttons.CIconButton
import ru.social.demo.ui.theme.SDTheme

@Composable
fun UserBlock(
    user: User?,
    createDate: Timestamp?,
    onEdit: (() -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Avatar(
            char = user?.name?.get(0) ?: 'U',
            imgUrl = user?.imageUrl,
            size = 36.dp
        )

        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                user?.name ?: "",
                style = SDTheme.typography.bodyMediumM,
                color = SDTheme.colors.fgPrimary
            )
            createDate?.let {
                Text(
                    it.parseDate(),
                    style = SDTheme.typography.bodyBoldS,
                    color = SDTheme.colors.fgTertiary
                )
            }

        }

        Spacer(modifier = Modifier.weight(1f))
        MoreButton(onEdit)
    }
}

@Composable
fun TextBlock(title: String?, body: String?) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        title?.let {
            Text(
                it,
                style = SDTheme.typography.headingS,
                color = SDTheme.colors.fgPrimary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        body?.let {
            ExpandableText(
                text = it,
                style = SDTheme.typography.bookL,
                color = SDTheme.colors.fgSecondary,
                showMoreStyle = SpanStyle(color = SDTheme.colors.fgPrimary, fontWeight = FontWeight.W500)
            )
        }

    }
}

@Composable
fun ReactionBlock(reactionsCount: Int?, commentsCount: Int?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Counter(
            image = {
                Image(
                    painterResource(R.drawable.ic_heart),
                    null,
                    colorFilter = ColorFilter.tint(color = SDTheme.colors.fgTertiary)
                )
            },
            reactionsCount ?: 0
        )

        Counter(
            image = {
                Image(
                    painterResource(R.drawable.ic_chat),
                    null,
                    colorFilter = ColorFilter.tint(color = SDTheme.colors.fgTertiary)
                )
            },
            commentsCount ?: 0
        )
    }
}


@Composable
private fun MoreButton(onEdit: (() -> Unit)? = null) {
    var expandedMore by remember { mutableStateOf(false) }

    Column {
        CIconButton(
            iconId = R.drawable.ic_more,
            contentColor = SDTheme.colors.fgSecondary,
            size = 30.dp,
        ) { expandedMore = !expandedMore }

        DropdownMenu(
            modifier = Modifier.wrapContentSize(),
            expanded = expandedMore,
            onDismissRequest = { expandedMore = false },
            shape = SDTheme.shapes.corners,
            containerColor = SDTheme.colors.bgPrimary
        ) {
            onEdit?.let {
                LabelTile(type = LabelType.SMALL, label = "Edit", iconId = R.drawable.ic_edit, onClick = onEdit)
            }
        }
    }
}

@Composable
private fun Counter(
    image: @Composable () -> Unit,
    counter: Int
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        image.invoke()
        Spacer(Modifier.width(4.dp))
        Text(
            "$counter",
            style = SDTheme.typography.bodyMediumS,
            color = SDTheme.colors.fgTertiary
        )

    }
}