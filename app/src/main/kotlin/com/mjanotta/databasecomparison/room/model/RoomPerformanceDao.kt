package com.mjanotta.databasecomparison.room.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.mjanotta.databasecomparison.room.entity.RoomPerformanceDataOuter
import io.reactivex.Flowable

@Dao
interface RoomPerformanceDao {

    @Query("SELECT * FROM performance")
    fun findAll(): Flowable<List<RoomPerformanceDataOuter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entities: List<RoomPerformanceDataOuter>)

    @Query("DELETE FROM performance")
    fun deleteAll()
}