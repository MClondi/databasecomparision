package com.mjanotta.databasecomparison.core

import android.os.Bundle
import org.parceler.Parcels

class ParcelerSaveStateDelegate : SaveStateDelegate {

    companion object {
        const val BUNDLE_PRESENTER_STATE = "PRESENTER_STATE"
    }

    override fun <S> restoreState(savedInstanceState: Bundle?, presenter: BasePresenter<*, S>) {
        savedInstanceState?.let {
            val state: S = Parcels.unwrap(it.getParcelable(BUNDLE_PRESENTER_STATE))
            presenter.restoreState(state)
        }
    }

    override fun <S> saveState(outState: Bundle, presenter: BasePresenter<*, S>) {
        val state: S? = presenter.saveState()
        outState.putParcelable(BUNDLE_PRESENTER_STATE, Parcels.wrap(state))
    }
}