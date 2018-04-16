package com.mjanotta.databasecomparison.home

import io.reactivex.rxkotlin.addTo
import org.parceler.Parcel
import org.parceler.ParcelConstructor

class HomePresenterImpl(view: HomeView, val router: HomeRouter) : HomePresenter<HomePresenterImpl.State>(view) {

    override var realmPerformanceSelected = false
    override var objectBoxPerformanceSelected = false
    override var roomPerformanceSelected = false

    override fun bindView() {

        view.realmPerformanceClicks
                .doOnNext {
                    realmPerformanceSelected = true
                    objectBoxPerformanceSelected = false
                    realmPerformanceSelected = false
                }
                .subscribe { router.openRealmPerformance() }
                .addTo(disposables)
        view.objectBoxPerformanceClicks
                .doOnNext {
                    realmPerformanceSelected = false
                    objectBoxPerformanceSelected = true
                    realmPerformanceSelected = false
                }
                .subscribe { router.openObjectBoxPerformance() }
                .addTo(disposables)
        view.roomPerformanceClicks
                .doOnNext {
                    realmPerformanceSelected = false
                    objectBoxPerformanceSelected = false
                    realmPerformanceSelected = true
                }
                .subscribe { router.openRoomPerformance() }
                .addTo(disposables)
    }

    override fun restoreState(state: State?) {
        state?.let {
            realmPerformanceSelected = it.realmPerformanceSelected
            objectBoxPerformanceSelected = it.objectBoxPerformanceSelected
            roomPerformanceSelected = it.roomPerformanceSelected
        }
    }

    override fun saveState(): State? = State(
            realmPerformanceSelected,
            objectBoxPerformanceSelected,
            roomPerformanceSelected)

    @Parcel(Parcel.Serialization.BEAN)
    class State @ParcelConstructor constructor(
            val realmPerformanceSelected: Boolean,
            val objectBoxPerformanceSelected: Boolean,
            val roomPerformanceSelected: Boolean
    )
}