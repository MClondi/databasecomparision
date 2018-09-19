package com.mjanotta.databasecomparison.objectbox.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class ObjectBoxPerformanceDataInner(
        @Id var id: Long = 0,
        var data5: String = "",
        var data6: String = "",
        var data7: String = "",
        var data8: String = "",
        var data9: String = "",
        var data10: String = "",
        var queryParam: String = ""
)