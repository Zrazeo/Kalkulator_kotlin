package com.example.kalkulator

import net.objecthunter.exp4j.ExpressionBuilder

class ExpressionEvaluator {

    fun calculateExpression(expression: String): Double {
        val exp = ExpressionBuilder(expression).build()
        return exp.evaluate()
    }

}
