package com.trackify.attendance.viewmodel

import androidx.lifecycle.*
import com.trackify.attendance.data.entity.Attendance
import com.trackify.attendance.data.entity.Student
import com.trackify.attendance.data.entity.User
import com.trackify.attendance.repository.AttendanceRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.liveData

class AttendanceViewModel(private val repository: AttendanceRepository) : ViewModel() {

    val allStudents: LiveData<List<Student>> = repository.allStudents.asLiveData()
    
    fun getAttendanceByDate(date: String): LiveData<List<Attendance>> = 
        repository.getAttendanceByDate(date).asLiveData()

    fun getAttendanceByStudent(studentId: Int): LiveData<List<Attendance>> = 
        repository.getAttendanceByStudent(studentId).asLiveData()

    fun getAllAttendance(): LiveData<List<Attendance>> = 
        repository.getAllAttendance().asLiveData()

    suspend fun login(username: String, password: String): User? = 
        repository.login(username, password)

    fun insertStudent(name: String, className: String) = viewModelScope.launch {
        val studentId = repository.insertStudent(Student(name = name, className = className))
        // Create a student user account as well
        repository.insertUser(User(username = name.lowercase().replace(" ", ""), password = "123", role = "student", studentId = studentId.toInt()))
    }

    fun updateStudent(student: Student) = viewModelScope.launch {
        repository.updateStudent(student)
    }

    fun deleteStudent(student: Student) = viewModelScope.launch {
        repository.deleteStudent(student)
    }

    fun markAttendance(studentId: Int, date: String, status: String) = viewModelScope.launch {
        repository.insertAttendance(Attendance(studentId = studentId, date = date, status = status))
    }

    suspend fun isAttendanceMarked(studentId: Int, date: String): Boolean = 
        repository.isAttendanceMarked(studentId, date)

    fun getStudentById(id: Int): LiveData<Student?> = liveData {
        emit(repository.getStudentById(id))
    }
}

class AttendanceViewModelFactory(private val repository: AttendanceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AttendanceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AttendanceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
