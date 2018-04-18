package com.mjanotta.databasecomparison.sqlite

import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with
import com.mjanotta.databasecomparison.performance.PerformanceFragment
import com.mjanotta.databasecomparison.performance.PerformanceItem
import com.mjanotta.databasecomparison.performance.PerformancePresenter
import com.mjanotta.databasecomparison.sqlite.entity.SqlitePerformanceDataOuter

class SqlitePerformanceFragment : PerformanceFragment<SqlitePerformanceDataOuter>() {

    override val presenter: PerformancePresenter<*, SqlitePerformanceDataOuter> by kodein.with(this).instance("SqlitePerformance")

    override fun mapItem(item: SqlitePerformanceDataOuter): PerformanceItem<SqlitePerformanceDataOuter> = SqlitePerformanceItem(item)


}