package ru.social.core.ui.common.components.text

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import kotlin.math.absoluteValue


class MaskTransformation(
    private val mask: String,
    private val maxLength: Int = Int.MAX_VALUE
) : VisualTransformation {

    private val specialSymbolsIndices = mask.indices.filter { mask[it] != MASK_SYM }

    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        var maskIndex = 0
        val textTrim = if (text.text.length >= maxLength) text.text.substring(0..< maxLength) else text.text
        textTrim.forEach { char ->
            while (specialSymbolsIndices.contains(maskIndex)) {
                out += mask[maskIndex]
                maskIndex++
            }
            out += char
            maskIndex++
        }
        return TransformedText(AnnotatedString(out), offsetTranslator())
    }

    private fun offsetTranslator() = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            val offsetValue = offset.absoluteValue
            if (offsetValue == 0) return 0
            var numberOfHashtags = 0
            val masked = mask.takeWhile {
                if (it == MASK_SYM) numberOfHashtags++
                numberOfHashtags < offsetValue
            }
            return masked.length + 1
        }

        override fun transformedToOriginal(offset: Int): Int =
            mask.take(offset.absoluteValue).count { it == MASK_SYM }

    }


    companion object {

        private const val MASK_SYM = '#'

        const val DATA_MASK = "##.##.####"
        const val DATA_MASK_COUNT = 8

        fun maskDataText(text: String) = maskText(text, DATA_MASK, MASK_SYM, '.')

        private fun maskText(text: String, mask: String, maskSym: Char = MASK_SYM, constSym: Char): String {
            val result = StringBuilder()

            var i = 0
            var j = 0
            while (i < mask.length && j < text.length) {
                if (mask[i] == maskSym) {
                    result.append(text[j])
                    j++
                } else {
                    result.append(constSym)
                }
                i++
            }

            return result.toString()
        }

    }

}
