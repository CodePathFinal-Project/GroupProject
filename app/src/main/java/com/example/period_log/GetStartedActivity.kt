package com.example.period_log


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseObject
import com.parse.ParseUser

class GetStartedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.get_started_layout)

        findViewById<Button>(R.id.signUpBtn).setOnClickListener{
            val username = findViewById<EditText>(R.id.usernameSignUpEt).text.toString()
            val password = findViewById<EditText>(R.id.passwordSignUpEt).text.toString()
            signUpUser(username,password)
        }
    }

    private fun signUpUser(username:String, password: String) {
        val user = ParseUser()

        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground{ e ->
            if (e == null) {
                Log.i(TAG, "Successfully signed up user")
                Toast.makeText(this, "Successfully signing up", Toast.LENGTH_SHORT).show()
                Log.i(TAG, user.objectId)
                signUpUserPeriodAndCycleLength(user)
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Error signing up", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUpUserPeriodAndCycleLength(user : ParseUser) {
        val userPeriodAndCycleLength = UserPeriodAndCycleLength()
        userPeriodAndCycleLength.setUser(user)
        userPeriodAndCycleLength.saveInBackground {
            if (it != null){
                it.localizedMessage?.let { message -> Log.e(TAG, message) }
                Toast.makeText(this, "Error saving userPeriodAndCycle object.", Toast.LENGTH_SHORT).show()
            }else{
                Log.d(TAG,"userPeriodAndCycleLength object saved.")
                goToQuestionnaire()
            }
        }
    }

    private fun goToQuestionnaire() {
        val intent = Intent(this@GetStartedActivity, QuestionnaireActivity::class.java)
        startActivity(intent)
    }

    companion object {
        val TAG = "GetStartedActivity"
    }
}