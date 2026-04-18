package com.trackify.attendance.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.trackify.attendance.TrackifyApplication
import com.trackify.attendance.data.entity.Student
import com.trackify.attendance.databinding.ActivityManageStudentsBinding
import com.trackify.attendance.databinding.DialogAddStudentBinding
import com.trackify.attendance.ui.adapter.StudentAdapter
import com.trackify.attendance.viewmodel.AttendanceViewModel
import com.trackify.attendance.viewmodel.AttendanceViewModelFactory

class ManageStudentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManageStudentsBinding
    private val viewModel: AttendanceViewModel by viewModels {
        AttendanceViewModelFactory((application as TrackifyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageStudentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = StudentAdapter(
            onEditClick = { student -> showAddEditDialog(student) },
            onDeleteClick = { student -> viewModel.deleteStudent(student) }
        )
        binding.rvStudents.adapter = adapter

        viewModel.allStudents.observe(this) { students ->
            adapter.submitList(students)
        }

        binding.fabAddStudent.setOnClickListener {
            showAddEditDialog(null)
        }
    }

    private fun showAddEditDialog(student: Student?) {
        val dialogBinding = DialogAddStudentBinding.inflate(LayoutInflater.from(this))
        val builder = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(true)

        val dialog = builder.create()

        if (student != null) {
            dialogBinding.tvDialogTitle.text = "Edit Student"
            dialogBinding.etStudentName.setText(student.name)
            dialogBinding.etClassName.setText(student.className)
        }

        dialogBinding.btnSave.setOnClickListener {
            val name = dialogBinding.etStudentName.text.toString().trim()
            val className = dialogBinding.etClassName.text.toString().trim()

            if (name.isNotEmpty() && className.isNotEmpty()) {
                if (student == null) {
                    viewModel.insertStudent(name, className)
                    Toast.makeText(this, "Student added", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.updateStudent(student.copy(name = name, className = className))
                    Toast.makeText(this, "Student updated", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }
}
