package ru.social.core.ui.common.components.text

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle


@Composable
fun ExpandableText(
    text: String,
    style: TextStyle = LocalTextStyle.current,
    color: Color = Color.Unspecified,
    collapsedMaxLine: Int = 3,
    showMoreText: String = " More",
    showMoreStyle: SpanStyle = SpanStyle(),
    showLessText: String = " Less",
    showLessStyle: SpanStyle = showMoreStyle,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var isExpanded by remember { mutableStateOf(false) }
    var clickable by remember { mutableStateOf(false) }
    var lastVisibleIndex by remember { mutableIntStateOf(0) }  // TODO: when I added post with 1-line string - exception!

    Box(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { isExpanded = !isExpanded }
            )
            .then(modifier)
    ) {
        Text(
            text = buildAnnotatedString {
                if (clickable) {
                    if (isExpanded) {
                        append(text)
                        withStyle(showLessStyle) {append(showLessText)}
                    } else {
                        val adjustText = text
                            .substring(startIndex = 0, endIndex = lastVisibleIndex)
                            .dropLast(showMoreText.length)
                            .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                        append(adjustText)
                        withStyle(showMoreStyle) { append(showMoreText) }
                    }
                } else {
                    append(text)
                }
            },
            style = style,
            color = color,
            modifier = Modifier.fillMaxWidth().animateContentSize(),
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
            onTextLayout = { result: TextLayoutResult ->
                if (!isExpanded && result.hasVisualOverflow) {
                    clickable = true
                    lastVisibleIndex = result.getLineEnd(collapsedMaxLine - 1)
                }
            }
        )
    }
}
