package com.mjanotta.databasecomparison.realm

import com.mjanotta.databasecomparison.performance.PerformanceFragment
import com.mjanotta.databasecomparison.performance.PerformanceItem
import com.mjanotta.databasecomparison.performance.PerformancePresenter
import com.mjanotta.databasecomparison.realm.entity.RealmPerformanceDataOuter
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with

class RealmPerformanceFragment : PerformanceFragment<RealmPerformanceDataOuter>() {

    override val presenter: PerformancePresenter<*, RealmPerformanceDataOuter> by kodein.with(this).instance("RealmPerformance")

    override fun mapItem(item: RealmPerformanceDataOuter): PerformanceItem<RealmPerformanceDataOuter> = RealmPerformanceItem(item)
}