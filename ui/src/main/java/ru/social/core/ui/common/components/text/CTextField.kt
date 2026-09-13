package ru.social.core.ui.common.components.text

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import ru.social.core.ui.R
import ru.social.core.ui.common.theme.SDTheme
import ru.social.core.ui.common.utils.conditional
import ru.social.core.ui.common.utils.verticalScrollbar

@Composable
fun CTextField(
    modifier: Modifier = Modifier,
    value: String,
    singleLine: Boolean = false,
    textStyle: TextStyle = SDTheme.typography.bookL,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
) {
    val scrollbarColor = SDTheme.colors.fgActionEmphasis

    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = SDTheme.colors.fgPrimary,
            backgroundColor = SDTheme.colors.fgActionEmphasis.copy(alpha = 0.3f)
        )
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            singleLine = singleLine,
            decorationBox = { innerTextField ->
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .conditional(!singleLine) {
                            verticalScrollbar(scrollState, color = scrollbarColor)
                            .verticalScroll(state = scrollState)
                        }

                ) {
                    if (value.isEmpty())
                        Text(
                            stringResource(R.string.text_field_placeholder),
                            color = SDTheme.colors.fgTertiary
                        )
                    innerTextField()
                }

            },
            textStyle = textStyle.copy(color = SDTheme.colors.fgPrimary),
            cursorBrush = SolidColor(SDTheme.colors.fgPrimary),
            visualTransformation = visualTransformation
        )
    }
}
