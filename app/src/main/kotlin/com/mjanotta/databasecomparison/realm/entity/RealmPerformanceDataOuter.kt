package com.mjanotta.databasecomparison.realm.entity

import io.realm.RealmObject

open class RealmPerformanceDataOuter(
        var data1: String = "",
        var data2: String = "",
        var data3: String = "",
        var data4: String = "",
        var data5: RealmPerformanceDataInner? = RealmPerformanceDataInner()
) : RealmObject()