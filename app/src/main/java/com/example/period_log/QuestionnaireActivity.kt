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


class QuestionnaireActivity : AppCompatActivity() {

    lateinit var currUserPeriodAndCycleLength : ParseObject
    var allUsers: MutableList<UserPeriodAndCycleLength> = mutableListOf()

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

    private  fun savePeriodAndCycleLength(user: ParseUser, periodLength : Int?, cycleLength : Int?) {
        //Get all objects in our back4app UserPeriodAndCycleLength
        val query: ParseQuery<UserPeriodAndCycleLength> = ParseQuery.getQuery<UserPeriodAndCycleLength>(UserPeriodAndCycleLength::class.java)
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
                            allUsers.addAll(usersPeriodAndCycleLength)
                            Log.i(TAG, "The fetch has only one user")
                        }

                    }
                }


            }
        })
//        var userPeriodAndCycle: List<UserPeriodAndCycleLength> = query.find()
//        val objectId = userPeriodAndCycle[0].objectId
//        Log.i(TAG, "Current user objectId $objectId")
//        query.getInBackground(objectId
//        ) { userPeriodAndCycleLength, e ->
//            if (e == null) {
//                // Update it new data
//                userPeriodAndCycleLength.put(UserPeriodAndCycleLength.KEY_PERIOD_LENGTH, periodLength)
//                userPeriodAndCycleLength.put(UserPeriodAndCycleLength.KEY_CYCLE_LENGTH, cycleLength)
//                userPeriodAndCycleLength.saveInBackground()
//                Log.i(TAG, "Period and cycle length has been updated!")
//            } else {
//                Log.i(TAG, "The period length and cycle length did not get updated!")
//            }
//        }
    }

    companion object {
        const val TAG = "QuestionnaireActivity"
    }
}