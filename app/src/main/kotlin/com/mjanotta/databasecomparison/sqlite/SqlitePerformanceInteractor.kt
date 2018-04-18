package com.mjanotta.databasecomparison.sqlite

import com.mjanotta.databasecomparison.performance.PerformanceInteractor
import com.mjanotta.databasecomparison.sqlite.entity.SqlitePerformanceDataInner
import com.mjanotta.databasecomparison.sqlite.entity.SqlitePerformanceDataOuter
import com.mjanotta.databasecomparison.sqlite.model.SqlitePerformanceDao
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SqlitePerformanceInteractor(
        private val dao: SqlitePerformanceDao,
        private val items: Long
): PerformanceInteractor<SqlitePerformanceDataOuter> {


    private var data: List<SqlitePerformanceDataOuter> = createData()

    override fun saveData(): Completable {

        return Completable.fromAction { dao.save(data) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) }

    override fun readData(): Single<List<SqlitePerformanceDataOuter>> {
        return dao.findAll().firstOrError()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteData(): Completable = Completable.fromAction { dao.deleteAll() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun reset(): Completable {
        return Completable.fromAction {
            data = createData()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun createData(): List<SqlitePerformanceDataOuter> {
        return Single.fromCallable {
            val dataOuter = SqlitePerformanceDataOuter(0, "", "", "", "", SqlitePerformanceDataInner("", "", "", "", "", ""))
            val dataInner = SqlitePerformanceDataInner("", "", "", "", "", "")

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