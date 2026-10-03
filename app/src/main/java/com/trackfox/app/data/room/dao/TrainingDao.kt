package com.trackfox.app.data.room.dao

import androidx.room.*
import com.trackfox.app.data.entity.TrainingDB

@Dao
interface TrainingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTraining(trainingDB : TrainingDB)
}