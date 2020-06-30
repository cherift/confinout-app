package com.example.confinout.database

import androidx.room.*
import com.example.confinout.model.MyEvent
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: MyEvent) : Completable

    @Query("SELECT * FROM events")
    fun findAll() : Flowable<MutableList<MyEvent>>

    @Delete
    fun delete(event: MyEvent) : Completable
}