package dev.igorcferreira.textinputedittextformatter.compose

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import dev.igorcferreira.textinputedittextformatter.formatter.InputFormatter

class InputFormatVisualTransformation(
    private val formatter: InputFormatter
): VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val original = text.text
        val transformed = formatter.format(original)
            ?: return TransformedText(text, OffsetMapping.Identity)
        val delta = transformed.length - original.length
        return TransformedText(AnnotatedString(transformed), object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int = offset + delta
            override fun transformedToOriginal(offset: Int): Int = offset - delta
        })
    }
}