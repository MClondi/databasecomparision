package com.mjanotta.databasecomparison

import android.arch.persistence.room.Room
import android.content.Context
import com.mjanotta.databasecomparison.objectbox.entity.MyObjectBox
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.github.salomonbrys.kodein.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.objectbox.BoxStore
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import org.threeten.bp.ZonedDateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


fun appModule(context: Context) = Kodein.Module {
    constant("swapi") with "https://swapi.co/api/"

    bind<OkHttpClient>() with singleton {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.networkInterceptors().add(StethoInterceptor())
        }
        return@singleton builder.build()
    }

    bind<Gson>() with singleton {
        GsonBuilder()
                .registerTypeAdapter(ZonedDateTime::class.java, ZonedDateTimeDeserializer())
                .create()
    }

    bind<Retrofit>() with singleton {
        Retrofit.Builder()
                .baseUrl(instance<String>("swapi"))
                .client(instance())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(instance()))
                .build()
    }

    bind<BoxStore>() with singleton {
        MyObjectBox.builder()
                .androidContext(context)
                .build()
    }

    bind<AppDatabase>() with singleton {
        Room.databaseBuilder(context, AppDatabase::class.java, "swapi-database").build()
    }

    bind<RealmConfiguration>() with singleton {
        RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
    }

    bind<Realm>() with provider { Realm.getDefaultInstance() }
}