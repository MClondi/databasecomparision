package com.mjanotta.databasecomparison.performance

import android.support.v7.widget.RecyclerView
import android.view.View
import com.mjanotta.databasecomparison.R
import com.mikepenz.fastadapter.items.GenericAbstractItem

abstract class PerformanceItem<T>(model: T) : GenericAbstractItem<T, PerformanceItem<T>, PerformanceItem.PerformanceViewHolder>(model) {

    override fun getViewHolder(view: View): PerformanceViewHolder {
        return PerformanceViewHolder(view)
    }

    override fun getLayoutRes(): Int {
        return R.layout.item_performance
    }

    override fun getType(): Int {
        return layoutRes
    }

    class PerformanceViewHolder(view: View) : RecyclerView.ViewHolder(view)
}