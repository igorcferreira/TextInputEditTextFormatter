/*
 * Copyright (c) 2022 Igor Ferreira.
 */

package dev.igorcferreira.textinputedittextformatter.formatter

/**
 * This represents a class to be used by [dev.igorcferreira.textinputedittextformatter.android.material.textfield.TextInputEditTextMask]
 * when formatting user input.
 */
interface InputFormatter {
    /**
     * Method used to format an input text based on the class definition.
     * @param text  Original text input by the user to be validated/formatted.
     * @return Formatted text or null if the text is invalid
     */
    fun format(text: String): String?
}