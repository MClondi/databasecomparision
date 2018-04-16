package com.mjanotta.databasecomparison.util

import android.content.Context
import android.support.annotation.AttrRes
import android.support.annotation.ColorRes
import android.support.v4.text.TextUtilsCompat
import com.mjanotta.databasecomparison.R
import java.util.*

@ColorRes fun Context.colorResFromAttr(@AttrRes attr: Int): Int {
    val ta = obtainStyledAttributes(intArrayOf(attr))
    val res = ta.getResourceId(0, R.color.colorAccent)
    ta.recycle()
    return res
}

fun Context.isRtl(): Boolean {
    val textDirection = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault())
    return textDirection == Character.DIRECTIONALITY_RIGHT_TO_LEFT.toInt() || textDirection == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC.toInt()
}