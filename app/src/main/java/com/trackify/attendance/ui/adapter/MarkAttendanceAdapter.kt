package com.trackify.attendance.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trackify.attendance.data.entity.Student
import com.trackify.attendance.databinding.ItemMarkAttendanceBinding

class MarkAttendanceAdapter : ListAdapter<Student, MarkAttendanceAdapter.ViewHolder>(StudentDiffCallback()) {

    private val attendanceStatusMap = mutableMapOf<Int, String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMarkAttendanceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getAttendanceData(): Map<Int, String> = attendanceStatusMap

    inner class ViewHolder(private val binding: ItemMarkAttendanceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(student: Student) {
            binding.tvStudentName.text = student.name
            binding.tvClassName.text = student.className
            
            // Default to Present
            if (!attendanceStatusMap.containsKey(student.id)) {
                attendanceStatusMap[student.id] = "Present"
            }
            
            binding.switchStatus.isChecked = attendanceStatusMap[student.id] == "Present"
            binding.switchStatus.text = attendanceStatusMap[student.id]

            binding.switchStatus.setOnCheckedChangeListener { _, isChecked ->
                val status = if (isChecked) "Present" else "Absent"
                attendanceStatusMap[student.id] = status
                binding.switchStatus.text = status
            }
        }
    }

    class StudentDiffCallback : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean = oldItem == newItem
    }
}
