package ru.social.core.ui.common.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

val Shape = SDShape(
    corners = RoundedCornerShape(20.dp),
    textCorners = RoundedCornerShape(16.dp),
    buttonCorners = RoundedCornerShape(100.dp),
    appBarCorners = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
)