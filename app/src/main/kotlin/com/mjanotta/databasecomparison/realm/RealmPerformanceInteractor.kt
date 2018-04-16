package com.mjanotta.databasecomparison.realm

import com.mjanotta.databasecomparison.performance.PerformanceInteractor
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

    private val data: List<RealmPerformanceDataOuter> = createData({ RealmPerformanceDataOuter() }, { RealmPerformanceDataInner() })

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

    override fun reset(): Completable = Completable.complete()

    private fun createData(outerData: () -> RealmPerformanceDataOuter, innerData: () -> RealmPerformanceDataInner): List<RealmPerformanceDataOuter> {
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