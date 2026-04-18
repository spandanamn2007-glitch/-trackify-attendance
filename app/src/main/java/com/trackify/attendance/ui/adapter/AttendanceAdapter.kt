package com.trackify.attendance.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trackify.attendance.data.entity.Attendance
import com.trackify.attendance.databinding.ItemAttendanceBinding

class AttendanceAdapter : ListAdapter<Attendance, AttendanceAdapter.ViewHolder>(AttendanceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAttendanceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemAttendanceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(attendance: Attendance) {
            binding.tvDate.text = attendance.date
            binding.tvStatus.text = attendance.status
            if (attendance.status == "Absent") {
                binding.tvStatus.setTextColor(Color.RED)
            } else {
                binding.tvStatus.setTextColor(Color.parseColor("#4CAF50"))
            }
        }
    }

    class AttendanceDiffCallback : DiffUtil.ItemCallback<Attendance>() {
        override fun areItemsTheSame(oldItem: Attendance, newItem: Attendance): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Attendance, newItem: Attendance): Boolean = oldItem == newItem
    }
}
