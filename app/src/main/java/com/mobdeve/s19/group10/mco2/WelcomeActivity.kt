// WelcomeActivity.kt
package com.mobdeve.s19.group10.mco2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome)

        val getStartedButton = findViewById<Button>(R.id.startButton)

        getStartedButton.setOnClickListener {

            val intent = Intent(this, RegisterLoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
