package com.example.period_log

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.github.sundeepk.compactcalendarview.domain.Event
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser
import com.parse.ParseObject

import com.parse.GetCallback
import java.util.*
import android.widget.CompoundButton

import android.widget.Switch





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
    var startDateOn: Boolean = false
    var endDateOn: Boolean = false


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
        //show it in the seekbar - it's inside the fetchDailyInput function
        //TODO: Add daily input as events and show it in the calendar view
        //TODO: Toggle button check cant be true for both start & end date
        //TODO: fetch the start date and end date and apply to switch toggles
        //TODO: if the daily input date is in between cycle, prevent both toggle buttons to be on w/ error message

        startDateSwitch.setOnCheckedChangeListener{ buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Start On.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Start Off.", Toast.LENGTH_SHORT).show()
            }
            startDateOn = isChecked
        }

        endDateSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "End On.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "End Off.", Toast.LENGTH_SHORT).show()
            }
            endDateOn = isChecked
        }

        btnSave.setOnClickListener {

            if (startDateOn && endDateOn){
                Toast.makeText(this, "You cannot have both Start and End cycle on the same day.", Toast.LENGTH_SHORT).show()
            }
            else{
                if (startDateOn) {
                    saveStartDate(user, CalendarActivity.currentDate)
                }
                else if (endDateOn) {
                    saveEndDate(user, CalendarActivity.currentDate)
                }

                //Searching for dailyInput in the server
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
    }

    //saveStartDate add (startDate, - 1) in cyclesInPair and post a cycle object to the server
    private fun saveStartDate(user: ParseUser, startDate: Long){
        //add the (startDate, - 1) into cyclesInPair
        val pair = Pair(startDate, (-1).toLong())
        CalendarActivity.cyclesInPair.add(pair)

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

    //saveEndDate update the endDate in server and cyclesInPair
    private fun saveEndDate(user: ParseUser, endDate: Long) {
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
                            "Successfully fetch the cycle with endedAt:${currCycle.getEndedAt()}"
                        )
                        //TODO: change the (startDate, - 1) in cycleInPair to (startDate, endDate)
                        var pair = Pair(currCycle.getStartedAt(),(-1).toLong())
                        CalendarActivity.cyclesInPair.remove(pair)
                        pair = Pair(currCycle.getStartedAt(), endDate)
                        CalendarActivity.cyclesInPair.add(pair)

                        //3. Update the endDate using its objectId
                        query.getInBackground(currCycle.objectId) { currCycle, e ->
                            if (e == null) {
                                //Update the endedAt and saveInBackground to update the Parse Server
                                currCycle.put(Cycle.KEY_ENDED_AT, endDate)
                                currCycle.saveInBackground()
                                Log.i(TAG, "Successfully updating endedAt")
                                Toast.makeText(this@DailyInputActivity,"End Date is updated.", Toast.LENGTH_SHORT).show()
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
        val ev = Event(Color.YELLOW, date)
        CalendarActivity.compactCalendarView.addEvent(ev)

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
                        crampsSeekBar.setProgress(crampValue)
                        acneSeekBar.setProgress(acneValue)
                        headacheSeekBar.setProgress(headacheValue)
                        fatigueSeekBar.setProgress(fatigueValue)
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