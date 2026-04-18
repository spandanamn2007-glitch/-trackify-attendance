package com.trackify.attendance.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trackify.attendance.data.entity.Attendance
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(attendance: Attendance)

    @Query("SELECT * FROM attendance WHERE date = :date")
    fun getAttendanceByDate(date: String): Flow<List<Attendance>>

    @Query("SELECT * FROM attendance WHERE studentId = :studentId")
    fun getAttendanceByStudent(studentId: Int): Flow<List<Attendance>>

    @Query("SELECT EXISTS(SELECT 1 FROM attendance WHERE studentId = :studentId AND date = :date)")
    suspend fun isAttendanceMarked(studentId: Int, date: String): Boolean
    
    @Query("SELECT * FROM attendance")
    fun getAllAttendance(): Flow<List<Attendance>>
}
