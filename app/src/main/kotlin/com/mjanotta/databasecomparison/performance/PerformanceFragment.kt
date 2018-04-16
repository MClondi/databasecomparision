package com.mjanotta.databasecomparison.performance

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.afollestad.materialdialogs.MaterialDialog
import com.mjanotta.databasecomparison.R
import com.mjanotta.databasecomparison.core.BaseFragment
import com.mjanotta.databasecomparison.util.tint
import com.jakewharton.rxbinding2.view.RxMenuItem
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_performance.*

abstract class PerformanceFragment<T> : BaseFragment<PerformancePresenter<*, T>>(), PerformanceView<T> {

    override lateinit var startTestClicks: Observable<Any>

    private var progressDialog: MaterialDialog? = null
    private val performanceAdapter: FastItemAdapter<PerformanceItem<T>> = FastItemAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_performance, container, false)
    }

    override fun setView(view: View, savedInstanceState: Bundle?) {
        performances.layoutManager = LinearLayoutManager(activity)
        performances.adapter = performanceAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_performance, menu)
        val startMenuItem = menu.findItem(R.id.action_start)
        startMenuItem.tint(R.color.colorWhite, activity)
        startTestClicks = RxMenuItem.clicks(startMenuItem)
        presenter.bindMenu()
    }

    override fun showData(data: List<T>) {
        val items = data.map { mapItem(it) }
        performanceAdapter.set(items)
    }

    override fun showProgress() {
        progressDialog = MaterialDialog.Builder(activity)
                .progress(true, 0)
                .content(R.string.performanceLoading)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .show()
    }

    override fun hideProgress() {
        progressDialog?.dismiss()
    }

    override fun showError(error: String) {
        Snackbar.make(coordinator, error, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideProgress()
    }

    abstract fun mapItem(item: T): PerformanceItem<T>
}