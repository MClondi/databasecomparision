package com.mjanotta.databasecomparison.room

import com.mjanotta.databasecomparison.performance.PerformanceInteractor
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

    private var data: List<RoomPerformanceDataOuter> = createData({ RoomPerformanceDataOuter(0, "", "", "", "", RoomPerformanceDataInner("", "", "", "", "", "")) }, { RoomPerformanceDataInner("", "", "", "", "", "") })


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

    override fun reset(): Completable {
        return Completable.fromAction {
            data = createData({ RoomPerformanceDataOuter(0, "", "", "", "", RoomPerformanceDataInner("", "", "", "", "", "")) }, { RoomPerformanceDataInner("", "", "", "", "", "") })
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun createData(outerData: () -> RoomPerformanceDataOuter, innerData: () -> RoomPerformanceDataInner): List<RoomPerformanceDataOuter> {
        return Single.fromCallable {
            val dataOuter = outerData()
            val dataInner = innerData()

            dataInner.data5 = "5"
            dataInner.data6 = "6"
            dataInner.data7 = "7"
            dataInner.data8 = "8"
            dataInner.data9 = "9"
            dataInner.data10 = "10"

            dataOuter.data1 = "1"
            dataOuter.data2 = "2"
            dataOuter.data3 = "3"
            dataOuter.data4 = "4"
            dataOuter.data5 = dataInner

            return@fromCallable dataOuter
        }
                .repeat(items)
                .toList()
                .blockingGet()
    }
}