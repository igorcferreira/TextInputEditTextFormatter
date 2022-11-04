package dev.igorcferreira.textinputedittextformatter.android.material

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import dev.igorcferreira.textinputedittextformatter.formatter.InputFormatter
import java.lang.ref.WeakReference
import kotlin.math.min

/**
 * Implementation of `TextWatcher` that helps formatting text input by the user.
 * For example, formatting the number `5000` into `$5,000` based on the currency
 * code "USD".
 *
 * @param field [WeakReference] of the [TextInputEditText] that will be observed and updated.
 * @param formatter [InputFormatter] that will be used to validate and format the input string.
 *
 * @see [InputFormatter], [TextInputEditText], [TextWatcher]
 */
class TextInputEditTextMask(
    private val field: WeakReference<TextInputEditText>,
    private val formatter: InputFormatter
): TextWatcher {

    constructor(
        field: TextInputEditText,
        formatter: InputFormatter
    ): this(WeakReference(field), formatter)

    init {
        field.get()?.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable?) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val text = s?.toString() ?: return
        formatter.format(text)?.let {
            update(it, start + count, it.length - text.length)
        }
    }

    private fun update(text: String, position: Int, delta: Int) {
        val target = field.get() ?: return
        target.removeTextChangedListener(this)
        target.setText(text)
        target.addTextChangedListener(this)
        target.setSelection(min(position + delta, text.length))
    }
}