package com.example.food.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.food.R
import com.example.food.databinding.ActivityIntroBinding
import com.example.food.databinding.ActivitySignupBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()


        mAuth = FirebaseAuth.getInstance()


        setVariable()
        }
    private fun setVariable() {
        binding.signupBtn.setOnClickListener {
            val email = binding.userEdt.text.toString()
            val password = binding.passEdt.text.toString()

            if (password.length < 6) {
                Toast.makeText(this@SignupActivity, "Your password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@SignupActivity) { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        Log.i("Signup", "Signup Successful")
                        startActivity(Intent(this@SignupActivity, MainActivity::class.java))
                        Toast.makeText(this@SignupActivity, "Signup Successful", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.i("Signup", "Failure: ", task.exception)
                        Toast.makeText(this@SignupActivity, "Authentication failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
