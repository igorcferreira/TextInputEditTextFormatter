/*
 * Copyright (c) 2022 Igor Ferreira.
 */
package dev.igorcferreira.textinputedittextformatter

import android.app.LocaleManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import dev.igorcferreira.textinputedittextformatter.app.databinding.ActivityMainBinding
import dev.igorcferreira.textinputedittextformatter.android.material.TextInputEditTextMask
import dev.igorcferreira.textinputedittextformatter.formatter.CurrencyFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val formatter = CurrencyFormatter("USD", eraseSingleSymbol = true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureTextInputEditText()
        configureActions()
    }

    private fun configureTextInputEditText() {
        binding.inputCompose.formatter = formatter
        binding.textInputEditText.apply {
            addTextChangedListener(TextInputEditTextMask(this, formatter))
        }
    }

    private fun configureActions() {
        binding.outputTextInputEditText.setOnClickListener {
            binding.textInputEditText.text
                ?.toString()
                //For the TextInputEditText, the text will be formatted.
                //So, to get input double, we use the extractDouble method from the CurrencyFormatter
                ?.let(formatter::extractDouble)
                ?.apply(this::show)
        }
        binding.outputOutlineTextField.setOnClickListener {
            binding.inputCompose.input
                //For the OutlinedTextField, the text will be non-formatted.
                //This is because, for Combine, the SDK gives a Mask.
                //So, we can just work with the raw text
                .let(String::toDoubleOrNull)
                ?.apply(this::show)
        }
    }

    private fun show(currency: Double) = Toast.makeText(
        this,
        "Double value: $currency",
        Toast.LENGTH_SHORT
    ).show()
}