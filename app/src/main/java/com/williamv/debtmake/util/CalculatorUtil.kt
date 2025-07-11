package com.williamv.debtmake.util

/**
 * 简单表达式解析与计算工具类，支持 + - × ÷ 括号和小数
 */
object CalculatorUtil {
    fun evaluate(expr: String): String {
        return try {
            val sanitized = expr.replace('×', '*').replace('÷', '/')
            val result = ExpressionParser().parse(sanitized)
            // 避免多余小数点
            if (result % 1.0 == 0.0) {
                result.toLong().toString()
            } else {
                String.format("%.2f", result)
            }
        } catch (e: Exception) {
            ""
        }
    }
}

// 支持基本表达式（仅正数，括号，+ - * /），有小数支持
class ExpressionParser {
    private var pos = -1
    private var ch = 0
    private lateinit var str: String

    fun parse(s: String): Double {
        str = s
        pos = -1
        nextChar()
        val x = parseExpression()
        if (pos < str.length) throw RuntimeException("Unexpected: " + ch.toChar())
        return x
    }

    private fun nextChar() {
        pos++
        ch = if (pos < str.length) str[pos].toInt() else -1
    }

    private fun eat(charToEat: Int): Boolean {
        while (ch == ' '.toInt()) nextChar()
        if (ch == charToEat) {
            nextChar()
            return true
        }
        return false
    }

    // 语法: expression = term | expression `+` term | expression `-` term
    private fun parseExpression(): Double {
        var x = parseTerm()
        while (true) {
            when {
                eat('+'.toInt()) -> x += parseTerm()
                eat('-'.toInt()) -> x -= parseTerm()
                else -> return x
            }
        }
    }

    // 语法: term = factor | term `*` factor | term `/` factor
    private fun parseTerm(): Double {
        var x = parseFactor()
        while (true) {
            when {
                eat('*'.toInt()) -> x *= parseFactor()
                eat('/'.toInt()) -> x /= parseFactor()
                else -> return x
            }
        }
    }

    // 语法: factor = `+` factor | `-` factor | `(` expression `)` | number
    private fun parseFactor(): Double {
        if (eat('+'.toInt())) return parseFactor() // +n
        if (eat('-'.toInt())) return -parseFactor() // -n

        var x: Double
        val startPos = pos
        if (eat('('.toInt())) {
            x = parseExpression()
            eat(')'.toInt())
        } else if (ch in '0'.toInt()..'9'.toInt() || ch == '.'.toInt()) {
            while (ch in '0'.toInt()..'9'.toInt() || ch == '.'.toInt()) nextChar()
            x = str.substring(startPos, pos).toDoubleOrNull() ?: throw RuntimeException("Invalid number")
        } else {
            throw RuntimeException("Unexpected: " + ch.toChar())
        }

        return x
    }
}
