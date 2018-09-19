package com.mjanotta.databasecomparison.room

import com.mjanotta.databasecomparison.performance.PerformanceInteractor
import com.mjanotta.databasecomparison.performance.PerformanceQuery
import com.mjanotta.databasecomparison.room.entity.RoomPerformanceDataInner
import com.mjanotta.databasecomparison.room.entity.RoomPerformanceDataOuter
import com.mjanotta.databasecomparison.room.model.RoomPerformanceDao
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RoomPerformanceInteractor(
        private val dao: RoomPerformanceDao,
        private val items: Long
) : PerformanceInteractor<RoomPerformanceDataOuter> {

    private var data: List<RoomPerformanceDataOuter> = createData()


    override fun saveData(): Completable {
        return Completable.fromAction { dao.save(data) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun readData(): Single<List<RoomPerformanceDataOuter>> {
        return dao.findAll().firstOrError()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteData(): Completable = Completable.fromAction { dao.deleteAll() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun queryData(value: String): Single<List<RoomPerformanceDataOuter>> {
        return dao.findOuterDataByInnerDataQueryParam(value)
                .firstOrError()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun reset(): Completable {
        return Completable.fromAction {
            data = createData()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun createData(): List<RoomPerformanceDataOuter> {
        var currentItem = 0L
        return Single.fromCallable {

            val queryParam = when (++currentItem) {
                1L ->  PerformanceQuery.NEAR
                (items - 1) ->  PerformanceQuery.FAR
                else ->  "random"
            }

            val dataInner = RoomPerformanceDataInner("5", "6", "7", "8", "9", "10", queryParam)
            val dataOuter = RoomPerformanceDataOuter(0, "1", "2", "3", "4", dataInner)

            return@fromCallable dataOuter
        }
                .repeat(items)
                .toList()
                .blockingGet()
    }
}