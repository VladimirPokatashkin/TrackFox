package com.trackfox.app.data.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.trackfox.app.data.entity.*
import com.trackfox.app.data.room.dao.TrainingDao
import com.trackfox.app.data.room.dao.UserDao

@androidx.room.Database(entities = [TrainingDB::class, User::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun trainingDao() : TrainingDao
    abstract fun userDao() : UserDao

    companion object {
        @Volatile
        private var INSTANCE : Database? = null

        fun getDatabase(context : Context) : Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Database::class.java,
                    "trackfoxDatabase.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}