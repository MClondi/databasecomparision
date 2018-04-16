package com.mjanotta.databasecomparison.objectbox

import android.annotation.SuppressLint
import com.mjanotta.databasecomparison.objectbox.entity.ObjectBoxPerformanceDataOuter
import com.mjanotta.databasecomparison.performance.PerformanceItem
import kotlinx.android.synthetic.main.item_performance.view.*

class ObjectBoxPerformanceItem(model: ObjectBoxPerformanceDataOuter) : PerformanceItem<ObjectBoxPerformanceDataOuter>(model) {

    @SuppressLint("MissingSuperCall")
    override fun bindView(holder: PerformanceViewHolder, payloads: MutableList<Any>?) {
        super.bindView(holder, payloads)
        holder.itemView.data1.text = model.data1
        holder.itemView.data2.text = model.data2
        holder.itemView.data3.text = model.data3
        holder.itemView.data4.text = model.data4
        holder.itemView.data5.text = model.data5.target.data5
        holder.itemView.data5.text = model.data5.target.data6
        holder.itemView.data7.text = model.data5.target.data7
        holder.itemView.data8.text = model.data5.target.data8
        holder.itemView.data9.text = model.data5.target.data9
        holder.itemView.data10.text = model.data5.target.data10
    }
}