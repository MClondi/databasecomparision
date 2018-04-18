package com.mjanotta.databasecomparison.sqlite.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

data class SqlitePerformanceDataOuter(
        var id: Int,
        var data1: String,
        var data2: String,
        var data3: String,
        var data4: String,
        var data5: SqlitePerformanceDataInner
)