package com.trackfox.app.data.room.dao

import androidx.room.*
import com.trackfox.app.data.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user : User)

    @Delete(entity = User::class)
    suspend fun deleteUserById(id : Long)
}