/*
 * Copyright (c) 2022 Igor Ferreira.
 */

package dev.igorcferreira.textinputedittextformatter.extension

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

internal val String.emptyDecimalSuffix: String?
    get() {
        val regex = Regex("(\\.[0]*)\$")
        val match = regex.find(this)
        return match?.value
    }

private fun getFormatter(
    currencyCode: String,
    minimumFractionDigits: Int,
    maximumFractionDigits: Int,
    locale: Locale
): NumberFormat {
    val format = NumberFormat.getCurrencyInstance(locale)
    format.currency = Currency.getInstance(currencyCode)
    format.minimumFractionDigits = minimumFractionDigits
    format.maximumFractionDigits = maximumFractionDigits
    return format
}

internal fun Double.formatForCurrency(
    currencyCode: String,
    minimumFractionDigits: Int = 0,
    maximumFractionDigits: Int = Int.MAX_VALUE,
    locale: Locale = Locale.getDefault(Locale.Category.DISPLAY)
): String {
    return getFormatter(
        currencyCode,
        minimumFractionDigits,
        maximumFractionDigits,
        locale
    ).format(this)
}

internal fun String.isSymbolOfCurrency(
    currencyCode: String,
    locale: Locale = Locale.getDefault(Locale.Category.DISPLAY)
): Boolean {
    val formatter = getFormatter(
        currencyCode = currencyCode,
        minimumFractionDigits = 0,
        maximumFractionDigits = Int.MAX_VALUE,
        locale = locale
    )
    //We attempt to get the currency symbol from the underlying DecimalFormat
    //type first to ensure that Locale is correctly respected.
    val symbol = (formatter as? DecimalFormat)
        ?.decimalFormatSymbols
        ?.currencySymbol
        ?: formatter.currency?.symbol
    return this.trim() == symbol
}

internal fun String.formatAsCurrency(
    currencyCode: String,
    minimumFractionDigits: Int = 0,
    maximumFractionDigits: Int = Int.MAX_VALUE,
    locale: Locale = Locale.getDefault(Locale.Category.DISPLAY)
): Double? {
    return try {
        getFormatter(currencyCode, minimumFractionDigits, maximumFractionDigits, locale)
            .parse(this)
            ?.toDouble()
    } catch (ex: Exception) {
        null
    }
}