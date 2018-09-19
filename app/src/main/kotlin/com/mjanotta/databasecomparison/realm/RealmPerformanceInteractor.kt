package com.mjanotta.databasecomparison.realm

import com.mjanotta.databasecomparison.performance.PerformanceInteractor
import com.mjanotta.databasecomparison.performance.PerformanceQuery
import com.mjanotta.databasecomparison.realm.entity.RealmPerformanceDataInner
import com.mjanotta.databasecomparison.realm.entity.RealmPerformanceDataOuter
import com.mjanotta.databasecomparison.realm.model.RealmPerformanceDataOuterRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm

class RealmPerformanceInteractor(
        private val repository: RealmPerformanceDataOuterRepository,
        private val realm: Realm,
        private val items: Long
) : PerformanceInteractor<RealmPerformanceDataOuter> {

    private val data: List<RealmPerformanceDataOuter> = createData()

    override fun saveData(): Completable {
        return repository.save(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun readData(): Single<List<RealmPerformanceDataOuter>> {
        return repository.findAll(realm)
                .firstOrError()
                .map { it as List<RealmPerformanceDataOuter> }
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteData(): Completable {
        return repository.deleteAll(realm).observeOn(AndroidSchedulers.mainThread())
    }

    override fun queryData(value: String): Single<List<RealmPerformanceDataOuter>> {
        return repository.findOuterDataByInnerDataQueryParam(realm, value)
                .firstOrError()
                .map { it as List<RealmPerformanceDataOuter> }
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun reset(): Completable = Completable.complete()

    private fun createData(): List<RealmPerformanceDataOuter> {
        var currentItem = 0L
        return Single.fromCallable {
            val dataOuter = RealmPerformanceDataOuter()
            val dataInner = RealmPerformanceDataInner()

            dataInner.data5 = "5"
            dataInner.data6 = "6"
            dataInner.data7 = "7"
            dataInner.data8 = "8"
            dataInner.data9 = "9"
            dataInner.data10 = "10"
            dataInner.queryParam = when (++currentItem) {
                1L ->  PerformanceQuery.NEAR
                (items - 1) ->  PerformanceQuery.FAR
                else ->  "random"
            }

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