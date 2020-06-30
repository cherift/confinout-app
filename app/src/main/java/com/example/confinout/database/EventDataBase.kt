package com.example.confinout.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.confinout.model.MyEvent

@Database(entities = [(MyEvent::class)], version = 2, exportSchema = false)
abstract class EventDataBase : RoomDatabase() {

    abstract fun eventDao(): EventDao

    // Setting the object for only one time
    companion object {
        @Volatile
        private var INSTANCE : EventDataBase? = null

        fun getInstance(context: Context): EventDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventDataBase::class.java,
                    "database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}