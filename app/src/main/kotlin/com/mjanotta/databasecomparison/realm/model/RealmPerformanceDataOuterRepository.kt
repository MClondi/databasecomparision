package com.mjanotta.databasecomparison.realm.model

import com.mjanotta.databasecomparison.realm.entity.RealmPerformanceDataOuter
import io.reactivex.Completable
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmResults


interface RealmPerformanceDataOuterRepository {

    fun findAll(realm: Realm): Observable<RealmResults<RealmPerformanceDataOuter>>

    fun save(entities: Iterable<RealmPerformanceDataOuter>): Completable

    fun deleteAll(realm: Realm): Completable

    fun findOuterDataByInnerDataQueryParam(realm: Realm, value: String): Observable<RealmResults<RealmPerformanceDataOuter>>
}