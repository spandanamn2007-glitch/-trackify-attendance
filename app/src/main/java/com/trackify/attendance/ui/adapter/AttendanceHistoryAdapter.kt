package com.trackify.attendance.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trackify.attendance.data.entity.Attendance
import com.trackify.attendance.data.entity.Student
import com.trackify.attendance.databinding.ItemAttendanceHistoryBinding

class AttendanceHistoryAdapter(
    private val students: List<Student>
) : ListAdapter<Attendance, AttendanceHistoryAdapter.ViewHolder>(AttendanceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAttendanceHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val attendance = getItem(position)
        val student = students.find { it.id == attendance.studentId }
        holder.bind(attendance, student?.name ?: "Unknown Student")
    }

    inner class ViewHolder(private val binding: ItemAttendanceHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(attendance: Attendance, studentName: String) {
            binding.tvStudentName.text = studentName
            binding.tvStatus.text = attendance.status
            if (attendance.status == "Absent") {
                binding.tvStatus.setTextColor(Color.RED)
            } else {
                binding.tvStatus.setTextColor(Color.parseColor("#4CAF50")) // Green
            }
        }
    }

    class AttendanceDiffCallback : DiffUtil.ItemCallback<Attendance>() {
        override fun areItemsTheSame(oldItem: Attendance, newItem: Attendance): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Attendance, newItem: Attendance): Boolean = oldItem == newItem
    }
}
