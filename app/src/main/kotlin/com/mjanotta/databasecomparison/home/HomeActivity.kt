package com.mjanotta.databasecomparison.home

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.mjanotta.databasecomparison.R
import com.mjanotta.databasecomparison.core.BaseActivity
import com.github.salomonbrys.kodein.instance
import com.jakewharton.rxbinding2.support.design.widget.RxBottomNavigationView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity<HomePresenter<*>>(), HomeView {

    private val navigationClicks: Observable<MenuItem> by lazy {
        RxBottomNavigationView.itemSelections(bottomNavigation).cache()
    }

    override val presenter: HomePresenter<*> by kodein.instance()


    override val realmPerformanceClicks: Observable<Any> by lazy {
        navigationClicks
                .filter { it.itemId == R.id.action_realm_performance }
                .cast(Any::class.java)
    }

    override val objectBoxPerformanceClicks: Observable<Any>by lazy {
        navigationClicks
                .filter { it.itemId == R.id.action_object_box_performance }
                .cast(Any::class.java)
    }

    override val roomPerformanceClicks: Observable<Any>by lazy {
        navigationClicks
                .filter { it.itemId == R.id.action_room_performance }
                .cast(Any::class.java)
    }

    override fun setView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        initBottomNavigation()
    }

    private fun initBottomNavigation() = when {
        presenter.realmPerformanceSelected -> bottomNavigation.selectedItemId = R.id.action_realm_performance
        presenter.objectBoxPerformanceSelected -> bottomNavigation.selectedItemId = R.id.action_object_box_performance
        else -> bottomNavigation.selectedItemId = R.id.action_room_performance
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}