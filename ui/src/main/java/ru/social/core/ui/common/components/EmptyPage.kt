package ru.social.core.ui.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.social.core.ui.R
import ru.social.core.ui.common.theme.SDTheme

@Composable
fun EmptyPage(
    title: String,
    description: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(R.drawable.empty_state_image),
            null
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            title,
            style = SDTheme.typography.headingM,
            color = SDTheme.colors.fgPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            description,
            style = SDTheme.typography.bookL,
            color = SDTheme.colors.fgSecondary,
            textAlign = TextAlign.Center
        )
    }

}