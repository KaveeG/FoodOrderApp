package com.example.food.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.food.R
import com.example.food.databinding.ActivityLoginBinding
import com.example.food.databinding.ActivitySignupBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()


        setVariable()
        }
    private fun setVariable() {
        binding.LoginBtn.setOnClickListener {
            val email = binding.userEdt.text.toString()
            val password = binding.passEdt.text.toString()

            if (!email.isEmpty() && !password.isEmpty()) {
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@LoginActivity) { task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Authentication failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }else {
                Toast.makeText(
                    this@LoginActivity,
                    "Please fill username and password",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    }
}
