package com.mjanotta.databasecomparison.sqlite

import android.app.Fragment
import android.content.Context
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.androidFragmentScope
import com.mjanotta.databasecomparison.performance.PerformanceInteractor
import com.mjanotta.databasecomparison.performance.PerformancePresenter
import com.mjanotta.databasecomparison.performance.PerformancePresenterImpl
import com.mjanotta.databasecomparison.performance.PerformanceView
import com.mjanotta.databasecomparison.sqlite.entity.SqlitePerformanceDataOuter
import com.mjanotta.databasecomparison.sqlite.model.SqlitePerformanceDao
import com.mjanotta.databasecomparison.sqlite.model.SqlitePerformanceDaoImpl

fun sqlitePerformanceModule(context: Context) = Kodein.Module {

    bind<SqlitePerformanceDao>() with singleton {
        SqlitePerformanceDaoImpl(context)
    }

    bind<PerformanceInteractor<SqlitePerformanceDataOuter>>("SqlitePerformance") with provider { SqlitePerformanceInteractor(instance(), instance("items")) }
    bind<PerformancePresenter<*, SqlitePerformanceDataOuter>>("SqlitePerformance") with scopedSingleton(androidFragmentScope) {
        PerformancePresenterImpl(
                it as PerformanceView<SqlitePerformanceDataOuter>,
                instance("SqlitePerformance"),
                with(it as Fragment).instance(),
                instance("repeat")
        )
    }


}