package com.mjanotta.databasecomparison.performance

import com.mjanotta.databasecomparison.performance.entity.PerformanceResult
import com.mjanotta.databasecomparison.util.Stopwatch
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import org.parceler.Parcel
import org.parceler.ParcelConstructor
import java.util.concurrent.TimeUnit

open class PerformancePresenterImpl<in T>(
        view: PerformanceView<T>,
        private val interactor: PerformanceInteractor<T>,
        private val router: PerformanceRouter,
        private val repeat: Long
) : PerformancePresenter<PerformancePresenterImpl.State, T>(view) {

    override fun bindMenu() {
        view.startTestClicks.subscribe { startTest() }.addTo(disposables)
    }

    private fun startTest() {
        val performanceResult = PerformanceResult(0, 0, 0, 0, 0)
        val stopwatch = Stopwatch()
        view.showProgress()
        savePerformance(stopwatch, performanceResult)
                .andThen(readPerformance(stopwatch, performanceResult))
                .andThen(queryNearPerformance(stopwatch, performanceResult))
                .andThen(queryFarPerformance(stopwatch, performanceResult))
                .andThen(interactor.deleteData())
                .andThen(deletePerformance(stopwatch, performanceResult))
                .andThen(interactor.reset())
                .andThen(interactor.saveData())
                .andThen(interactor.reset())
                .andThen(interactor.readData())
                .doOnSuccess { view.showData(it) }
                .subscribeBy {
                    view.hideProgress()
                    router.showPerformanceResult(performanceResult)
                }
                .addTo(disposables)
    }

    private fun savePerformance(stopwatch: Stopwatch, performanceResult: PerformanceResult): Completable {
        return interactor.deleteData()
                .andThen(interactor.reset())
                .doOnComplete { stopwatch.start() }
                .andThen(interactor.saveData())
                .andThen(Single.fromCallable { stopwatch.stop(TimeUnit.MILLISECONDS) })
                .repeat(repeat)
                .toList()
                .map { it.average() }
                .doOnSuccess { performanceResult.saveTime = it.toLong() }
                .toCompletable()
    }

    private fun readPerformance(stopwatch: Stopwatch, performanceResult: PerformanceResult): Completable {
        return Completable.fromAction { stopwatch.start() }
                .andThen(interactor.readData())
                .map { stopwatch.stop(TimeUnit.MILLISECONDS) }
                .repeat(repeat)
                .toList()
                .map { it.average() }
                .doOnSuccess { performanceResult.readTime = it.toLong() }
                .toCompletable()
    }

    private fun queryNearPerformance(stopwatch: Stopwatch, performanceResult: PerformanceResult): Completable {
        return Completable.fromAction { stopwatch.start() }
                .andThen(interactor.queryData(PerformanceQuery.NEAR))
                .map { stopwatch.stop(TimeUnit.MILLISECONDS) }
                .repeat(repeat)
                .toList()
                .map { it.average() }
                .doOnSuccess { performanceResult.queryNearTime = it.toLong() }
                .toCompletable()
    }

    private fun queryFarPerformance(stopwatch: Stopwatch, performanceResult: PerformanceResult): Completable {
        return Completable.fromAction { stopwatch.start() }
                .andThen(interactor.queryData(PerformanceQuery.FAR))
                .map { stopwatch.stop(TimeUnit.MILLISECONDS) }
                .repeat(repeat)
                .toList()
                .map { it.average() }
                .doOnSuccess { performanceResult.queryFarTime = it.toLong() }
                .toCompletable()
    }

    private fun deletePerformance(stopwatch: Stopwatch, performanceResult: PerformanceResult): Completable {
        return interactor.reset()
                .andThen(interactor.saveData())
                .doOnComplete { stopwatch.start() }
                .andThen(interactor.deleteData())
                .andThen(Single.fromCallable { stopwatch.stop(TimeUnit.MILLISECONDS) })
                .repeat(repeat)
                .toList()
                .map { it.average() }
                .doOnSuccess { performanceResult.deleteTime = it.toLong() }
                .toCompletable()
    }

    override fun saveState(): State? {
        return State()
    }

    @Parcel(Parcel.Serialization.BEAN)
    class State @ParcelConstructor constructor()
}