package com.mjanotta.databasecomparison.performance

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidFragmentScope
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.scopedSingleton
import com.github.salomonbrys.kodein.with

val performanceModule = Kodein.Module {
    constant("items") with 5L
    constant("repeat") with 10L

    bind<PerformanceRouter>() with scopedSingleton(androidFragmentScope) { PerformanceRouterImpl(it) }
}