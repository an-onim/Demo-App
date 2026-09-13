package ru.social.core.ui.common.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.social.core.ui.common.theme.SDTheme


@Composable
fun CButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    iconId: Int? = null,
    label: String? = null,
    onClick: (() -> Unit)? = null
) {
    Button(
        modifier = modifier,
        enabled = enabled && onClick != null,
        onClick = { onClick?.invoke() },
        shape = SDTheme.shapes.buttonCorners,
        colors = ButtonColors(
            containerColor = SDTheme.colors.bgActionPrimary,
            contentColor = SDTheme.colors.fgOnColor,
            disabledContainerColor = SDTheme.colors.bgActionPrimary.copy(alpha = 0.5f),
            disabledContentColor = SDTheme.colors.fgOnColor
        )
    ) {
        if (iconId != null)
            Image(painter = painterResource(iconId), null)
        if (label != null)
            Text("$label", style = SDTheme.typography.bodyBoldL)
    }
}

@Composable
fun CTonalButton(
    enabled: Boolean = true,
    onClick: () -> Unit,
    iconId: Int? = null,
    label: String? = null
) {
    FilledTonalButton (
        enabled = enabled,
        onClick = onClick,
        shape = SDTheme.shapes.buttonCorners,
        colors = ButtonColors(
            containerColor = SDTheme.colors.bgHighlight,
            contentColor = SDTheme.colors.fgActionEmphasis,
            disabledContainerColor = SDTheme.colors.bgTertiary,
            disabledContentColor = SDTheme.colors.fgTertiary
        )
    ) {
        if (iconId != null)
            Image(painter = painterResource(iconId), null)
        if (label != null)
            Text("$label", style = SDTheme.typography.bodyBoldL)
    }
}

@Composable
fun COutlinedButton(
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    iconId: Int? = null,
    label: String? = null
) {
    OutlinedButton (
        enabled = enabled && onClick != null,
        onClick = { onClick?.invoke() },
        shape = SDTheme.shapes.buttonCorners,
        border = BorderStroke(width = 1.5.dp, color = SDTheme.colors.borderColor),
        colors =  ButtonDefaults.outlinedButtonColors(
            contentColor = SDTheme.colors.fgActionEmphasis,
            disabledContentColor = SDTheme.colors.fgTertiary
        )
    ) {
        if (iconId != null)
            Image(painter = painterResource(iconId), null)
        if (label != null)
            Text("$label", style = SDTheme.typography.bodyBoldL)
    }
}

@Composable
fun CTextButton(
    label: String,
    enabled: Boolean = true,
    contentColor: Color = SDTheme.colors.fgActionEmphasis,
    onClick: (() -> Unit)? = null
) {
    TextButton (
        enabled = enabled && onClick != null,
        onClick = { onClick?.invoke() },
        colors =  ButtonDefaults.outlinedButtonColors(
            contentColor = contentColor,
            disabledContentColor = SDTheme.colors.fgTertiary
        )
    ) {
        Text(label, style = SDTheme.typography.bodyBoldL)
    }
}


@Composable
fun CIconButton(
    iconId: Int,
    enabled: Boolean = true,
    size: Dp = 40.dp,
    contentColor: Color = SDTheme.colors.fgPrimary,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    CIconButtonInternal(
        iconId = iconId,
        enabled = enabled,
        size = size,
        bgColor = Color.Transparent,
        borderColor = Color.Transparent,
        contentColor = contentColor,
        modifier = modifier,
        onClick = onClick
    )
}

@Composable
fun CIconButtonFilled(
    iconId: Int,
    enabled: Boolean = true,
    size: Dp = 40.dp,
    bgColor: Color = SDTheme.colors.bgActionPrimary,
    contentColor: Color = SDTheme.colors.fgActionPrimary,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    CIconButtonInternal(
        iconId = iconId,
        enabled = enabled,
        size = size,
        bgColor = bgColor,
        borderColor = Color.Transparent,
        contentColor = contentColor,
        modifier = modifier,
        onClick = onClick
    )
}

@Composable
fun CIconButtonOutlined(
    iconId: Int,
    enabled: Boolean = true,
    size: Dp = 40.dp,
    bgColor: Color = Color.Transparent,
    borderColor: Color = SDTheme.colors.borderColor,
    contentColor: Color = SDTheme.colors.fgPrimary,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    CIconButtonInternal(
        iconId = iconId,
        enabled = enabled,
        size = size,
        bgColor = bgColor,
        borderColor = borderColor,
        contentColor = contentColor,
        modifier = modifier,
        onClick = onClick
    )
}

@Composable
private fun CIconButtonInternal(
    iconId: Int,
    enabled: Boolean = true,
    size: Dp = 40.dp,
    bgColor: Color,
    borderColor: Color,
    contentColor: Color,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    IconButton (
        modifier = modifier
            .border(1.5.dp, borderColor, shape = CircleShape)
            .background(color = bgColor, shape = CircleShape)
            .size(size)
            .padding(size/4),
        enabled = enabled,
        onClick = onClick,
    ) {
        Image(
            painter = painterResource(iconId),
            null,
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.tint(color = contentColor)
        )
    }
}