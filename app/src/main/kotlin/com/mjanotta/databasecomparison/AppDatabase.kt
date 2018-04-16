package com.mjanotta.databasecomparison

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.mjanotta.databasecomparison.room.entity.RoomPerformanceDataOuter
import com.mjanotta.databasecomparison.room.model.RoomPerformanceDao


@Database(version = 1,
        entities = arrayOf(
                RoomPerformanceDataOuter::class
        ))

abstract class AppDatabase : RoomDatabase() {

    abstract fun performanceDao(): RoomPerformanceDao
}