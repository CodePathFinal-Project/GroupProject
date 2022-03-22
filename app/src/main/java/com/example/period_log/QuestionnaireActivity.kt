package com.example.period_log

import android.content.Intent
import android.os.Bundle
import android.os.UserHandle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.*
import com.parse.GetCallback
import com.parse.ParseObject





class QuestionnaireActivity : AppCompatActivity() {

    lateinit var currUserPeriodAndCycleLength : UserPeriodAndCycleLength

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.questionnaire_layout)

        val saveBtn = findViewById<Button>(R.id.saveBtn)
        val periodLengthEt = findViewById<EditText>(R.id.periodLengthEt)
        val cycleLengthEt = findViewById<EditText>(R.id.cycleLengthEt)

        saveBtn.setOnClickListener {
            val user = ParseUser.getCurrentUser()
            Toast.makeText(this,"Curr userId: ${user.objectId}", Toast.LENGTH_SHORT).show()
            //Get periodLength and cycleLength from the EditText
            val periodLength = periodLengthEt.text.toString().toInt()
            val cycleLength = cycleLengthEt.text.toString().toInt()
            savePeriodAndCycleLength(user, periodLength, cycleLength)
        }
    }

    private  fun savePeriodAndCycleLength(user: ParseUser, periodLength : Int, cycleLength : Int) {
        //Get all objects in our back4app UserPeriodAndCycleLength
        val query : ParseQuery<UserPeriodAndCycleLength> = ParseQuery.getQuery(UserPeriodAndCycleLength::class.java)
        //This line is added because user is a pointer
        query.include(UserPeriodAndCycleLength.KEY_USER)
//        //Only return the periodAndCycleLength from the current user
        query.whereEqualTo(UserPeriodAndCycleLength.KEY_USER, user)
        //Retrieve the objectId by userId
        query.findInBackground(object: FindCallback<UserPeriodAndCycleLength> {
            override fun done(usersPeriodAndCycleLength : MutableList<UserPeriodAndCycleLength>?, e: ParseException?) {
                if (e != null) {
                    //Something went wrong
                    Log.e(TAG, "Error fetching userPeriodCycleLength")
                } else {
                    if (usersPeriodAndCycleLength != null) {
                        //We know that it will return a list with only one user in it
                        if (usersPeriodAndCycleLength.size == 1)
                        {
                            currUserPeriodAndCycleLength = usersPeriodAndCycleLength[0]
                            Log.i(TAG, "The successfully fetch has only one user ${currUserPeriodAndCycleLength.objectId}")
                            // Retrieve the object by id
                            query.getInBackground(currUserPeriodAndCycleLength.objectId) { currUserPeriodAndCycleLength, e ->
                                if (e == null) {
                                    // Update it periodLength and cycleLength and will get sent to your Parse Server.
                                    currUserPeriodAndCycleLength.put(UserPeriodAndCycleLength.KEY_PERIOD_LENGTH, periodLength)
                                    currUserPeriodAndCycleLength.put(UserPeriodAndCycleLength.KEY_CYCLE_LENGTH, cycleLength)
                                    currUserPeriodAndCycleLength.saveInBackground()
                                    Log.i(TAG, "Successfully updating periodLength and cycleLength")
                                    Toast.makeText(this@QuestionnaireActivity, "Period and cycle length are updated", Toast.LENGTH_SHORT).show()
                                }
                                else {
                                    Log.e(TAG, "There is an error updating periodLength and cycleLength")
                                    Toast.makeText(this@QuestionnaireActivity, "Error updating period and cycle length", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    companion object {
        const val TAG = "QuestionnaireActivity"
    }
}