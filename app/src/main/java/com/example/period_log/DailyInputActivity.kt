package com.example.period_log

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class DailyInputActivity : AppCompatActivity() {

    lateinit var logDate: TextView
    lateinit var startDate: EditText
    lateinit var endDate: EditText
    lateinit var crampsSeekBar: SeekBar
    lateinit var acneSeekBar: SeekBar
    lateinit var headacheSeekBar: SeekBar
    lateinit var fatigueSeekBar: SeekBar
    lateinit var btnSave: Button
//    lateinit var dateString: String
    lateinit var datePicker: DatePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daily_input_layout)

        logDate = findViewById(R.id.textDate)
        logDate.setText(Calendar.mYEdited.substring(4, 10))
        crampsSeekBar = findViewById(R.id.crampsBar)
        acneSeekBar = findViewById(R.id.acneBar)
        headacheSeekBar = findViewById(R.id.headacheBar)
        fatigueSeekBar = findViewById(R.id.fatigueBar)
        btnSave = findViewById(R.id.btnSaveDate)
        startDate =findViewById(R.id.etStartDate)
        endDate =findViewById(R.id.etEndDate)


        var crampValue = 0
        var acneValue = 0
        var headacheValue = 0
        var fatigueValue = 0

        crampsSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                Toast.makeText(this@DailyInputActivity, "cramp seek bar $progress", Toast.LENGTH_SHORT).show()
                crampValue = progress
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                //Toast.makeText(this@DailyInputActivity, "start", Toast.LENGTH_SHORT).show()
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                Toast.makeText(this@DailyInputActivity, "cramps level: $crampValue", Toast.LENGTH_SHORT).show()
            }
        })

        acneSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                Toast.makeText(this@DailyInputActivity, "cramp seek bar $progress", Toast.LENGTH_SHORT).show()
                acneValue = progress
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                //Toast.makeText(this@DailyInputActivity, "start", Toast.LENGTH_SHORT).show()
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                Toast.makeText(this@DailyInputActivity, "acne level: $acneValue", Toast.LENGTH_SHORT).show()
            }
        })

        headacheSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                Toast.makeText(this@DailyInputActivity, "cramp seek bar $progress", Toast.LENGTH_SHORT).show()
                headacheValue = progress
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                //Toast.makeText(this@DailyInputActivity, "start", Toast.LENGTH_SHORT).show()
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                Toast.makeText(this@DailyInputActivity, "headache level: $headacheValue", Toast.LENGTH_SHORT).show()
            }
        })

        fatigueSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                Toast.makeText(this@DailyInputActivity, "cramp seek bar $progress", Toast.LENGTH_SHORT).show()
                fatigueValue = progress
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                //Toast.makeText(this@DailyInputActivity, "start", Toast.LENGTH_SHORT).show()
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                Toast.makeText(this@DailyInputActivity, "fatigue level: $fatigueValue", Toast.LENGTH_SHORT).show()
            }
        })

        btnSave.setOnClickListener {
            //if symptoms all 0, ask if user wants to save empty symptoms?
            //save button saves date, symptoms values to parse

        }
    }
    //TODO: Change the startData and endData to ToggleButton
    //TODO: Might need to have find a way to persist the turn on of the Toggle Button

    //TODO: write saveStartDate -> post a new object with endDate - 1 to the server
    private fun saveStartDate(startDate: Long) {
        val cycle = Cycle()
        cycle.setUser(ParseUser.getCurrentUser())
        cycle.setStartedAt(startDate)
        cycle.setEndedAt(-1)
        cycle.saveInBackground() { e ->
            if (e != null) {
                //Something has went wrong
                Log.e(TAG, "Error while saving cycle")
                e.printStackTrace()
                Toast.makeText(this, "Error saving startedAt of cycle", Toast.LENGTH_SHORT).show()
            }
            else {
                Log.i(TAG, "Successfully saved startedAt of cycle")
                Toast.makeText(this, "startedAt of cycle has been saved!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //TODO: deleteStartDate the function is called when the startDate toggle button turnOff. It delete the cycle object from the ParseServer
    //TODO: deleteEndDate the function is called when the endDate toggle button turnOff. It sets the EndedDate back to -1

    //TODO: write saveEndDate -> look for the object with endDate -1 and update the endDate
    //The function return false to prevent the user to toggle endDate before input the startDate otherwise return true
    private fun saveEndData(user: ParseUser, endDate: Long) : Boolean {
        var successUpdate = false
        //Get all the objects from our ParseServer Cycle class
        val query : ParseQuery<Cycle> = ParseQuery.getQuery(Cycle::class.java)
        //This line is added because user is pointer
        query.include(Cycle.KEY_USER)
        //Find the currentUser object where the endedAt equal to -1
        query.whereEqualTo(Cycle.KEY_USER, user)
        query.whereEqualTo(Cycle.KEY_ENDED_AT, -1)

        //2. Get objectId of that cycle
        query.findInBackground(object: FindCallback<Cycle>{
            override fun done(cycle: MutableList<Cycle>?, e: ParseException?) {
                if (e != null) {
                    //Something went wrong
                    Log.e(TAG, "Error fetching cycle")
                } else {
                    if (cycle != null && cycle.size == 1) {
                        Log.i(TAG, "The successfully fetch only one cycle with end")
                        val currCycle = cycle[0]
                        Log.i(
                            TAG,
                            "Successfully fetch the cycle with endedAt:  ${
                                currCycle.getEndedAt().toString()
                            }"
                        )
                        //3. Update the endDate using its objectId
                        query.getInBackground(currCycle.objectId) { currCycle, e ->
                            if (e == null) {
                                //Update the endedAt and saveInBackground to update the Parse Server
                                currCycle.put(Cycle.KEY_ENDED_AT, endDate)
                                Log.i(TAG, "Successfully updating endedAt")
                                Toast.makeText(this@DailyInputActivity,"End Date is updated.", Toast.LENGTH_SHORT).show()
                                successUpdate = true
                            }
                            else
                            {
                                Log.e(TAG,"There is an error updating endedAt")
                                Toast.makeText(this@DailyInputActivity, "Input startDate before endDate",Toast.LENGTH_SHORT).show()
                                //Prevent the toggle button to turn on because successUpdate is still false
                            }

                        }
                    }
                }
            }
        })
        return successUpdate
    }

    companion object {
        val TAG = "DailyInputActivity"
    }
}