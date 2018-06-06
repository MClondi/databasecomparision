package com.mjanotta.databasecomparison.sqlite.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.mjanotta.databasecomparison.sqlite.entity.SqlitePerformanceDataInner
import com.mjanotta.databasecomparison.sqlite.entity.SqlitePerformanceDataOuter
import io.reactivex.Flowable

class SqlitePerformanceDaoImpl(context: Context) : SQLiteOpenHelper(context, DatabaseContract.DATABASE_NAME, null, 1), SqlitePerformanceDao {

    private val CREATE_TABLE_INNER = "CREATE TABLE ${DatabaseContract.InnerData.TABLE_NAME} (" +
            "${BaseColumns._ID} AUTOINCREMENT INTEGER PRIMARY KEY," +
            "${DatabaseContract.InnerData.COLUMN_5} TEXT," +
            "${DatabaseContract.InnerData.COLUMN_6} TEXT," +
            "${DatabaseContract.InnerData.COLUMN_7} TEXT," +
            "${DatabaseContract.InnerData.COLUMN_8} TEXT," +
            "${DatabaseContract.InnerData.COLUMN_9} TEXT," +
            "${DatabaseContract.InnerData.COLUMN_10} TEXT)"

    private val CREATE_TABLE_OUTER = "CREATE TABLE ${DatabaseContract.OuterData.TABLE_NAME} (" +
            "${BaseColumns._ID} AUTOINCREMENT INTEGER PRIMARY KEY," +
            "${DatabaseContract.OuterData.COLUMN_1} TEXT," +
            "${DatabaseContract.OuterData.COLUMN_2} TEXT," +
            "${DatabaseContract.OuterData.COLUMN_3} TEXT," +
            "${DatabaseContract.OuterData.COLUMN_4} TEXT," +
            "${DatabaseContract.OuterData.COLUMN_5} INTEGER," +
            "FOREIGN KEY(${DatabaseContract.OuterData.COLUMN_5}) REFERENCES ${DatabaseContract.InnerData.TABLE_NAME}(${BaseColumns._ID})"

    private val DELETE_TABLES = "DROP TABLE IF EXISTS ${DatabaseContract.InnerData.TABLE_NAME};" +
            "DROP TABLE IF EXISTS ${DatabaseContract.OuterData.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_INNER)
        db.execSQL(CREATE_TABLE_OUTER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DELETE_TABLES)
        onCreate(db)
    }

    override fun findAll(): Flowable<List<SqlitePerformanceDataOuter>> {
        return Flowable.fromCallable {
            val list = mutableListOf<SqlitePerformanceDataOuter>()
            val cursor = readableDatabase.rawQuery("SELECT * FORM ${DatabaseContract.OuterData.TABLE_NAME}", null)

            if (cursor.moveToFirst()) {
                do {

                    val innerCursor = readableDatabase.rawQuery("SELECT * FORM ${DatabaseContract.OuterData.TABLE_NAME} WHERE ${BaseColumns._ID} EQUALS ${cursor.getInt(5)}", null)
                    var innerObject: SqlitePerformanceDataInner? = null
                    if (innerCursor.moveToFirst()) {
                        innerObject = SqlitePerformanceDataInner(
                                innerCursor.getString(0),
                                innerCursor.getString(1),
                                innerCursor.getString(2),
                                innerCursor.getString(3),
                                innerCursor.getString(4),
                                innerCursor.getString(5)
                        )
                    }
                    innerCursor.close()

                    list.add(SqlitePerformanceDataOuter(
                            cursor.getColumnIndexOrThrow(BaseColumns._ID),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            innerObject!!
                    ))
                } while (cursor.moveToNext())
            }
            cursor.close()

            return@fromCallable list
        }
    }

    override fun save(entities: List<SqlitePerformanceDataOuter>) {
        entities.forEach { entity ->

            val innerContentValue = ContentValues().apply {
                put(DatabaseContract.InnerData.COLUMN_5, entity.data5.data5)
                put(DatabaseContract.InnerData.COLUMN_6, entity.data5.data6)
                put(DatabaseContract.InnerData.COLUMN_7, entity.data5.data7)
                put(DatabaseContract.InnerData.COLUMN_8, entity.data5.data8)
                put(DatabaseContract.InnerData.COLUMN_9, entity.data5.data9)
                put(DatabaseContract.InnerData.COLUMN_10, entity.data5.data10)
            }
            val innerDataId = writableDatabase.insert(DatabaseContract.InnerData.TABLE_NAME, null, innerContentValue)

            val outerContentValue = ContentValues().apply {
                put(DatabaseContract.OuterData.COLUMN_1, entity.data1)
                put(DatabaseContract.OuterData.COLUMN_2, entity.data2)
                put(DatabaseContract.OuterData.COLUMN_3, entity.data3)
                put(DatabaseContract.OuterData.COLUMN_4, entity.data4)
                put(DatabaseContract.OuterData.COLUMN_5, innerDataId)
            }

            writableDatabase.insert(DatabaseContract.OuterData.TABLE_NAME, null, outerContentValue)

        }
    }

    override fun deleteAll() {
        this.readableDatabase.execSQL(DELETE_TABLES)
     }

}