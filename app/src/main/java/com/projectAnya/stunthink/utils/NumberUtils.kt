package com.projectAnya.stunthink.utils

import java.math.RoundingMode
import java.text.DecimalFormat

object NumberUtils {
    fun roundOffDecimal(number: Float): Float {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toFloat()
    }
}