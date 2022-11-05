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

    /**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * are about to be replaced by new text with length <code>after</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     */
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    /**
     * This method is called to notify you that, somewhere within
     * <code>s</code>, the text has been changed.
     * It is legitimate to make further changes to <code>s</code> from
     * this callback, but be careful not to get yourself into an infinite
     * loop, because any changes you make will cause this method to be
     * called again recursively.
     * (You are not told where the change took place because other
     * afterTextChanged() methods may already have made other changes
     * and invalidated the offsets.  But if you need to know here,
     * you can use {@link Spannable#setSpan} in {@link #onTextChanged}
     * to mark your place and then look up from here where the span
     * ended up.
     */
    override fun afterTextChanged(s: Editable?) {}

    /**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * have just replaced old text that had length <code>before</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     */
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