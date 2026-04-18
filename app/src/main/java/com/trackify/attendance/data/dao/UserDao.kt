package com.trackify.attendance.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.trackify.attendance.data.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    suspend fun login(username: String, password: String): User?

    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE role = 'student' AND studentId = :studentId LIMIT 1")
    suspend fun getUserByStudentId(studentId: Int): User?
}
