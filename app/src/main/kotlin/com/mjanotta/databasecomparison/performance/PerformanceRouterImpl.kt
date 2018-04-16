package com.mjanotta.databasecomparison.performance

import android.app.Fragment
import com.mjanotta.databasecomparison.performance.entity.PerformanceResult

class PerformanceRouterImpl(val fragment: Fragment) : PerformanceRouter {

    override fun showPerformanceResult(performanceResult: PerformanceResult) {
        PerformanceResultDialog.create(performanceResult).show(fragment.activity.fragmentManager, PerformanceResultDialog.TAG)
    }
}