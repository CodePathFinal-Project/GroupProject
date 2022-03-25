package com.example.period_log

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        findViewById<Button>(R.id.logInBtn).setOnClickListener{
            val username = findViewById<EditText>(R.id.usernameEt).text.toString()
            val password = findViewById<EditText>(R.id.passwordEt).text.toString()
            loginUser(username,password)
        }
    }

    private fun loginUser(username: String, password: String) {
        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (user != null) {
                Log.i(TAG, "successfully logged in user")
                goToCalendar()
            } else {
                e.printStackTrace()
                Log.i(TAG, "error logging in user")
                Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT).show()
            }})
        )
    }

    private fun goToCalendar() {
        val intent = Intent(this@LoginActivity, CalendarActivity::class.java)
        startActivity(intent)
        finish()
    }


    companion object {
        val TAG = "LoginActivity"
    }

}