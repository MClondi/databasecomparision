package com.mjanotta.databasecomparison.realm.model

import io.reactivex.Observable
import io.reactivex.disposables.Disposables
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmModel
import io.realm.RealmResults
import java.util.*


class RxFactory {

    // Maps for storing strong references to Realm classes while they are subscribed to.
    // This is needed if users create Observables without manually maintaining a reference to them.
    // In that case RealmObjects/RealmResults/RealmLists might be GC'ed too early.
    private val resultsRefs: ThreadLocal<StrongReferenceCounter<RealmResults<*>>> = object : ThreadLocal<StrongReferenceCounter<RealmResults<*>>>() {
        override fun initialValue(): StrongReferenceCounter<RealmResults<*>> {
            return StrongReferenceCounter()
        }
    }

    fun <E : RealmModel> from(realm: Realm, results: RealmResults<E>): Observable<RealmResults<E>> {
        val realmConfig = realm.configuration
        return Observable.create {
            // Gets instance to make sure that the Realm is open for as long as the
            // Observable is subscribed to it.
            val observableRealm = Realm.getInstance(realmConfig)
            resultsRefs.get().acquireReference(results)

            val listener = RealmChangeListener<RealmResults<E>> { _ ->
                if (!it.isDisposed) {
                    it.onNext(results)
                }
            }
            results.addChangeListener(listener)
            it.setDisposable(Disposables.fromAction {
                results.removeChangeListener(listener)
                observableRealm.close()
                resultsRefs.get().releaseReference(results)
            })

            // Immediately calls onNext with the current value, as due to Realm's auto-update, it will be the latest
            // value.
            it.onNext(results)
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is RxFactory
    }

    override fun hashCode(): Int {
        return 37
    }

    // Helper class for keeping track of strong references to objects.
    private class StrongReferenceCounter<in K> {

        private val references = IdentityHashMap<K, Int>()

        fun acquireReference(`object`: K) {
            val count = references[`object`]
            if (count == null) {
                references.put(`object`, 1)
            } else {
                references.put(`object`, count + 1)
            }
        }

        fun releaseReference(`object`: K) {
            val count = references[`object`]
            if (count == null) {
                throw IllegalStateException("Object does not have any references: " + `object`)
            } else if (count > 1) {
                references.put(`object`, count - 1)
            } else if (count == 1) {
                references.remove(`object`)
            } else {
                throw IllegalStateException("Invalid reference count: " + count)
            }
        }
    }
}