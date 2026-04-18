package com.trackify.attendance.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.trackify.attendance.databinding.ActivityTeacherDashboardBinding

class TeacherDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeacherDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeacherDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardManageStudents.setOnClickListener {
            startActivity(Intent(this, ManageStudentsActivity::class.java))
        }

        binding.cardMarkAttendance.setOnClickListener {
            startActivity(Intent(this, MarkAttendanceActivity::class.java))
        }

        binding.cardViewAttendance.setOnClickListener {
            startActivity(Intent(this, AttendanceHistoryActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
    }
}