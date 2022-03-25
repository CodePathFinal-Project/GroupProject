package com.example.period_log

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser
import java.util.*
import java.util.Calendar
import kotlin.collections.ArrayList

// TODO: Write fetchCycle to fetch all the cycle when the refresh button is clicked
// TODO: Show all the period cycle on the calendar view
// TODO: Add expected cycle dates in to event.
// TODO: Add refresh button that clears all events and re-fetch
// TODO: Save button posts new values to the server.

class CalendarActivity : AppCompatActivity() {

    lateinit var mmmmYYYY : TextView
    lateinit var btnSettings: ImageButton
    lateinit var btnRefresh : ImageButton
    lateinit var btnDailyInput: Button
//    lateinit var compactCalendarView: CompactCalendarView
//    lateinit var today : Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "$startedAt $endedAt $fetched")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        compactCalendarView = findViewById(R.id.compactCalendar_view)
        if (!fetched){
            fetchCycles()
            fetchDailyInputDate()
            Log.i(TAG, "$cyclesInPair")
            Log.i(TAG, "very first time opening calendar")
            fetched = true
        }
        else{
            addCycleEvents()
            addEventsDailyInput()
        }
        Log.i(TAG, "$cyclesInPair")

        mYEdited = Calendar.getInstance().time.toString()
        currentDate = computeMidnight(Calendar.getInstance().timeInMillis)
        mmmmYYYY = findViewById(R.id.tvMonth)
        mmmmYYYY.setText(mYEdited.substring(4,8) + Calendar.getInstance().time.toString().substring(24,28))
        //REFRESH BUTTON
        //TODO: refresh button where its on clickListener call fetchCycles
        btnRefresh = findViewById<ImageButton>(R.id.Refreshbutton)
        btnRefresh.setOnClickListener {
            Log.i(TAG, "Refresh button")
            fetchCycles()
        }

        //SETTINGS BUTTON
        btnSettings = findViewById<ImageButton>(R.id.cvSettings)
        btnSettings.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        btnDailyInput = findViewById(R.id.btnViewDailyInput)
        btnDailyInput.setOnClickListener {
            gotoDailyInputActivity()
        }

        // define a listener to receive callbacks when certain events happen.
        compactCalendarView.setListener(object : CompactCalendarView.CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
                val events: List<Event> = compactCalendarView.getEvents(dateClicked)
                currentDate = dateClicked.time
                Log.i(TAG, "Day was clicked: $dateClicked with $events")
                mYEdited = dateClicked.toString()
                Toast.makeText(this@CalendarActivity, "$currentDate", Toast.LENGTH_SHORT).show()
                //showPopup(compactCalendarView)
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                currentDate = firstDayOfNewMonth.time //returns milliseconds
                Log.i(TAG, "Month was scrolled to: $firstDayOfNewMonth and $currentDate")
                //milliseconds to Date class
                //Toast.makeText(this@CalendarActivity, "$firstDayOfNewMonth", Toast.LENGTH_SHORT).show()
                mYEdited = firstDayOfNewMonth.toString()
                mmmmYYYY.setText(mYEdited.substring(4,8) + mYEdited.substring(24,28))

            }

        })
    }

    fun addCycleEvents() {
        for ((start, end) in cyclesInPair){
            if (end == (-1).toLong()) {
                Log.i(TAG, "populating ongoing cycle")
                //TODO: now without endDate we default to 5 days
                for (i in 0..4) {
                    var day = start + (i * DAYINMILLISEC)
                    val ev = Event(Color.BLUE, day)
                    compactCalendarView.addEvent(ev)
                }
            }else {
                for(day in start..(end+1) step DAYINMILLISEC) {
                    Log.i(TAG, "populating events on calendar")
                    val ev = Event(Color.MAGENTA, day)
                    compactCalendarView.addEvent(ev)
                }
            }
        }
    }
