package com.trackify.attendance.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.trackify.attendance.TrackifyApplication
import com.trackify.attendance.databinding.ActivityStudentDashboardBinding
import com.trackify.attendance.ui.adapter.AttendanceAdapter
import com.trackify.attendance.viewmodel.AttendanceViewModel
import com.trackify.attendance.viewmodel.AttendanceViewModelFactory

class StudentDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentDashboardBinding
    private val viewModel: AttendanceViewModel by viewModels {
        AttendanceViewModelFactory((application as TrackifyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val studentId = intent.getIntExtra("STUDENT_ID", -1)
        if (studentId == -1) {
            finish()
            return
        }

        val adapter = AttendanceAdapter()
        binding.rvAttendanceHistory.adapter = adapter

        viewModel.getStudentById(studentId).observe(this) { student ->
            student?.let {
                binding.tvStudentInfo.text = "${it.name} - ${it.className}"
            }
        }

        viewModel.getAttendanceByStudent(studentId).observe(this) { attendanceList ->
            adapter.submitList(attendanceList)
            
            if (attendanceList.isNotEmpty()) {
                val presentCount = attendanceList.count { it.status == "Present" }
                val percentage = (presentCount.toDouble() / attendanceList.size * 100).toInt()
                binding.tvPercentage.text = "$percentage%"
            } else {
                binding.tvPercentage.text = "0%"
            }
        }
    }
}
