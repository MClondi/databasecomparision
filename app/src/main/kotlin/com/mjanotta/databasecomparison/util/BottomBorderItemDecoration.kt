package com.mjanotta.databasecomparison.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView

class BottomBorderItemDecoration(context: Context, @DimenRes borderWidthRes: Int, @DimenRes borderMarginStartRes: Int, @ColorRes borderColorRes: Int) : RecyclerView.ItemDecoration() {

    private val borderWidth: Int = context.resources.getDimensionPixelSize(borderWidthRes)
    private val borderMarginStart: Int = context.resources.getDimensionPixelSize(borderMarginStartRes)
    private val borderColorPaint: Paint
    private val rtl: Boolean

    init {
        val borderColor = ContextCompat.getColor(context, borderColorRes)
        borderColorPaint = Paint()
        borderColorPaint.color = borderColor
        rtl = context.isRtl()
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val childCountInRecycler = parent.childCount
        val childCountInAdapter = parent.adapter.itemCount
        for (positionInRecycler in 0..childCountInRecycler - 1) {
            val view = parent.getChildAt(positionInRecycler)
            val positionInAdapter = parent.getChildAdapterPosition(view)

            if (childCountInAdapter - positionInAdapter == 1) {
                continue
            }

            var left = view.left
            var right = view.right

            if (rtl) {
                right -= borderMarginStart
            } else {
                left += borderMarginStart
            }

            val tY = view.translationY.toInt()
            val bottom = view.bottom + tY
            val top = bottom - borderWidth

            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), borderColorPaint)
        }
    }
}