package com.example.kalkulator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.kalkulator.databinding.ActivityMainBinding
import java.text.DecimalFormat
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isResultDisplayed = false
    private var lastResult: Double? = null
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.mytoolbar)
        setSupportActionBar(toolbar)

        with(binding) {
            button0.setOnClickListener(ButtonClickListener(isResultDisplayed, lastResult, binding))
            button1.setOnClickListener(ButtonClickListener(isResultDisplayed, lastResult, binding))
            button2.setOnClickListener(ButtonClickListener(isResultDisplayed, lastResult, binding))
            button3.setOnClickListener(ButtonClickListener(isResultDisplayed, lastResult, binding))
            button4.setOnClickListener(ButtonClickListener(isResultDisplayed, lastResult, binding))
            button5.setOnClickListener(ButtonClickListener(isResultDisplayed, lastResult, binding))
            button6.setOnClickListener(ButtonClickListener(isResultDisplayed, lastResult, binding))
            button7.setOnClickListener(ButtonClickListener(isResultDisplayed, lastResult, binding))
            button8.setOnClickListener(ButtonClickListener(isResultDisplayed, lastResult, binding))
            button9.setOnClickListener(ButtonClickListener(isResultDisplayed, lastResult, binding))
            buttonDecimal.setOnClickListener(ButtonClickListener(isResultDisplayed, lastResult, binding))
            buttonPlus.setOnClickListener(ButtonClickListener(isResultDisplayed, lastResult, binding))
            buttonMinus.setOnClickListener(ButtonClickListener(isResultDisplayed, lastResult, binding))
            buttonMultiply.setOnClickListener(ButtonClickListener(isResultDisplayed, lastResult, binding))
            buttonDivide.setOnClickListener(ButtonClickListener(isResultDisplayed, lastResult, binding))
            buttonOpenBracket.setOnClickListener(ButtonClickListener(isResultDisplayed, lastResult, binding))
            buttonCloseBracket.setOnClickListener(ButtonClickListener(isResultDisplayed, lastResult, binding))
            buttonClear.setOnClickListener { clearBannerText() }
            buttonEquals.setOnClickListener { evaluateExpression() }
            buttonDelete.setOnClickListener { deleteLastDigit() }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun evaluateExpression() {
        val expression = binding.bannerTextView.text.toString()

        try {
            val validator = ExpressionValidator()
            validator.validateExpression(expression)

            val evaluator = ExpressionEvaluator()
            val result = evaluator.calculateExpression(expression)

            val formattedResult = if (result.isWholeNumber()) {
                result.toLong().toString()
            } else {
                DecimalFormat("#.#####").format(result)
            }

            binding.bannerTextView.text = formattedResult
            isResultDisplayed = true
            lastResult = result

        } catch (e: Exception) {
            binding.bannerTextView.text = "Error"
            isResultDisplayed = true
        }
    }

    private fun clearBannerText() {
        binding.bannerTextView.text = ""
        isResultDisplayed = false
    }

    private fun deleteLastDigit() {
        val expression = binding.bannerTextView.text.toString()
        if (expression.isNotEmpty()) {
            val newExpression = expression.dropLast(1)
            binding.bannerTextView.text = newExpression
        }
    }

    private inner class ButtonClickListener(
        private val isResultDisplayed: Boolean,
        private val lastResult: Double?,
        private val binding: ActivityMainBinding
    ) : View.OnClickListener {

        override fun onClick(view: View) {
            val button = view as Button
            if (isResultDisplayed) {
                clearBannerText()
                this@MainActivity.isResultDisplayed = false
            }

            if (lastResult != null) {
                binding.bannerTextView.append(lastResult.toString())
                this@MainActivity.lastResult = null
            }

            appendToBannerText(button.text.toString())
        }

        private fun appendToBannerText(text: String) {
            if (isResultDisplayed) {
                clearBannerText()
                this@MainActivity.isResultDisplayed = false
            }
            binding.bannerTextView.append(text)
        }

        private fun clearBannerText() {
            binding.bannerTextView.text = ""
            this@MainActivity.isResultDisplayed = false
        }
    }

    private fun Double.isWholeNumber(): Boolean {
        return this % 1 == 0.0
    }
}
