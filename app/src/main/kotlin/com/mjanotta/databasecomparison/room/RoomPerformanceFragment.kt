package com.mjanotta.databasecomparison.room

import com.mjanotta.databasecomparison.performance.PerformanceFragment
import com.mjanotta.databasecomparison.performance.PerformanceItem
import com.mjanotta.databasecomparison.performance.PerformancePresenter
import com.mjanotta.databasecomparison.room.entity.RoomPerformanceDataOuter
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with

class RoomPerformanceFragment : PerformanceFragment<RoomPerformanceDataOuter>() {

    override val presenter: PerformancePresenter<*, RoomPerformanceDataOuter> by kodein.with(this).instance("RoomPerformance")

    override fun mapItem(item: RoomPerformanceDataOuter): PerformanceItem<RoomPerformanceDataOuter> = RoomPerformanceItem(item)


}