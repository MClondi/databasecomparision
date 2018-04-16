package com.mjanotta.databasecomparison.home

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance


val homeModule = Kodein.Module {
    bind<HomePresenter<*>>() with autoScopedSingleton(androidActivityScope) { HomePresenterImpl(it as HomeView, instance()) }
    bind<HomeRouter>() with autoScopedSingleton(androidActivityScope) { HomeRouterImpl(it.fragmentManager) }
}