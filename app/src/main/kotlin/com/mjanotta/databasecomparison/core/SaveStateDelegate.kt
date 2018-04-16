package com.mjanotta.databasecomparison.core

import android.os.Bundle

interface SaveStateDelegate {

    fun <S> restoreState(savedInstanceState: Bundle?, presenter: BasePresenter<*, S>)

    fun <S> saveState(outState: Bundle, presenter: BasePresenter<*, S>)
}