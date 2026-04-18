package com.trackify.attendance.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.trackify.attendance.TrackifyApplication
import com.trackify.attendance.databinding.ActivityLoginBinding
import com.trackify.attendance.viewmodel.AttendanceViewModel
import com.trackify.attendance.viewmodel.AttendanceViewModelFactory
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AttendanceViewModel by viewModels {
        AttendanceViewModelFactory((application as TrackifyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val user = viewModel.login(username, password)
                if (user != null) {
                    when (user.role) {
                        "teacher" -> {
                            startActivity(Intent(this@LoginActivity, TeacherDashboardActivity::class.java))
                            finish()
                        }
                        "student" -> {
                            val intent = Intent(this@LoginActivity, StudentDashboardActivity::class.java)
                            intent.putExtra("STUDENT_ID", user.studentId)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
