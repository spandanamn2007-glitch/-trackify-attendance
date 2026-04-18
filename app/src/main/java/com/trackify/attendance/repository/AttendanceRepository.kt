package com.trackify.attendance.repository

import com.trackify.attendance.data.dao.AttendanceDao
import com.trackify.attendance.data.dao.StudentDao
import com.trackify.attendance.data.dao.UserDao
import com.trackify.attendance.data.entity.Attendance
import com.trackify.attendance.data.entity.Student
import com.trackify.attendance.data.entity.User
import kotlinx.coroutines.flow.Flow

class AttendanceRepository(
    private val userDao: UserDao,
    private val studentDao: StudentDao,
    private val attendanceDao: AttendanceDao
) {
    // User operations
    suspend fun login(username: String, password: String): User? = userDao.login(username, password)
    suspend fun insertUser(user: User) = userDao.insert(user)
    suspend fun getUserByStudentId(studentId: Int) = userDao.getUserByStudentId(studentId)

    // Student operations
    val allStudents: Flow<List<Student>> = studentDao.getAllStudents()
    suspend fun insertStudent(student: Student): Long = studentDao.insert(student)
    suspend fun updateStudent(student: Student) = studentDao.update(student)
    suspend fun deleteStudent(student: Student) = studentDao.delete(student)
    suspend fun getStudentById(id: Int) = studentDao.getStudentById(id)

    // Attendance operations
    fun getAttendanceByDate(date: String): Flow<List<Attendance>> = attendanceDao.getAttendanceByDate(date)
    fun getAttendanceByStudent(studentId: Int): Flow<List<Attendance>> = attendanceDao.getAttendanceByStudent(studentId)
    suspend fun insertAttendance(attendance: Attendance) = attendanceDao.insert(attendance)
    suspend fun isAttendanceMarked(studentId: Int, date: String): Boolean = attendanceDao.isAttendanceMarked(studentId, date)
    fun getAllAttendance(): Flow<List<Attendance>> = attendanceDao.getAllAttendance()
}
