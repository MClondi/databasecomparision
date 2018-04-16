package com.mjanotta.databasecomparison.room.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "performance")
data class RoomPerformanceDataOuter(
        @PrimaryKey(autoGenerate = true) var id: Int,
        var data1: String,
        var data2: String,
        var data3: String,
        var data4: String,
        @Embedded var data5: RoomPerformanceDataInner
)