/*
 * Copyright (c) 2022 Igor Ferreira.
 */
package dev.igorcferreira.textinputedittextformatter

import com.google.android.material.textfield.TextInputEditText
import dev.igorcferreira.textinputedittextformatter.android.material.textfield.TextInputEditTextMask
import dev.igorcferreira.textinputedittextformatter.formatter.CurrencyFormatter
import org.junit.Test
import org.mockito.Mockito
import java.util.Locale

class TestTextInputEditTextMask {

    @Test
    fun `when a number is added to the end of string, the cursor should match the result`() {
        val field = Mockito.mock(TextInputEditText::class.java)
        val formatter = CurrencyFormatter(
            currencyCode = "USD",
            eraseSingleSymbol = true,
            locale = Locale.ENGLISH
        )
        val watcher = TextInputEditTextMask(field, formatter)
        Mockito.verify(field, Mockito.times(1)).addTextChangedListener(Mockito.any())

        watcher.onTextChanged("564", 2, 3, 1)

        Mockito.verify(field, Mockito.times(1)).removeTextChangedListener(Mockito.any())
        Mockito.verify(field, Mockito.times(1)).setText(Mockito.eq("$564"))
        Mockito.verify(field, Mockito.times(2)).addTextChangedListener(Mockito.any())
        Mockito.verify(field, Mockito.times(1)).setSelection(Mockito.eq(4))
    }

    @Test
    fun `when a number is added to the middle of the string and adds a group char, the cursor should match the result`() {
        val field = Mockito.mock(TextInputEditText::class.java)
        val formatter = CurrencyFormatter(
            currencyCode = "USD",
            eraseSingleSymbol = true,
            locale = Locale.ENGLISH
        )
        val watcher = TextInputEditTextMask(field, formatter)
        Mockito.verify(field, Mockito.times(1)).addTextChangedListener(Mockito.any())

        watcher.onTextChanged("5604", 2, 3, 1)

        Mockito.verify(field, Mockito.times(1)).removeTextChangedListener(Mockito.any())
        Mockito.verify(field, Mockito.times(1)).setText(Mockito.eq("$5,604"))
        Mockito.verify(field, Mockito.times(2)).addTextChangedListener(Mockito.any())
        Mockito.verify(field, Mockito.times(1)).setSelection(Mockito.eq(5))
    }

    @Test
    fun `when a number is added to the middle of the string with a currency symbol, the cursor should match the result`() {
        val field = Mockito.mock(TextInputEditText::class.java)
        val formatter = CurrencyFormatter(
            currencyCode = "USD",
            eraseSingleSymbol = true,
            locale = Locale.ENGLISH
        )
        val watcher = TextInputEditTextMask(field, formatter)
        Mockito.verify(field, Mockito.times(1)).addTextChangedListener(Mockito.any())

        watcher.onTextChanged("$5604", 3, 4, 1)

        Mockito.verify(field, Mockito.times(1)).removeTextChangedListener(Mockito.any())
        Mockito.verify(field, Mockito.times(1)).setText(Mockito.eq("$5,604"))
        Mockito.verify(field, Mockito.times(2)).addTextChangedListener(Mockito.any())
        Mockito.verify(field, Mockito.times(1)).setSelection(Mockito.eq(5))
    }

    @Test
    fun `when only the symbol is left, the formatter should clean it`() {
        val field = Mockito.mock(TextInputEditText::class.java)
        val formatter = CurrencyFormatter(
            currencyCode = "USD",
            eraseSingleSymbol = true,
            locale = Locale.ENGLISH
        )
        val watcher = TextInputEditTextMask(field, formatter)
        Mockito.verify(field, Mockito.times(1)).addTextChangedListener(Mockito.any())

        watcher.onTextChanged("$", 2, 1, 1)

        Mockito.verify(field, Mockito.times(1)).removeTextChangedListener(Mockito.any())
        Mockito.verify(field, Mockito.times(1)).setText(Mockito.eq(""))
        Mockito.verify(field, Mockito.times(2)).addTextChangedListener(Mockito.any())
        Mockito.verify(field, Mockito.times(1)).setSelection(Mockito.eq(0))
    }

