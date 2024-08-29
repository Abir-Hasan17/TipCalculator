package com.learning.tipcalculator

import android.annotation.SuppressLint
import androidx.compose.ui.graphics.Color
import com.learning.tipcalculator.ui.theme.remarkBad
import com.learning.tipcalculator.ui.theme.remarkExcellent
import com.learning.tipcalculator.ui.theme.remarkGood
import com.learning.tipcalculator.ui.theme.remarkGreat
import com.learning.tipcalculator.ui.theme.remarkOk
import java.text.DecimalFormatSymbols
import java.util.Locale

object Utils {
    fun getColorByRemark(remark: String): Color{
        return when(remark){
            "Bad" -> remarkBad
            "Ok" -> remarkOk
            "Great" -> remarkGreat
            "Good" -> remarkGood
            "Excellent" -> remarkExcellent
            else -> Color.White
        }
    }

    fun getTipRemark(p: Int): String{
        return when (p) {
            in 6..10 -> "Ok"
            in 11..15 -> "Good"
            in 16..25 -> "Great"
            in 26..35 -> "Excellent"
            else -> "Bad"
        }

    }

    @SuppressLint("DefaultLocale")
    fun getTipAmount(baseAmount: String, tipPercent: Int): String {
        val ans = ((baseAmount.toFloatOrNull() ?: 0f) * (tipPercent.toFloat() / 100))
        return String.format("%.2f", ans)
    }

    @SuppressLint("DefaultLocale")
    fun getAmountToPay(tipAmount: String, baseAmount: String): String {
        val ans = (tipAmount.toFloatOrNull() ?: 0f) + (baseAmount.toFloatOrNull() ?: 0f)
        return String.format("%.2f", ans)
    }

    fun decimalCleanUp(input: String): String {

        val symbols = DecimalFormatSymbols(Locale.US)
        val decimalSeparator = symbols.decimalSeparator
        if (input.matches("\\D".toRegex())) return ""
        if (input.matches("0+".toRegex())) return "0"

        val sb = StringBuilder()

        var hasDecimalSep = false

        for (char in input) {
            if (char.isDigit()) {
                sb.append(char)
                continue
            }
            if (char == decimalSeparator && !hasDecimalSep && sb.isNotEmpty()) {
                sb.append(char)
                hasDecimalSep = true
            }
        }

        return sb.toString()
    }
}