package com.mjanotta.databasecomparison.home

import com.mjanotta.databasecomparison.core.BasePresenter
import com.mjanotta.databasecomparison.core.BaseView
import io.reactivex.Observable

interface HomeView : BaseView {

    val realmPerformanceClicks: Observable<Any>
    val objectBoxPerformanceClicks: Observable<Any>
    val roomPerformanceClicks: Observable<Any>
}

abstract class HomePresenter<S>(view: HomeView) : BasePresenter<HomeView, S>(view) {

    abstract var realmPerformanceSelected: Boolean
    abstract var objectBoxPerformanceSelected: Boolean
    abstract var roomPerformanceSelected: Boolean
}

interface HomeRouter {


    fun openRealmPerformance()

    fun openObjectBoxPerformance()

    fun openRoomPerformance()
}