//            if(end > 0){
//                for(day in start..(end+1) step DAYINMILLISEC){
//                    Log.i(TAG, "populating events on calendar")
//                    val ev = Event(Color.MAGENTA, day)
//                    compactCalendarView.addEvent(ev)
//                }
//            }else{
//                for(day in start..(currentDate + 1) step DAYINMILLISEC){
//                    Log.i(TAG, "populating ongoing cycle")
//                    val ev = Event(Color.CYAN, day)
//                    compactCalendarView.addEvent(ev)
//
//            }
//        }
//    }

    fun fetchCycles() {
        //Specify the class query
        val query : ParseQuery<Cycle> = ParseQuery.getQuery(Cycle::class.java)
        //Find all objects
        //This is line is added because user is a pointer
        query.include(Cycle.KEY_USER)
        //Only return cycles from currently signed in user
        query.whereEqualTo(Cycle.KEY_USER, ParseUser.getCurrentUser())
        query.addDescendingOrder(Cycle.KEY_STARTED_AT)

        //Only return the most recent 5 cycles
        query.setLimit(5)

        query.findInBackground (object : FindCallback<Cycle> {
                override fun done(cycles: MutableList<Cycle>?, e: ParseException?) {
                    if (e != null) {
                        //Something went wrong
                        Log.e(TAG, "Error fetching posts")
                    } else {
                        if (cycles != null) {
                            for (cycle in cycles) {
                                Log.i(
                                    TAG, "Cycle startedAt: " + cycle.getStartedAt().toString()
                                            + ", endedAt: " + cycle.getEndedAt().toString()
                                            + ", username:" + cycle.getUser()?.username
                                )
                            }
                            allCycles.clear()
                            var cycleSize = allCycles.size
                            compactCalendarView.removeAllEvents()
                            Log.i(TAG, "Cycles are cleared $cycleSize and events are cleared")
                            allCycles.addAll(cycles)
                            cyclesInPair.clear()

                            //TODO: notifyChanged to rerender the calendarView
                            //TODO: decide on end date measure? last day of period or the day after?
                            if (allCycles.size !=  0) {
                                Log.i(TAG, "allCycles is not empty!!")
                                for (cycle in allCycles) {
                                    val pair = Pair(cycle.getStartedAt(), cycle.getEndedAt())
                                    cyclesInPair.add(pair)
                                    Log.i(TAG, "$cyclesInPair")
//                                    startedAt = cycle.getStartedAt()
//                                    endedAt = cycle.getEndedAt()
                                    //TODO: delete events element before populating?
//                                    for (day in startedAt..(endedAt+1) step DAYINMILLISEC) {
//                                        Log.i(TAG, "Reach this for loop")
//                                        val ev = Event(Color.CYAN, day)
//                                        compactCalendarView.addEvent(ev)
//                                    }
                                }
                            } else {
                                Log.i(TAG, "allCycles is empty!!")
                            }
                            addCycleEvents()
                        }
                    }

                }
            })
        }

    fun fetchDailyInputDate() {
        //Specify the class query
        val query : ParseQuery<DailyInput> = ParseQuery.getQuery(DailyInput::class.java)
        //Find all objects
        //This is line is added because user is a pointer
        query.include(DailyInput.KEY_USER)
        //Only return cycles from currently signed in user
        query.whereEqualTo(DailyInput.KEY_USER, ParseUser.getCurrentUser())

        query.findInBackground (object : FindCallback<DailyInput> {
            override fun done(dailyInputs: MutableList<DailyInput>?, e: ParseException?) {
                if (e != null) {
                    //Something went wrong
                    Log.e(TAG, "Error fetching posts")
                } else {
                    if (dailyInputs != null) {
                        allDailyInputs.clear()
                        compactCalendarView.removeAllEvents()
                        allDailyInputs.addAll(dailyInputs)
                        allDailyInputDate.clear()

                        if (allDailyInputs.size !=  0) {
                            Log.i(TAG, "allCycles is not empty!!")
                            for (input in allDailyInputs) {
                                allDailyInputDate.add(input.getDate())
                                Log.i(TAG, "$allDailyInputDate")
                            }
                        } else {
                            Log.i(TAG, "allDailyInputs is empty!!")
                        }
                        addEventsDailyInput()
                    }
                }

            }
        })
    }

    private fun addEventsDailyInput() {
        for (date in allDailyInputDate){
            val ev = Event(Color.CYAN, date)
            compactCalendarView.addEvent(ev)
        }
    }

    private fun gotoDailyInputActivity() {
        val intent = Intent(this, DailyInputActivity::class.java)
        startActivity(intent)
    }

    private fun computeMidnight(time: Long) : Long{
        var res = time/86400000
        res = res.toLong()
        return res*86400000 + 25200000
    }


    companion object{
        var TAG = "Calendar"
        var allDailyInputs: MutableList<DailyInput> = mutableListOf()
        var allDailyInputDate = ArrayList<Long>()
        var allCycles: MutableList<Cycle> = mutableListOf()
        var cyclesInPair = ArrayList<Pair<Long, Long>>()
        var mYEdited = ""
        var currentDate: Long = 0
        lateinit var compactCalendarView: CompactCalendarView
        var fetched = false
        var startedAt: Long = 0
        var endedAt: Long = 0
        const val DAYINMILLISEC = 86400000.toLong()
    }
}