    @Test
    fun `when a number is added to middle of string, the cursor should match the result`() {
        val field = Mockito.mock(TextInputEditText::class.java)
        val formatter = CurrencyFormatter(
            currencyCode = "USD",
            eraseSingleSymbol = true,
            locale = Locale.ENGLISH
        )
        val watcher = TextInputEditTextMask(field, formatter)
        Mockito.verify(field, Mockito.times(1)).addTextChangedListener(Mockito.any())

        watcher.onTextChanged("564", 1, 2, 1)

        Mockito.verify(field, Mockito.times(1)).removeTextChangedListener(Mockito.any())
        Mockito.verify(field, Mockito.times(1)).setText(Mockito.eq("$564"))
        Mockito.verify(field, Mockito.times(2)).addTextChangedListener(Mockito.any())
        Mockito.verify(field, Mockito.times(1)).setSelection(Mockito.eq(3))
    }

    @Test
    fun `when a decimal point is added to the string, the cursor should match the result`() {
        val field = Mockito.mock(TextInputEditText::class.java)
        val formatter = CurrencyFormatter(
            currencyCode = "USD",
            eraseSingleSymbol = true,
            locale = Locale.ENGLISH
        )
        val watcher = TextInputEditTextMask(field, formatter)
        Mockito.verify(field, Mockito.times(1)).addTextChangedListener(Mockito.any())

        watcher.onTextChanged("56.", 2, 3, 1)

        Mockito.verify(field, Mockito.times(1)).removeTextChangedListener(Mockito.any())
        Mockito.verify(field, Mockito.times(1)).setText(Mockito.eq("$56."))
        Mockito.verify(field, Mockito.times(2)).addTextChangedListener(Mockito.any())
        Mockito.verify(field, Mockito.times(1)).setSelection(Mockito.eq(4))
    }

    @Test
    fun `when erasing a decimal point on the string, the cursor should match the result`() {
        val field = Mockito.mock(TextInputEditText::class.java)
        val formatter = CurrencyFormatter(
            currencyCode = "USD",
            eraseSingleSymbol = true,
            locale = Locale.ENGLISH
        )
        val watcher = TextInputEditTextMask(field, formatter)
        Mockito.verify(field, Mockito.times(1)).addTextChangedListener(Mockito.any())

        watcher.onTextChanged("56", 3, 2, 1)

        Mockito.verify(field, Mockito.times(1)).removeTextChangedListener(Mockito.any())
        Mockito.verify(field, Mockito.times(1)).setText(Mockito.eq("$56"))
        Mockito.verify(field, Mockito.times(2)).addTextChangedListener(Mockito.any())
        Mockito.verify(field, Mockito.times(1)).setSelection(Mockito.eq(3))
    }

    @Test
    fun `when the first decimal 0 is added to the string, the cursor should match the result`() {
        val field = Mockito.mock(TextInputEditText::class.java)
        val formatter = CurrencyFormatter(
            currencyCode = "USD",
            eraseSingleSymbol = true,
            locale = Locale.ENGLISH
        )
        val watcher = TextInputEditTextMask(field, formatter)
        Mockito.verify(field, Mockito.times(1)).addTextChangedListener(Mockito.any())

        watcher.onTextChanged("56.0", 3, 4, 1)

        Mockito.verify(field, Mockito.times(1)).removeTextChangedListener(Mockito.any())
        Mockito.verify(field, Mockito.times(1)).setText(Mockito.eq("$56.0"))
        Mockito.verify(field, Mockito.times(2)).addTextChangedListener(Mockito.any())
        Mockito.verify(field, Mockito.times(1)).setSelection(Mockito.eq(5))
    }
}