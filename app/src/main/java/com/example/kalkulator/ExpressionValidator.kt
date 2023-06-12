package com.example.kalkulator

class ExpressionValidator {

    fun validateExpression(expression: String) {
        validateCharacters(expression)
        validateParentheses(expression)
        validateOperators(expression)
        validateDivisionByZero(expression)
    }

    private fun validateCharacters(expression: String) {
        val allowedCharactersRegex = "[0-9+\\-*/.()]+".toRegex()
        if (!expression.matches(allowedCharactersRegex)) {
            throw IllegalArgumentException("Invalid characters in the expression")
        }
    }

    private fun validateParentheses(expression: String) {
        var parenthesesBalance = 0
        for (char in expression) {
            when (char) {
                '(' -> parenthesesBalance++
                ')' -> parenthesesBalance--
            }

            if (parenthesesBalance < 0) {
                throw IllegalArgumentException("Unbalanced parentheses in the expression")
            }
        }

        if (parenthesesBalance != 0) {
            throw IllegalArgumentException("Unbalanced parentheses in the expression")
        }
    }

    private fun validateOperators(expression: String) {
        val operatorsRegex = "[+\\-*/]+".toRegex()
        if (operatorsRegex.containsMatchIn(expression)) {
            val lastChar = expression.last()
            if (lastChar.isOperator()) {
                throw IllegalArgumentException("Invalid operator placement in the expression")
            }
        } else {
            throw IllegalArgumentException("No operators found in the expression")
        }
    }

    private fun validateDivisionByZero(expression: String) {
        val divisionByZeroRegex = "/(0[.0]*)".toRegex()
        if (divisionByZeroRegex.containsMatchIn(expression)) {
            throw IllegalArgumentException("Division by zero is not allowed")
        }
    }

    private fun Char.isOperator(): Boolean {
        return this == '+' || this == '-' || this == '*' || this == '/'
    }

}
