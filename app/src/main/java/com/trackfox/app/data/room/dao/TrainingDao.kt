package com.trackfox.app.data.room.dao

import androidx.room.*
import com.trackfox.app.data.entity.TrainingDB

@Dao
interface TrainingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTraining(trainingDB : TrainingDB)

    @Query("SELECT * FROM trainings WHERE userId = :userId")
    suspend fun getAllUsersTrainings(userId : Long) : List<TrainingDB>

    @Query("SELECT * FROM trainings WHERE id = :id")
    suspend fun getById(id : Long) : TrainingDB
}