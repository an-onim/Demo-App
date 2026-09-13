package ru.social.core.ui.common.components.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.social.core.ui.R

@Composable
fun DataTextField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    RoundedTextField(
        value = value,
        hint = stringResource(R.string.birthday),
        visualTransformation = MaskTransformation(MaskTransformation.DATA_MASK),
        onValueChange = {
            if (it.length <= MaskTransformation.DATA_MASK_COUNT) {
                onValueChange.invoke(it)
            }
        }
    )
}