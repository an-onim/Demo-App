package ru.social.core.ui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import ru.social.core.ui.R
import ru.social.core.ui.common.components.buttons.CButton
import ru.social.core.ui.common.components.buttons.CTextButton
import ru.social.core.ui.common.theme.SDTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CAlertDialog(
    isDialogVisible: Boolean,
    onDismissRequest: () -> Unit,
    title: String? = null,
    subTitle: String? = null,
    onBack: (() -> Unit)? = null,
    actionLabel: String,
    onAction: (() -> Unit)? = null
) {
    if (isDialogVisible) {
        BasicAlertDialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .clip(SDTheme.shapes.corners)
                    .background(color = SDTheme.colors.bgPrimary)
                    .wrapContentSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                title?.let {
                    Text(
                        it,
                        style = SDTheme.typography.headingM,
                        color = SDTheme.colors.fgPrimary,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(Modifier.height(8.dp))
                subTitle?.let {
                    Text(
                        it,
                        style = SDTheme.typography.bookL,
                        color = SDTheme.colors.fgSecondary,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(Modifier.height(16.dp))
                Row {
                    CTextButton(
                        label = stringResource(R.string.back),
                        onClick = onBack
                    )
                    Spacer(Modifier.width(16.dp))
                    CButton(
                        label = actionLabel,
                        onClick = onAction
                    )
                }
            }
        }
    }
}