package ru.social.core.ui.user.profile.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.social.demo.R
import ru.social.core.base.data.model.User
import ru.social.demo.ui.components.Avatar
import ru.social.demo.ui.components.buttons.CButton
import ru.social.demo.ui.theme.SDTheme

@Composable
fun FriendsInfoBlock(
    users: List<String>? = null
) {
    InfoBlock(title =  stringResource(R.string.friends)) {
        if (users.isNullOrEmpty()) {
            EmptyList()
        } else {
            Box{}
//            users.map { FriendItem(it) }
        }
    }
}

@Composable
private fun EmptyList() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.no_friends_desc),
            style = SDTheme.typography.bookL,
            color = SDTheme.colors.fgSecondary
        )
        Spacer(Modifier.height(12.dp))
        CButton(
            modifier = Modifier.width(260.dp),
            label = stringResource(R.string.add_friends)
        ) { }
    }
}

@Composable
private fun FriendItem(user: User) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Avatar(
            size = 40.dp,
            imgUrl = user.imageUrl,
            char = user.name?.get(0)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            user.name ?: "",
            style = SDTheme.typography.bodyBoldL,
            color = SDTheme.colors.fgPrimary
        )
    }
}