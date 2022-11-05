package dev.igorcferreira.textinputedittextformatter.compose

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import dev.igorcferreira.textinputedittextformatter.formatter.InputFormatter

/**
 * A [VisualTransformation] implementation that applies the format of a specific [InputFormatter]
 * into an [AnnotatedString] provided by Combine's text component.
 *
 * @param formatter [InputFormatter] that will be used to validate and format the input string.
 *
 * @see [InputFormatter], [VisualTransformation]
 */
class InputFormatVisualTransformation(
    private val formatter: InputFormatter
): VisualTransformation {

    /**
     * Change the visual output of given text.
     *
     * Note that the returned text length can be different length from the given text. The composable
     * will call the offset translator for converting offsets for various reasons, cursor drawing
     * position, text selection by gesture, etc.
     *
     * Example: The ASCII only password (replacing with '*' chars)
     *  original text   : thisispassword
     *  transformed text: **************
     *
     * Example: Credit Card Visual Output (inserting hyphens each 4 digits)
     *  original text   : 1234567890123456
     *  transformed text: 1234-5678-9012-3456
     *
     * @param text The original text
     * @return the pair of filtered text and offset translator.
     */
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