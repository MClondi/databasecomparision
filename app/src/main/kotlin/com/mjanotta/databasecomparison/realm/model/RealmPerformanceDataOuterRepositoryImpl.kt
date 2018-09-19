package com.mjanotta.databasecomparison.realm.model

import com.mjanotta.databasecomparison.realm.entity.RealmPerformanceDataInner
import com.mjanotta.databasecomparison.realm.entity.RealmPerformanceDataOuter
import io.reactivex.Completable
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmResults

class RealmPerformanceDataOuterRepositoryImpl(val rxFactory: RxFactory) : RealmPerformanceDataOuterRepository {

    override fun findAll(realm: Realm): Observable<RealmResults<RealmPerformanceDataOuter>> {
        return rxFactory.from(realm, realm.where(RealmPerformanceDataOuter::class.java).findAllAsync())
                .filter { it.isLoaded }
    }

    override fun save(entities: Iterable<RealmPerformanceDataOuter>): Completable {
        return Completable.fromAction {
            Realm.getDefaultInstance().let {
                it.executeTransaction { realm -> realm.copyToRealm(entities) }
            }
        }
    }

    override fun findOuterDataByInnerDataQueryParam(realm: Realm, value: String): Observable<RealmResults<RealmPerformanceDataOuter>> {
        return rxFactory.from(realm, realm.where(RealmPerformanceDataOuter::class.java).equalTo("data5.queryParam", value).findAllAsync())
                .filter { it.isLoaded }

    }

    override fun deleteAll(realm: Realm): Completable {
        return Completable.fromAction {
            realm.executeTransaction {
                it.delete(RealmPerformanceDataOuter::class.java)
                it.delete(RealmPerformanceDataInner::class.java)
            }
        }
    }
}