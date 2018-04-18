package com.mjanotta.databasecomparison.sqlite.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.mjanotta.databasecomparison.sqlite.entity.SqlitePerformanceDataOuter
import io.reactivex.Flowable

class SqlitePerformanceDaoImpl(context: Context) : SQLiteOpenHelper(context, DatabaseContract.DATABASE_NAME, null, 1), SqlitePerformanceDao {

    private val CREATE_TABLE_INNER = "CREATE TABLE ${DatabaseContract.InnerData.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${DatabaseContract.InnerData.COLUMN_5} TEXT," +
            "${DatabaseContract.InnerData.COLUMN_6} TEXT," +
            "${DatabaseContract.InnerData.COLUMN_7} TEXT," +
            "${DatabaseContract.InnerData.COLUMN_8} TEXT," +
            "${DatabaseContract.InnerData.COLUMN_9} TEXT," +
            "${DatabaseContract.InnerData.COLUMN_10} TEXT)"

    private val CREATE_TABLE_OUTER = "CREATE TABLE ${DatabaseContract.OuterData.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun save(entities: List<SqlitePerformanceDataOuter>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}