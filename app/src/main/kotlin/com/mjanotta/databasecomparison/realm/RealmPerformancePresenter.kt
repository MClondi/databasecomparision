package com.mjanotta.databasecomparison.realm

import com.mjanotta.databasecomparison.performance.PerformanceInteractor
import com.mjanotta.databasecomparison.performance.PerformancePresenterImpl
import com.mjanotta.databasecomparison.performance.PerformanceRouter
import com.mjanotta.databasecomparison.performance.PerformanceView
import com.mjanotta.databasecomparison.realm.entity.RealmPerformanceDataOuter
import io.realm.Realm

class RealmPerformancePresenter(
        view: PerformanceView<RealmPerformanceDataOuter>,
        interactor: PerformanceInteractor<RealmPerformanceDataOuter>,
        router: PerformanceRouter,
        repeat: Long,
        val realm: Realm
) : PerformancePresenterImpl<RealmPerformanceDataOuter>(view, interactor, router, repeat) {

    override fun destroy() {
        super.destroy()
        realm.close()
    }
}