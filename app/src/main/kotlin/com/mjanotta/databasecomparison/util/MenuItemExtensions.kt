package com.mjanotta.databasecomparison.util

import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.MenuItem

fun MenuItem.tint(@ColorRes colorRes: Int, context: Context) {
    val wrapDrawable = DrawableCompat.wrap(icon)
    val color = ContextCompat.getColor(context, colorRes)
    DrawableCompat.setTint(wrapDrawable, color)
    icon = wrapDrawable
}
