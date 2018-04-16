package com.mjanotta.databasecomparison.room

import android.app.Fragment
import com.mjanotta.databasecomparison.AppDatabase
import com.mjanotta.databasecomparison.performance.PerformanceInteractor
import com.mjanotta.databasecomparison.performance.PerformancePresenter
import com.mjanotta.databasecomparison.performance.PerformancePresenterImpl
import com.mjanotta.databasecomparison.performance.PerformanceView
import com.mjanotta.databasecomparison.room.entity.RoomPerformanceDataOuter
import com.mjanotta.databasecomparison.room.model.RoomPerformanceDao
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.androidFragmentScope

val roomPerformanceModule = Kodein.Module {
    bind<RoomPerformanceDao>() with provider { instance<AppDatabase>().performanceDao() }
    bind<PerformanceInteractor<RoomPerformanceDataOuter>>("RoomPerformance") with provider { RoomPerformanceInteractor(instance(), instance("items")) }
    bind<PerformancePresenter<*, RoomPerformanceDataOuter>>("RoomPerformance") with scopedSingleton(androidFragmentScope) {
        PerformancePresenterImpl(
                it as PerformanceView<RoomPerformanceDataOuter>,
                instance("RoomPerformance"),
                with(it as Fragment).instance(),
                instance("repeat")
        )
    }
}