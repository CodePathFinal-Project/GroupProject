package com.example.period_log

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.parse.ParseObject
import com.parse.ParseUser


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_layout)

//        //         Testing the connection
//        val firstObject = ParseObject("FirstClass")
//        firstObject.put("message","Hey ! First message from android. Parse is now connected")
//        firstObject.saveInBackground {
//            if (it != null){
//                it.localizedMessage?.let { message -> Log.e("MainActivity", message) }
//            }else{
//                Log.d("MainActivity","Object saved.")
//            }
//        }

        // Check if there is a user logged in.
        // If there is, take them to Calendar
        if (ParseUser.getCurrentUser() != null) {
            val intent = Intent(this@MainActivity, Calendar::class.java)
            startActivity(intent)
            finish()
        }

        val getStartedButton = findViewById<Button>(R.id.getStartedBtn)
        getStartedButton.setOnClickListener{
            val intent = Intent(this, GetStartedActivity::class.java)
            startActivity(intent)
        }

        val signInButton = findViewById<Button>(R.id.signInBtn)
        signInButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //TODO if the user is logged in, switch to calendar view
        val tempCalendarBtn = findViewById<Button>(R.id.buttonForCalendarView)
        tempCalendarBtn.setOnClickListener{
            val intent = Intent(this, Calendar::class.java)
            startActivity(intent)
        }
    }
}