package com.nico.weatherapp.common.utils

import android.content.res.Resources
import android.util.DisplayMetrics
import kotlin.math.roundToInt


fun convertPixelsToDp(px: Int): Int {
    val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
    val dp = px / (metrics.densityDpi / 160f)
    return dp.roundToInt()
}

fun convertDpToPixels(dp: Int): Int {
    val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
    val px = dp * (metrics.densityDpi / 160f)
    return px.roundToInt()
}

val Int.dp: Int
    get() = convertDpToPixels(this)

val Int.px: Int
    get() = convertPixelsToDp(this)

