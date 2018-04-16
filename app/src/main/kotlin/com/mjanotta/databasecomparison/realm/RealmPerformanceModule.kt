package com.mjanotta.databasecomparison.realm

import android.app.Fragment
import com.mjanotta.databasecomparison.performance.PerformanceInteractor
import com.mjanotta.databasecomparison.performance.PerformancePresenter
import com.mjanotta.databasecomparison.performance.PerformanceView
import com.mjanotta.databasecomparison.realm.entity.RealmPerformanceDataOuter
import com.mjanotta.databasecomparison.realm.model.RealmPerformanceDataOuterRepository
import com.mjanotta.databasecomparison.realm.model.RealmPerformanceDataOuterRepositoryImpl
import com.mjanotta.databasecomparison.realm.model.RxFactory
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.androidFragmentScope

val realmPerformanceModule = Kodein.Module {
    bind<RxFactory>() with singleton { RxFactory() }
    bind<RealmPerformanceDataOuterRepository>() with singleton { RealmPerformanceDataOuterRepositoryImpl(instance()) }
    bind<PerformanceInteractor<RealmPerformanceDataOuter>>("RealmPerformance") with provider { RealmPerformanceInteractor(instance(), instance(), instance("items")) }
    bind<PerformancePresenter<*, RealmPerformanceDataOuter>>("RealmPerformance") with scopedSingleton(androidFragmentScope) {
        RealmPerformancePresenter(
                it as PerformanceView<RealmPerformanceDataOuter>,
                instance("RealmPerformance"),
                with(it as Fragment).instance(),
                instance("repeat"),
                instance()
        )
    }
}