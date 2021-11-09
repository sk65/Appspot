package com.yefimoyevhen.appspot.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.yefimoyevhen.appspot.database.model.User

@Dao
interface AppspotDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertUser(user: User)

    @Query("DELETE FROM user")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user")
    suspend fun findAllUsers(): List<User>

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun findUserById(id: String): User

}