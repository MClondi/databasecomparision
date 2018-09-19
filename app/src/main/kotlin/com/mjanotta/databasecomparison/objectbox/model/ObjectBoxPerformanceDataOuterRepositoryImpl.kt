package com.mjanotta.databasecomparison.objectbox.model

import com.mjanotta.databasecomparison.objectbox.entity.ObjectBoxPerformanceDataInner
import com.mjanotta.databasecomparison.objectbox.entity.ObjectBoxPerformanceDataOuter
import io.objectbox.Box
import io.objectbox.rx.RxQuery
import io.reactivex.Completable
import io.reactivex.Observable

class ObjectBoxPerformanceDataOuterRepositoryImpl(
        private val performanceBox: Box<ObjectBoxPerformanceDataOuter>,
        private val innerPerformanceBox: Box<ObjectBoxPerformanceDataInner>
) : ObjectBoxPerformanceDataOuterRepository {

    override fun findAll(): Observable<List<ObjectBoxPerformanceDataOuter>> = RxQuery.observable(performanceBox.query().build())

    override fun save(entities: List<ObjectBoxPerformanceDataOuter>): Completable = Completable.fromAction {
        innerPerformanceBox.put(entities.map { it.data5.target })
        performanceBox.put(entities)
    }

    override fun findOuterDataByInnerDataQueryParam(value: String): Observable<List<ObjectBoxPerformanceDataOuter>> {
        return RxQuery.observable(performanceBox.query().filter { outer ->
            outer.data5.target.queryParam == value
        }.build())
    }

    override fun deleteAll(): Completable = Completable.fromAction {
        performanceBox.removeAll()
        innerPerformanceBox.removeAll()
    }
}
