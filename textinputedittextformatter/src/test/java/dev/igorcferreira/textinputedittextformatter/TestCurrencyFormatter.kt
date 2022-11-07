/*
 * Copyright (c) 2022 Igor Ferreira.
 */
package dev.igorcferreira.textinputedittextformatter

import dev.igorcferreira.textinputedittextformatter.formatter.CurrencyFormatter
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale

class TestCurrencyFormatter {

    @Test
    fun `when a currency formatter is on pt_BR locale, the symbol should be correct stripped`() {
        val formatter = CurrencyFormatter(
            currencyCode = "BRL",
            eraseSingleSymbol = true,
            locale = Locale.forLanguageTag("pt_BR")
        )

        val base = "R\$ "
        val formatted = formatter.format(base)

        assertEquals("", formatted)
    }

    @Test
    fun `when a currency formatter is not requesting to erase the symbol, the symbol should be returned`() {
        val formatter = CurrencyFormatter(
            currencyCode = "USD",
            eraseSingleSymbol = false,
            locale = Locale.ENGLISH
        )

        val base = "$"
        val formatted = formatter.format(base)

        assertEquals("$", formatted)
    }

    @Test
    fun `when an invalid string is passed, the formatter should indicate failure`() {
        val formatter = CurrencyFormatter(
            currencyCode = "USD",
            locale = Locale.ENGLISH
        )

        val base = "This is not a valid string"
        val formatted = formatter.format(base)

        assertEquals(null, formatted)
    }

    @Test
    fun `when passing a double string, the formatter should return a valid parsed item`() {
        val formatter = CurrencyFormatter(
            currencyCode = "USD",
            locale = Locale.ENGLISH
        )
        val base = "1234.56"
        val formatted = formatter.format(base)

        assertEquals("$1,234.56", formatted)
    }

    @Test
    fun `when passing a double string, the formatter should return a valid parsed item regardless of Locale`() {
        val formatter = CurrencyFormatter(
            currencyCode = "USD",
            locale = Locale.forLanguageTag("pt_BR")
        )
        val base = "1234.56"
        val formatted = formatter.format(base)

        assertEquals("US\$ 1,234.56", formatted)
    }

    @Test
    fun `when passing a formatted string, the formatter should not breaking the value`() {
        val formatter = CurrencyFormatter(
            currencyCode = "USD",
            locale = Locale.ENGLISH
        )
        val base = "$1,234.56"
        val formatted = formatter.format(base)

        assertEquals("$1,234.56", formatted)
    }

    @Test
    fun `when passing a formatted string, the formatter should not breaking the value regardless of Locale`() {
        val formatter = CurrencyFormatter(
            currencyCode = "USD",
            locale = Locale.forLanguageTag("pt_BR")
        )
        val base = "US\$ 1,234.56"
        val formatted = formatter.format(base)

        assertEquals("US\$ 1,234.56", formatted)
    }

    @Test
    fun `when passing a partially formatted string, the formatter should return a correctly formatted value`() {
        val formatter = CurrencyFormatter(
            currencyCode = "USD",
            locale = Locale.ENGLISH
        )
        val base = "$1234.56"
        val formatted = formatter.format(base)

        assertEquals("$1,234.56", formatted)
    }

    @Test
    fun `when passing a partially formatted string, the formatter should return a correctly formatted value regardless of Locale`() {
        val formatter = CurrencyFormatter(
            currencyCode = "USD",
            locale = Locale.forLanguageTag("pt_BR")
        )
        val base = "US\$ 1234.56"
        val formatted = formatter.format(base)

        assertEquals("US\$ 1,234.56", formatted)
    }

    @Test
    fun `when passing a string with partial decimals, formatter should accept it and return the partial decimal`() {
        val formatter = CurrencyFormatter(
            currencyCode = "USD",
            locale = Locale.ENGLISH
        )

        assertEquals("$1,234", formatter.format("$1,234"))
        assertEquals("$1,234.", formatter.format("$1,234."))
        assertEquals("$1,234.0", formatter.format("$1,234.0"))

        assertEquals("$1,234", formatter.format("$1234"))
        assertEquals("$1,234.", formatter.format("$1234."))
        assertEquals("$1,234.0", formatter.format("$1234.0"))

        assertEquals("$1,234", formatter.format("1234"))
        assertEquals("$1,234.", formatter.format("1234."))
        assertEquals("$1,234.0", formatter.format("1234.0"))
    }

