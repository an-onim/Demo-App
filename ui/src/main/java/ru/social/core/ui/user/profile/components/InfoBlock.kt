package ru.social.core.ui.user.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.social.demo.ui.theme.SDTheme

@Composable
fun InfoBlock(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            title,
            style = SDTheme.typography.headingS,
            color = SDTheme.colors.fgPrimary,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        content()
    }
}
