package com.mjanotta.databasecomparison.core

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<out V, S>(val view: V)  where V : BaseView {

    val disposables = CompositeDisposable()

    open fun bindView() {}

    open fun restoreState(state: S?) {}

    open fun saveState(): S? {
        return null
    }

    open fun destroy() {
        disposables.clear()
    }
}