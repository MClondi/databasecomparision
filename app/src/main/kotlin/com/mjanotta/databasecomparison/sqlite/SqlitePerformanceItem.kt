package com.mjanotta.databasecomparison.sqlite

import android.annotation.SuppressLint
import com.mjanotta.databasecomparison.performance.PerformanceItem
import com.mjanotta.databasecomparison.sqlite.entity.SqlitePerformanceDataOuter
import kotlinx.android.synthetic.main.item_performance.view.*

class SqlitePerformanceItem(model: SqlitePerformanceDataOuter) : PerformanceItem<SqlitePerformanceDataOuter>(model) {

    @SuppressLint("MissingSuperCall")
    override fun bindView(holder: PerformanceViewHolder, payloads: MutableList<Any>?) {
        super.bindView(holder, payloads)
        holder.itemView.data1.text = model.data1
        holder.itemView.data2.text = model.data2
        holder.itemView.data3.text = model.data3
        holder.itemView.data4.text = model.data4
        holder.itemView.data5.text = model.data5.data5
        holder.itemView.data5.text = model.data5.data6
        holder.itemView.data7.text = model.data5.data7
        holder.itemView.data8.text = model.data5.data8
        holder.itemView.data9.text = model.data5.data9
        holder.itemView.data10.text = model.data5.data10
    }
}