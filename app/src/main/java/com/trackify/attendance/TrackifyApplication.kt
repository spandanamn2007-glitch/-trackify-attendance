package com.trackify.attendance

import android.app.Application
import com.trackify.attendance.data.AppDatabase
import com.trackify.attendance.repository.AttendanceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TrackifyApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { 
        AttendanceRepository(database.userDao(), database.studentDao(), database.attendanceDao()) 
    }
}
