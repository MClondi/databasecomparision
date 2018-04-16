package com.mjanotta.databasecomparison.core

import android.app.Fragment
import android.os.Bundle
import android.view.View
import com.mjanotta.databasecomparison.core.BasePresenter
import com.mjanotta.databasecomparison.core.ParcelerSaveStateDelegate
import com.mjanotta.databasecomparison.core.SaveStateDelegate
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<out P> : SaveStateDelegate by ParcelerSaveStateDelegate(), Fragment() where P : BasePresenter<*, *> {

    protected val kodein = LazyKodein(appKodein)
    protected val disposables = CompositeDisposable()

    protected abstract val presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreState(savedInstanceState, presenter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView()
        setView(view, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveState(outState, presenter)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
        presenter.destroy()
    }

    protected abstract fun setView(view: View, savedInstanceState: Bundle?)
}