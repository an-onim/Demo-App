package ru.social.core.ui.common.components.text

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.social.core.ui.R
import ru.social.core.ui.common.theme.SDTheme


@Composable
fun RoundedTextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String? = null,
    required: Boolean = false,
    enabled: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
) {

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
            singleLine = true,
            enabled = enabled,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = SDTheme.colors.borderColor,
                            shape = SDTheme.shapes.corners
                        )
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {
                        if (!hint.isNullOrBlank()) {
                            Text(
                                hint,
                                style = SDTheme.typography.caption,
                                color = SDTheme.colors.fgTertiary
                            )
                        }

                        innerTextField()
                    }

                    if (required) {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = stringResource(R.string.required),
                            style = SDTheme.typography.caption,
                            color = SDTheme.colors.fgSecondary
                        )
                    }
                }

            },
            textStyle = SDTheme.typography.bodyMediumM.copy(color = SDTheme.colors.fgPrimary),
            cursorBrush = SolidColor(SDTheme.colors.fgPrimary),
            visualTransformation = visualTransformation
        )
    }
}