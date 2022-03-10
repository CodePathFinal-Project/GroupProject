package com.example.period_log

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.*
import com.parse.GetCallback


class QuestionnaireActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.questionnaire_layout)

        val saveBtn = findViewById<Button>(R.id.saveBtn)
        val periodLengthEt = findViewById<EditText>(R.id.periodLengthEt)
        val cycleLengthEt = findViewById<EditText>(R.id.cycleLengthEt)

        saveBtn.setOnClickListener {
            val user = ParseUser.getCurrentUser()
            val userId = user.objectId.toString()

            //Get periodLength and cycleLength from the EditText
            val periodLength = periodLengthEt.text.toString().toInt()
            val cycleLength = cycleLengthEt.text.toString().toInt()

            Log.i(TAG, "Save Button has been clicked $userId")

            //savePeriodAndCycleLength(userId, periodLength, cycleLength)
        }
    }

//    private  fun savePeriodAndCycleLength(userId: String, periodLength : Int, cycleLength : Int) {
//        //Get all objects in our back4app UserPeriodAndCycleLength
//        val query: ParseQuery<UserPeriodAndCycleLength> = ParseQuery.getQuery<UserPeriodAndCycleLength>(UserPeriodAndCycleLength::class.java)
//        //Retrieve the object by id
//        query.getInBackground(userId, object : GetCallback<UserPeriodAndCycleLength> {
//            override fun done(userPeriodAndCycleLength : UserPeriodAndCycleLength, e: ParseException?) {
//                if (e == null) {
//                    // Update it new data
//                    userPeriodAndCycleLength.put(UserPeriodAndCycleLength.KEY_PERIOD_LENGTH, periodLength)
//                    userPeriodAndCycleLength.put(UserPeriodAndCycleLength.KEY_CYCLE_LENGTH, cycleLength)
//                    userPeriodAndCycleLength.saveInBackground()
//                    Log.i(TAG, "Period and cycle length has been updated!")
//                } else {
//                    Log.i(TAG, "The period length and cycle length did not get updated!")
//                }
//            }
//        })
//    }

    companion object {
        const val TAG = "QuestionnaireActivity"
    }
}