package com.example.tp3.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.example.tp3.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User): Long

    // Change Flow<User?> → suspend fun User?
    @Query("SELECT * FROM users ORDER BY id DESC LIMIT 1")
    suspend fun getLastUser(): User?

    @Query("SELECT * FROM users WHERE login = :login")
    suspend fun getUserByLogin(login: String): User?

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

}

