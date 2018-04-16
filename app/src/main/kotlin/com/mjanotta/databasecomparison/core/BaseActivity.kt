package com.mjanotta.databasecomparison.core

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity<out P> : SaveStateDelegate by ParcelerSaveStateDelegate(), AppCompatActivity() where P : BasePresenter<*, *> {

    protected val kodein = LazyKodein(appKodein)
    protected val disposables = CompositeDisposable()

    protected abstract val presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreState(savedInstanceState, presenter)
        setView(savedInstanceState)
        presenter.bindView()
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

    protected abstract fun setView(savedInstanceState: Bundle?)
}