package com.trackfox.app.data.room.dao

import androidx.room.*
import com.trackfox.app.data.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user : User) : Long

    @Delete(entity = User::class)
    suspend fun deleteUserById(id : Long)

    @Query("SELECT * FROM users WHERE name = :name")
    suspend fun getUserByName(name : String) : User?
}