package com.mjanotta.databasecomparison.objectbox

import android.app.Fragment
import com.mjanotta.databasecomparison.objectbox.entity.ObjectBoxPerformanceDataInner
import com.mjanotta.databasecomparison.objectbox.entity.ObjectBoxPerformanceDataOuter
import com.mjanotta.databasecomparison.objectbox.model.ObjectBoxPerformanceDataOuterRepository
import com.mjanotta.databasecomparison.objectbox.model.ObjectBoxPerformanceDataOuterRepositoryImpl
import com.mjanotta.databasecomparison.performance.PerformanceInteractor
import com.mjanotta.databasecomparison.performance.PerformancePresenter
import com.mjanotta.databasecomparison.performance.PerformancePresenterImpl
import com.mjanotta.databasecomparison.performance.PerformanceView
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.androidFragmentScope
import io.objectbox.BoxStore

val objectBoxPerformanceModule = Kodein.Module {
    bind<ObjectBoxPerformanceDataOuterRepository>() with singleton {
        ObjectBoxPerformanceDataOuterRepositoryImpl(
                instance<BoxStore>().boxFor(ObjectBoxPerformanceDataOuter::class.java),
                instance<BoxStore>().boxFor(ObjectBoxPerformanceDataInner::class.java))
    }
    bind<PerformanceInteractor<ObjectBoxPerformanceDataOuter>>("ObjectBoxPerformance") with provider { ObjectBoxPerformanceInteractor(instance(), instance("items")) }
    bind<PerformancePresenter<*, ObjectBoxPerformanceDataOuter>>("ObjectBoxPerformance") with scopedSingleton(androidFragmentScope) {
        PerformancePresenterImpl(
                it as PerformanceView<ObjectBoxPerformanceDataOuter>,
                instance("ObjectBoxPerformance"),
                with(it as Fragment).instance(),
                instance("repeat")
        )
    }
}