package com.trackify.attendance.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.trackify.attendance.TrackifyApplication
import com.trackify.attendance.databinding.ActivityAttendanceHistoryBinding
import com.trackify.attendance.ui.adapter.AttendanceHistoryAdapter
import com.trackify.attendance.viewmodel.AttendanceViewModel
import com.trackify.attendance.viewmodel.AttendanceViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class AttendanceHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAttendanceHistoryBinding
    private val viewModel: AttendanceViewModel by viewModels {
        AttendanceViewModelFactory((application as TrackifyApplication).repository)
    }

    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val initialDate = dateFormat.format(calendar.time)
        binding.btnSelectDate.text = "Date: $initialDate"

        viewModel.allStudents.observe(this) { students ->
            val adapter = AttendanceHistoryAdapter(students)
            binding.rvAttendanceHistory.adapter = adapter

            loadAttendance(initialDate, adapter)

            binding.btnSelectDate.setOnClickListener {
                DatePickerDialog(
                    this,
                    { _, year, month, dayOfMonth ->
                        calendar.set(year, month, dayOfMonth)
                        val selectedDate = dateFormat.format(calendar.time)
                        binding.btnSelectDate.text = "Date: $selectedDate"
                        loadAttendance(selectedDate, adapter)
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
    }

    private fun loadAttendance(date: String, adapter: AttendanceHistoryAdapter) {
        viewModel.getAttendanceByDate(date).observe(this) { attendanceList ->
            adapter.submitList(attendanceList)
            binding.tvNoData.visibility = if (attendanceList.isEmpty()) View.VISIBLE else View.GONE
        }
    }
}
