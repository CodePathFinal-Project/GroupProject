package com.example.period_log

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
//import com.parse.ParseObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        //        Testing the connection
//        val firstObject = ParseObject("FirstClass")
//        firstObject.put("message","Hey ! First message from android. Parse is now connected")
//        firstObject.saveInBackground {
//            if (it != null){
//                it.localizedMessage?.let { message -> Log.e("MainActivity", message) }
//            }else{
//                Log.d("MainActivity","Object saved.")
//            }
//        }
        val getStartedButton = findViewById<Button>(R.id.getStartedBtn)
        getStartedButton.setOnClickListener{
            val intent = Intent(this, GetStarted::class.java)
            startActivity(intent)
        }

        val signInButton = findViewById<Button>(R.id.signInBtn)
        signInButton.setOnClickListener{
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
    }
}