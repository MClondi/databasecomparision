package com.mjanotta.databasecomparison

import android.arch.persistence.room.Room
import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton
import com.mjanotta.databasecomparison.objectbox.entity.MyObjectBox
import io.objectbox.BoxStore
import io.realm.Realm
import io.realm.RealmConfiguration


fun appModule(context: Context) = Kodein.Module {

    bind<BoxStore>() with singleton {
        MyObjectBox.builder()
                .androidContext(context)
                .build()
    }

    bind<AppDatabase>() with singleton {
        Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
    }

    bind<RealmConfiguration>() with singleton {
        RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
    }

    bind<Realm>() with provider { Realm.getDefaultInstance() }
}