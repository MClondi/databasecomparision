package com.mjanotta.databasecomparison.objectbox.model

import com.mjanotta.databasecomparison.objectbox.entity.ObjectBoxPerformanceDataOuter
import io.reactivex.Completable
import io.reactivex.Observable

interface ObjectBoxPerformanceDataOuterRepository {

    fun findAll(): Observable<List<ObjectBoxPerformanceDataOuter>>

    fun save(entities: List<ObjectBoxPerformanceDataOuter>): Completable

    fun deleteAll(): Completable

    fun findOuterDataByInnerDataQueryParam(value: String): Observable<List<ObjectBoxPerformanceDataOuter>>
}