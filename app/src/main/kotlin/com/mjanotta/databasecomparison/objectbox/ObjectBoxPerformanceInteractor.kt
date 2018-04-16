package com.mjanotta.databasecomparison.objectbox

import com.mjanotta.databasecomparison.objectbox.entity.ObjectBoxPerformanceDataInner
import com.mjanotta.databasecomparison.objectbox.entity.ObjectBoxPerformanceDataOuter
import com.mjanotta.databasecomparison.objectbox.model.ObjectBoxPerformanceDataOuterRepository
import com.mjanotta.databasecomparison.performance.PerformanceInteractor
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ObjectBoxPerformanceInteractor(
        private val repository: ObjectBoxPerformanceDataOuterRepository,
        private val items: Long
) : PerformanceInteractor<ObjectBoxPerformanceDataOuter> {

    private var data: List<ObjectBoxPerformanceDataOuter> = createData({ ObjectBoxPerformanceDataOuter() }, { ObjectBoxPerformanceDataInner() })

    override fun saveData(): Completable {
        return repository.save(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun readData(): Single<List<ObjectBoxPerformanceDataOuter>> {
        return repository.findAll()
                .firstOrError()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteData(): Completable = repository.deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun reset(): Completable {
        return Completable.fromAction { data = createData({ ObjectBoxPerformanceDataOuter() }, { ObjectBoxPerformanceDataInner() }) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun createData(outerData: () -> ObjectBoxPerformanceDataOuter, innerData: () -> ObjectBoxPerformanceDataInner): List<ObjectBoxPerformanceDataOuter> {
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
            dataOuter.data5.target = dataInner

            return@fromCallable dataOuter
        }
                .repeat(items)
                .toList()
                .blockingGet()
    }
}