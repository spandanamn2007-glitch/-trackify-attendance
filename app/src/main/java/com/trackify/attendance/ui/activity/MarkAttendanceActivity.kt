package com.trackify.attendance.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.trackify.attendance.TrackifyApplication
import com.trackify.attendance.databinding.ActivityMarkAttendanceBinding
import com.trackify.attendance.ui.adapter.MarkAttendanceAdapter
import com.trackify.attendance.viewmodel.AttendanceViewModel
import com.trackify.attendance.viewmodel.AttendanceViewModelFactory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MarkAttendanceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMarkAttendanceBinding
    private val viewModel: AttendanceViewModel by viewModels {
        AttendanceViewModelFactory((application as TrackifyApplication).repository)
    }

    private val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarkAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvDate.text = "Date: $currentDate"

        val adapter = MarkAttendanceAdapter()
        binding.rvMarkAttendance.adapter = adapter

        viewModel.allStudents.observe(this) { students ->
            adapter.submitList(students)
        }

        binding.btnSaveAttendance.setOnClickListener {
            val attendanceData = adapter.getAttendanceData()
            if (attendanceData.isEmpty()) {
                Toast.makeText(this, "No students to mark attendance", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                var alreadyMarked = false
                for ((studentId, _) in attendanceData) {
                    if (viewModel.isAttendanceMarked(studentId, currentDate)) {
                        alreadyMarked = true
                        break
                    }
                }

                if (alreadyMarked) {
                    Toast.makeText(this@MarkAttendanceActivity, "Attendance already marked for today", Toast.LENGTH_SHORT).show()
                } else {
                    for ((studentId, status) in attendanceData) {
                        viewModel.markAttendance(studentId, currentDate, status)
                    }
                    Toast.makeText(this@MarkAttendanceActivity, "Attendance saved successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
