package com.mjanotta.databasecomparison.realm.entity

import io.realm.RealmObject

open class RealmPerformanceDataInner(
        var data5: String = "",
        var data6: String = "",
        var data7: String = "",
        var data8: String = "",
        var data9: String = "",
        var data10: String = "",
        var queryParam: String = ""
) : RealmObject()