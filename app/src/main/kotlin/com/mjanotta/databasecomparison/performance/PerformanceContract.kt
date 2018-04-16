package com.mjanotta.databasecomparison.performance

import com.mjanotta.databasecomparison.core.BasePresenter
import com.mjanotta.databasecomparison.core.BaseView
import com.mjanotta.databasecomparison.performance.entity.PerformanceResult
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface PerformanceView<in T> : BaseView {

    var startTestClicks: Observable<Any>

    fun showData(data: List<T>)

    fun showProgress()

    fun hideProgress()
}

interface PerformanceInteractor<T> {

    fun saveData(): Completable

    fun readData(): Single<List<T>>

    fun deleteData(): Completable

    fun reset(): Completable
}

abstract class PerformancePresenter<S, in T>(view: PerformanceView<T>) : BasePresenter<PerformanceView<T>, S>(view) {

    abstract fun bindMenu()
}

interface PerformanceRouter {

    fun showPerformanceResult(performanceResult: PerformanceResult)
}