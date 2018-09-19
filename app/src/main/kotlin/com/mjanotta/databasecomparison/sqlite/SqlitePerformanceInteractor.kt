package com.mjanotta.databasecomparison.sqlite

import com.mjanotta.databasecomparison.performance.PerformanceInteractor
import com.mjanotta.databasecomparison.performance.PerformanceQuery
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
        return dao.findAll()
                .firstOrError()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteData(): Completable = Completable.fromAction { dao.deleteAll() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun queryData(value: String): Single<List<SqlitePerformanceDataOuter>> {
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

    private fun createData(): List<SqlitePerformanceDataOuter> {
        var currentItem = 0L
        return Single.fromCallable {

            val queryParam = when (++currentItem) {
                1L ->  PerformanceQuery.NEAR
                (items - 1) ->  PerformanceQuery.FAR
                else ->  "random"
            }

            val dataInner = SqlitePerformanceDataInner("5", "6", "7", "8", "9", "10", queryParam)
            val dataOuter = SqlitePerformanceDataOuter(0, "1", "2", "3", "4", dataInner)

            return@fromCallable dataOuter
        }
                .repeat(items)
                .toList()
                .blockingGet()
    }
}