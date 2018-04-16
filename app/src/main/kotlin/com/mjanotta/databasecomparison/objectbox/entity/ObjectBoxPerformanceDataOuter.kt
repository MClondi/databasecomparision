package com.mjanotta.databasecomparison.objectbox.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class ObjectBoxPerformanceDataOuter(
        @Id var id: Long = 0,
        var data1: String = "",
        var data2: String = "",
        var data3: String = "",
        var data4: String = ""
) {

    lateinit var data5: ToOne<ObjectBoxPerformanceDataInner>
}
