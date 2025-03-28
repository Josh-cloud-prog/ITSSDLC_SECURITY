package com.mobdeve.s19.group10.mco2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val firstNameEditText = findViewById<EditText>(R.id.et_register_first_name)
        val lastNameEditText = findViewById<EditText>(R.id.et_register_last_name)
        val emailEditText = findViewById<EditText>(R.id.et_register_email)
        val phoneEditText = findViewById<EditText>(R.id.et_register_phone)
        val passwordEditText = findViewById<EditText>(R.id.et_register_password)
        val confirmPasswordEditText = findViewById<EditText>(R.id.et_register_confirm_password)
        val registerButton = findViewById<Button>(R.id.btn_register)

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")

        registerButton.setOnClickListener {
            val firstName = firstNameEditText.text.toString().trim()
            val lastName = lastNameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty()
            ) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                // Register user with Firebase
                registerUser(firstName, lastName, email, phone, password)
            }
        }
    }

    private fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        password: String
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Firebase Authentication successful
                    val user = auth.currentUser

                    user?.let {
                        // Update the user's profile with the display name
                        val profileUpdates = userProfileChangeRequest {
                            displayName = "$firstName $lastName"
                        }
                        it.updateProfile(profileUpdates)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    Toast.makeText(
                                        this,
                                        "Registration successful!",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // Navigate to LoginActivity
                                    val intent = Intent(this, LoginActivity::class.java)
                                    intent.putExtra("email", email) // Pass the email to autofill
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Failed to update profile: ${updateTask.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                } else {
                    // Handle registration failure
                    Toast.makeText(
                        this,
                        "Registration failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    // User data model
    data class User(
        val firstName: String = "",
        val lastName: String = "",
        val email: String = "",
        val phone: String = ""
    )
}
