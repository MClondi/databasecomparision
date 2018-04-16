package com.mjanotta.databasecomparison

import android.app.Application
import com.mjanotta.databasecomparison.home.homeModule
import com.mjanotta.databasecomparison.objectbox.objectBoxPerformanceModule
import com.mjanotta.databasecomparison.performance.performanceModule
import com.mjanotta.databasecomparison.realm.realmPerformanceModule
import com.mjanotta.databasecomparison.room.roomPerformanceModule
import com.facebook.stetho.Stetho
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.android.autoAndroidModule
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mjanotta.databasecomparison.appModule
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import io.realm.Realm
import io.realm.RealmConfiguration

class DatabaseComparisonApplication : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(autoAndroidModule(this@DatabaseComparisonApplication))
        import(appModule(this@DatabaseComparisonApplication))
        import(homeModule)
        import(performanceModule)
        import(realmPerformanceModule)
        import(objectBoxPerformanceModule)
        import(roomPerformanceModule)
    }

    val realmConfig: RealmConfiguration by kodein.lazy.instance()

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)

        registerActivityLifecycleCallbacks(androidActivityScope.lifecycleManager)

        Realm.init(this)
        Realm.setDefaultConfiguration(realmConfig)

        if (BuildConfig.DEBUG) {
            Stetho.initialize(Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                    .build())
        }
    }
}