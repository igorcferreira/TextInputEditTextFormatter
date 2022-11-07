package dev.igorcferreira.textinputedittextformatter

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.text.input.KeyboardType
import dev.igorcferreira.textinputedittextformatter.compose.InputFormatVisualTransformation
import dev.igorcferreira.textinputedittextformatter.formatter.InputFormatter

class FormatterMaterialOutlinedTextField constructor(
    context: Context,
    formatter: InputFormatter,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AbstractComposeView(context, attrs, defStyle) {

    @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
    ): this(context, InputFormatter.None, attrs, defStyle)

    private var _formatter = mutableStateOf(formatter)
    private var _input: MutableState<String> = mutableStateOf("")

    var input: String
        get() = _input.value
        set(value) { _input.value = value }

    var formatter: InputFormatter
        get() = _formatter.value
        set(value) { _formatter.value = value }

    @Composable
    override fun Content() {
        var composeInput by remember { _input }
        val formatterState by remember { _formatter }

        MaterialTheme {
            OutlinedTextField(
                value = composeInput,
                onValueChange = { composeInput = it },
                visualTransformation = InputFormatVisualTransformation(formatterState),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}