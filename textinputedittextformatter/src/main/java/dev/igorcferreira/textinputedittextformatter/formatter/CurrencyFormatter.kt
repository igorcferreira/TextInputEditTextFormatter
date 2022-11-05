/*
 * Copyright (c) 2022 Igor Ferreira.
 */
package dev.igorcferreira.textinputedittextformatter.formatter

import dev.igorcferreira.textinputedittextformatter.extension.emptyDecimalSuffix
import dev.igorcferreira.textinputedittextformatter.extension.formatAsCurrency
import dev.igorcferreira.textinputedittextformatter.extension.formatForCurrency
import dev.igorcferreira.textinputedittextformatter.extension.isSymbolOfCurrency
import java.util.Locale

/**
 * Implementation of [InputFormatter] to valdiate and convert text inputs into well formatted
 * currency text. For example, the input "5000" will be converted into "$5,000" for a
 * currencyCode USD.
 *
 * @param currencyCode 3 letter ISO 4217 code for the desired currency.
 * @param eraseSingleSymbol Flag to control if the currency symbol should always be present of if the parser would accept an empty string
 * @param minimumFractionDigits Sets the minimum number of digits allowed in the fraction portion.
 * If set, the result string will have at least this number of fraction digits.
 * For example, for a minimumFractionDigits of 2, the input "500" is converted into "$500.00"
 * @param maximumFractionDigits Sets the maximum. number of digits allowed in the fraction portion.
 * If set, the result string will have at least this number of fraction digits.
 * For example, for a minimumFractionDigits of 2, the input "500.123" is converted into "$500.12"
 * @param locale The desired [Locale] to which the string will be adapted
 *
 * @see [InputFormatter], [dev.igorcferreira.textinputedittextformatter.android.material.textfield.TextInputEditTextMask]
 */
class CurrencyFormatter(
    private val currencyCode: String,
    private val eraseSingleSymbol: Boolean = false,
    private val minimumFractionDigits: Int = 0,
    private val maximumFractionDigits: Int = Int.MAX_VALUE,
    private val locale: Locale = Locale.getDefault(Locale.Category.DISPLAY)
): InputFormatter {

    /**
     * Method used to format an input text based on the class definition.
     * @param text  Original text input by the user to be validated/formatted.
     * @return Formatted text or null if the text is invalid
     */
    override fun format(text: String): String? {
        if (text.isSymbolOfCurrency(currencyCode, locale)) {
            return if (eraseSingleSymbol) "" else text
        }

        /** We are using a more permissive conversion into double to better format the text */
        val double = text.toDoubleOrNull() ?: text.formatAsCurrency(
            currencyCode = currencyCode,
            minimumFractionDigits = 0,
            maximumFractionDigits = Int.MAX_VALUE,
            locale = locale
        )

        return double
            ?.formatForCurrency(
                currencyCode = currencyCode,
                minimumFractionDigits = minimumFractionDigits,
                maximumFractionDigits = maximumFractionDigits,
                locale = locale
            )?.let { "$it${text.emptyDecimalSuffix ?: ""}" }
    }
}