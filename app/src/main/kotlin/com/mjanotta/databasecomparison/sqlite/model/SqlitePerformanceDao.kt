package com.mjanotta.databasecomparison.sqlite.model

import com.mjanotta.databasecomparison.sqlite.entity.SqlitePerformanceDataOuter
import io.reactivex.Flowable

interface SqlitePerformanceDao {

    fun findAll(): Flowable<List<SqlitePerformanceDataOuter>>

    fun save(entities: List<SqlitePerformanceDataOuter>)

    fun deleteAll()

    fun findOuterDataByInnerDataQueryParam(value: String): Flowable<List<SqlitePerformanceDataOuter>>
}