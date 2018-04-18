package com.mjanotta.databasecomparison.sqlite.model

import android.provider.BaseColumns

object DatabaseContract {

    const val DATABASE_NAME = "PerformanceDb"

    object OuterData: BaseColumns {
        const val TABLE_NAME = "OUTER_table"
        const val COLUMN_1 = "COL1"
        const val COLUMN_2 = "COL2"
        const val COLUMN_3 = "COL3"
        const val COLUMN_4 = "COL4"
        const val COLUMN_5 = "COL5"
    }

    object InnerData: BaseColumns {
        const val TABLE_NAME = "inner_table"
        const val COLUMN_5 = "COL5"
        const val COLUMN_6 = "COL6"
        const val COLUMN_7 = "COL7"
        const val COLUMN_8 = "COL8"
        const val COLUMN_9 = "COL9"
        const val COLUMN_10 = "COL10"
    }

}