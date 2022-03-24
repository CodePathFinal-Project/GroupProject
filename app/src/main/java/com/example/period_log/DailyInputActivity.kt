package com.example.period_log

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser
import com.parse.ParseObject

import com.parse.GetCallback
import java.util.*


class DailyInputActivity : AppCompatActivity() {

    lateinit var logDate: TextView
//    lateinit var startDate: EditText
//    lateinit var endDate: EditText
    lateinit var crampsSeekBar: SeekBar
    lateinit var acneSeekBar: SeekBar
    lateinit var headacheSeekBar: SeekBar
    lateinit var fatigueSeekBar: SeekBar
    lateinit var btnSave: Button
    lateinit var startDateSwitch : Switch
    lateinit var endDateSwitch : Switch


    var crampValue = 0
    var acneValue = 0
    var headacheValue = 0
    var fatigueValue = 0

    lateinit var cycle : Cycle



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daily_input_layout)

        logDate = findViewById(R.id.textDate)
        logDate.setText(CalendarActivity.mYEdited.substring(4, 10))

        crampsSeekBar = findViewById(R.id.crampsBar)
        acneSeekBar = findViewById(R.id.acneBar)
        headacheSeekBar = findViewById(R.id.headacheBar)
        fatigueSeekBar = findViewById(R.id.fatigueBar)

        btnSave = findViewById(R.id.btnSaveDate)

        startDateSwitch = findViewById(R.id.switch1)
        endDateSwitch = findViewById(R.id.switch2)

        val user = ParseUser.getCurrentUser()
        fetchDailyInput(user, CalendarActivity.currentDate)

        crampsSeekBar.progress = crampValue
        acneSeekBar.progress = acneValue
        headacheSeekBar.progress = headacheValue
        fatigueSeekBar.progress = fatigueValue

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

        //save daily input and update daily input functions (no duplicate date values)
        //fetch daily input values and
        //TODO: show it in the seekbar
        //TODO: Add daily input as events and show it in the calendar view
        //TODO: Toggle button check cant be true for both start & end date
        //TODO: fetch the start date and end date and apply to switch toggles
        //TODO: if the daily input date is in between cycle, prevent both toggle buttons to be on w/ error message


        btnSave.setOnClickListener {
            //if symptoms all 0, ask if user wants to save empty symptoms?
            //save button saves date, symptoms values to parse
            //if-else
            val query : ParseQuery<DailyInput> = ParseQuery.getQuery(DailyInput::class.java)
            query.include(DailyInput.KEY_USER)
            query.whereEqualTo(DailyInput.KEY_USER, user)
            query.whereEqualTo(DailyInput.KEY_DATE, CalendarActivity.currentDate)
            query.findInBackground(object: FindCallback<DailyInput>{
                override fun done(dailyInput: MutableList<DailyInput>?, e: ParseException?) {
                    if(e != null)
                    {
                        Log.e(TAG, "Error fetching daily input")
                    }else{
                        if(dailyInput != null){
                            if ( dailyInput.size == 0){
                                saveDailyInput(user, acneValue, crampValue, fatigueValue, headacheValue, CalendarActivity.currentDate)
                            } else{
                                val currentDailyInputObjectId = dailyInput[0].objectId
                                updateDailyInput(query, currentDailyInputObjectId, acneValue, crampValue, fatigueValue, headacheValue, CalendarActivity.currentDate)
                            }
                    }}
                }
            })
        }
    }
    //TODO: Change the startData and endData to ToggleButton
    //TODO: Might need to have find a way to persist the turn on of the Toggle Button

    //TODO: write saveStartDate -> post a new object with endDate - 1 to the server
    //return true if there is no object with endDate -1 then create a cycle with endDate -1
    //otherwise return false
    private fun saveStartDate(user: ParseUser, startDate: Long) : Boolean {
        val successSave = false

        //Get all the objects from our ParseServer Cycle class
        val query : ParseQuery<Cycle> = ParseQuery.getQuery(Cycle::class.java)
        //This line is added because user is pointer
        query.include(Cycle.KEY_USER)
        //Find the currentUser object where the endedAt equal to -1
        query.whereEqualTo(Cycle.KEY_USER, user)
        query.whereEqualTo(Cycle.KEY_ENDED_AT, -1)

        //Get the object
        query.findInBackground(object: FindCallback<Cycle> {
            override fun done(cycle: MutableList<Cycle>?, e: ParseException?) {
                if (e != null) {
                    //Something went wrong
                    Log.e(TAG, "Error fetching cycle")
                } else {
                    if (cycle != null && cycle.size == 0) {
                        Log.i(TAG, "The successfully confirmed that there is no cycle with endedAt -1")
                        val cycle = Cycle()
                        cycle.setUser(ParseUser.getCurrentUser())
                        cycle.setStartedAt(startDate)
                        cycle.setEndedAt(-1)
                        cycle.saveInBackground() { e ->
                            if (e != null) {
                                //Something has went wrong
                                Log.e(TAG, "Error while saving cycle")
                                e.printStackTrace()
                                Toast.makeText(this@DailyInputActivity, "Error saving startedAt of cycle", Toast.LENGTH_SHORT).show()
                            }
                            else {
                                Log.i(TAG, "Successfully saved startedAt of cycle")
                                Toast.makeText(this@DailyInputActivity, "startedAt of cycle has been saved!", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                    else {
                        Log.e(TAG, "There is a cycle without startDate")
                        Toast.makeText(this@DailyInputActivity, "Please input the endDate for the previous cycle before input a new cycle", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
        return successSave
    }

    //TODO: deleteStartDate the function is called when the startDate toggle button turnOff. It delete the cycle object from the ParseServer
    private fun deleteStartDate(user: ParseUser, startDate: Long) {
        //Get all the objects from our ParseServer Cycle class
        val query : ParseQuery<Cycle> = ParseQuery.getQuery(Cycle::class.java)
        //This line is added because user is pointer
        query.include(Cycle.KEY_USER)
        //Find the currentUser object where the endedAt equal to -1
        query.whereEqualTo(Cycle.KEY_USER, user)
        query.whereEqualTo(Cycle.KEY_STARTED_AT, startDate)

        query.getFirstInBackground(object : GetCallback<Cycle> {
            override fun done(cycle: Cycle, e: ParseException?) {
                if (e != null) {
                    //Something went wrong
                    Log.e(TAG, "Error fetching the cycle")
                } else {
                    cycle.delete()
                    cycle.saveInBackground()
                    Log.i(TAG, "Delete the cycle successfully!")
                }
            }
        })
    }

    //TODO: deleteEndDate the function is called when the endDate toggle button turnOff. It sets the EndedDate back to -1
    private fun deleteEndDate(user: ParseUser, endDate: Long) {
        //Get all the objects from our ParseServer Cycle class
        val query : ParseQuery<Cycle> = ParseQuery.getQuery(Cycle::class.java)
        //This line is added because user is pointer
        query.include(Cycle.KEY_USER)
        //Find the currentUser object where the endedAt equal to -1
        query.whereEqualTo(Cycle.KEY_USER, user)
        query.whereEqualTo(Cycle.KEY_ENDED_AT, endDate)
        //Retrieve the objectId by userId
        query.findInBackground(object: FindCallback<Cycle>{
            override fun done(cycles: MutableList<Cycle>?, e: ParseException?) {
                if (e != null) {
                    //Something went wrong
                    Log.e(QuestionnaireActivity.TAG, "Error fetching userPeriodCycleLength")
                } else {
                    if (cycles != null) {
                        //We know that it will return a list with only one user in it
                        if (cycles.size == 1) {
                            cycle = cycles[0]
                            //We know that it will return a list with only one user in it
                            Log.i(
                                QuestionnaireActivity.TAG,
                                "The successfully fetch has only one user ${cycle.objectId}"
                            )
                            // Retrieve the object by id
                            query.getInBackground(cycle.objectId) { cycle, e ->
                                if (e == null) {
                                    // Update it periodLength and cycleLength and will get sent to your Parse Server.
                                    cycle.put(Cycle.KEY_ENDED_AT, -1)
                                    cycle.saveInBackground()
                                    Log.i(
                                        QuestionnaireActivity.TAG,
                                        "Successfully updating endDate"
                                    )
                                    Toast.makeText(
                                        this@DailyInputActivity,
                                        "End date has been updated!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Log.e(
                                        QuestionnaireActivity.TAG,
                                        "There is an error updating endDate"
                                    )
                                    Toast.makeText(
                                        this@DailyInputActivity,
                                        "Error updating end date",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    //TODO: write saveEndDate -> look for the object with endDate -1 and update the endDate
    // return true if it is successfully update, otherwise return false
    private fun saveEndDate(user: ParseUser, endDate: Long) : Boolean {
        var successSave = false
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
                                successSave = true
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
        return successSave
    }


    private fun saveDailyInput(user: ParseUser, acne: Int, cramp: Int, fatigue: Int, headache: Int, date: Long) {

        val dailyInput = DailyInput()

        dailyInput.setUser(user)
        dailyInput.setAcne(acne)
        dailyInput.setCramp(cramp)
        dailyInput.setFatigue(fatigue)
        dailyInput.setHeadache(headache)
        dailyInput.setDate(date)
        dailyInput.saveInBackground{ exception->
            if (exception != null){
                exception.printStackTrace()
                Toast.makeText(this, "Error saving daily input object.", Toast.LENGTH_SHORT).show()
            }else{
                Log.d(TAG,"daily input values saved.")
                gotoCalendarActivity()
            }
        }
//        CalendarActivity.allCycles.
    }

    private fun updateDailyInput(query: ParseQuery<DailyInput>, objectId: String, acne: Int, cramp: Int, fatigue: Int, headache: Int, date: Long){
        query.getInBackground(objectId){ currentDailyInput, e ->
            if (e != null){
                e.printStackTrace()
                Toast.makeText(this, "Error updating daily input object.", Toast.LENGTH_SHORT).show()
            }else{
                currentDailyInput.put(DailyInput.KEY_ACNE, acne)
                currentDailyInput.put(DailyInput.KEY_CRAMP, cramp)
                currentDailyInput.put(DailyInput.KEY_FATIGUE, fatigue)
                currentDailyInput.put(DailyInput.KEY_HEADACHE, headache)
                currentDailyInput.put(DailyInput.KEY_DATE, date)
                currentDailyInput.saveInBackground()
                Toast.makeText(this, "Successfully update daily input", Toast.LENGTH_SHORT).show()
                gotoCalendarActivity()
            }
        }
    }

    private fun fetchDailyInput(user: ParseUser, date: Long) {
        val query : ParseQuery<DailyInput> = ParseQuery.getQuery(DailyInput::class.java)
        query.include(DailyInput.KEY_USER)
        query.whereEqualTo(DailyInput.KEY_USER, user)
        query.whereEqualTo(DailyInput.KEY_DATE, date)
        query.findInBackground(object: FindCallback<DailyInput>{
            override fun done(dailyInputs: MutableList<DailyInput>?, e: ParseException?) {
                if(e != null)
                {
                    Log.e(TAG, "No Daily Input")
                }else{
                    if(dailyInputs != null && dailyInputs.size == 1){
                        val currentDailyInput = dailyInputs[0]
                        acneValue = currentDailyInput.getAcne()
                        crampValue = currentDailyInput.getCramp()
                        headacheValue = currentDailyInput.getHeadache()
                        fatigueValue = currentDailyInput.getFatigue()
                        Toast.makeText(this@DailyInputActivity, "$acneValue $crampValue $headacheValue $fatigueValue", Toast.LENGTH_SHORT).show()
                    }}
            }
        })
    }

    private fun gotoCalendarActivity() {
        val intent = Intent(this, CalendarActivity::class.java)
        startActivity(intent)
    }

    companion object {
        val TAG = "DailyInputActivity"
    }
}