    @Test
    fun `when a minimumFractionDigits is set, the formatter should respect it`() {
        val formatter = CurrencyFormatter(
            currencyCode = "USD",
            minimumFractionDigits = 2,
            locale = Locale.ENGLISH
        )

        assertEquals("$4.00", formatter.format("4"))
        assertEquals("$4.00", formatter.format("$4"))

        assertEquals("$1,234.00", formatter.format("1234"))
        assertEquals("$1,234.00", formatter.format("$1234"))
        assertEquals("$1,234.00", formatter.format("$1,234"))

        assertEquals("$4.20", formatter.format("4.2"))
        assertEquals("$4.20", formatter.format("$4.2"))
        assertEquals("$4.20", formatter.format("4.20"))
        assertEquals("$4.20", formatter.format("$4.20"))

        assertEquals("$1,234.20", formatter.format("1234.2"))
        assertEquals("$1,234.20", formatter.format("$1234.2"))
        assertEquals("$1,234.20", formatter.format("1234.20"))
        assertEquals("$1,234.20", formatter.format("$1234.20"))
    }

    @Test
    fun `when a maximumFractionDigits is set, the formatter should respect it`() {
        val formatter = CurrencyFormatter(
            currencyCode = "USD",
            maximumFractionDigits = 2,
            locale = Locale.ENGLISH
        )

        assertEquals("$4.12", formatter.format("4.123"))
        assertEquals("$4.12", formatter.format("$4.123"))

        assertEquals("$4.13", formatter.format("4.128"))
        assertEquals("$4.13", formatter.format("$4.128"))

        assertEquals("$1,234.12", formatter.format("1234.123"))
        assertEquals("$1,234.12", formatter.format("$1234.123"))
        assertEquals("$1,234.12", formatter.format("$1,234.123"))

        assertEquals("$1,234.13", formatter.format("1234.128"))
        assertEquals("$1,234.13", formatter.format("$1234.128"))
        assertEquals("$1,234.13", formatter.format("$1,234.128"))
    }

    @Test
    fun `when both a minimumFractionDigits and a maximumFractionDigits are set, the formatter should respect it`() {
        val formatter = CurrencyFormatter(
            currencyCode = "USD",
            minimumFractionDigits = 2,
            maximumFractionDigits = 3,
            locale = Locale.ENGLISH
        )

        assertEquals("$4.00", formatter.format("4"))
        assertEquals("$4.00", formatter.format("$4"))

        assertEquals("$1,234.00", formatter.format("1234"))
        assertEquals("$1,234.00", formatter.format("$1234"))
        assertEquals("$1,234.00", formatter.format("$1,234"))

        assertEquals("$4.20", formatter.format("4.2"))
        assertEquals("$4.20", formatter.format("$4.2"))
        assertEquals("$4.20", formatter.format("4.20"))
        assertEquals("$4.20", formatter.format("$4.20"))

        assertEquals("$1,234.20", formatter.format("1234.2"))
        assertEquals("$1,234.20", formatter.format("$1234.2"))
        assertEquals("$1,234.20", formatter.format("1234.20"))
        assertEquals("$1,234.20", formatter.format("$1234.20"))

        assertEquals("$4.123", formatter.format("4.1234"))
        assertEquals("$4.123", formatter.format("$4.1234"))

        assertEquals("$4.124", formatter.format("4.1238"))
        assertEquals("$4.124", formatter.format("$4.1238"))

        assertEquals("$1,234.123", formatter.format("1234.1234"))
        assertEquals("$1,234.123", formatter.format("$1234.1234"))
        assertEquals("$1,234.123", formatter.format("$1,234.1234"))

        assertEquals("$1,234.124", formatter.format("1234.1238"))
        assertEquals("$1,234.124", formatter.format("$1234.1238"))
        assertEquals("$1,234.124", formatter.format("$1,234.1238"))
    }

    @Test
    fun `when a pound value is set, the cleanup code should return a valid double`() {
        val locale = Locale.UK
        val formatter = CurrencyFormatter(
            currencyCode = "GBP",
            eraseSingleSymbol = true,
            locale = locale
        )

        assertEquals(123_456_789.789, formatter.extractDouble("£123,456.789"))
        assertEquals(123_456_789.0, formatter.extractDouble("£123,456,789"))
    }
}