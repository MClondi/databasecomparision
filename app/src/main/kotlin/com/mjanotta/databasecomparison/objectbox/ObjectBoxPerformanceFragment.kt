package com.mjanotta.databasecomparison.objectbox

import com.mjanotta.databasecomparison.objectbox.entity.ObjectBoxPerformanceDataOuter
import com.mjanotta.databasecomparison.performance.PerformanceFragment
import com.mjanotta.databasecomparison.performance.PerformanceItem
import com.mjanotta.databasecomparison.performance.PerformancePresenter
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with

class ObjectBoxPerformanceFragment : PerformanceFragment<ObjectBoxPerformanceDataOuter>() {

    override val presenter: PerformancePresenter<*, ObjectBoxPerformanceDataOuter> by kodein.with(this).instance("ObjectBoxPerformance")

    override fun mapItem(item: ObjectBoxPerformanceDataOuter): PerformanceItem<ObjectBoxPerformanceDataOuter> = ObjectBoxPerformanceItem(item)
}