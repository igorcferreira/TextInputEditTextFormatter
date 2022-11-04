/*
 * Copyright (c) 2022 Igor Ferreira.
 */
package dev.igorcferreira.textinputedittextformatter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }

    private fun configureTextInputEditText() {
        binding.inputCompose.formatter = formatter
        binding.textInputEditText.apply {
            addTextChangedListener(TextInputEditTextMask(this, formatter))
        }
    }
}