package com.example.period_log

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.widget.*
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser
import java.util.*
import java.util.Calendar


// TODO: Add a refresh button to the calendarView -> so we could show the recent cycle

// TODO: Write fetchCycle to fetch all the cycle when the refresh button is clicked
// TODO: Show all the period cycle on the calendar view
class Calendar : AppCompatActivity() {

    lateinit var mmmmYYYY : TextView
    lateinit var mYEdited : String
    lateinit var btnSettings: ImageButton
    lateinit var btnRefresh : Button
    val wrapper: Context = ContextThemeWrapper(this, R.style.PopupMenuStyle)

    //var allCycles: MutableList<Cycle> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        mmmmYYYY = findViewById(R.id.tvMonth)
        mmmmYYYY.setText(MONTHS[Calendar.getInstance().get(Calendar.MONTH)] + ' '+ Calendar.getInstance().get(Calendar.YEAR).toString())

        //REFRESH BUTTON
        //TODO: refresh button where its on clickListener call fetchCycles
        btnRefresh = findViewById<Button>(R.id.Refreshbutton)

        btnRefresh.setOnClickListener {
            fetchCycles()
            startActivity(intent)
            finish()
        }

        //SETTINGS BUTTON
        btnSettings = findViewById<ImageButton>(R.id.cvSettings)
        btnSettings.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)

            //TODO: Test the fetchQuery
            //fetchCycles()
            startActivity(intent)
        }

        val compactCalendarView: CompactCalendarView = findViewById(R.id.compactcalendar_view) as CompactCalendarView
        // Set first day of week to Monday, defaults to Monday so calling setFirstDayOfWeek is not necessary
        // Use constants provided by Java Calendar class

        // Add event on Feb 22, 2022
        var ev = Event(Color.GREEN, 1645516800000, "Some extra data that I want to store.")
        compactCalendarView.addEvent(ev)

        // Added event Feb 23, 2022
        ev = Event(Color.GREEN, 1645603200000)
        compactCalendarView.addEvent(ev)

        // Added event Feb 23 ,2022
        val temp = 1644739200000
        ev = Event(Color.CYAN, temp)
        compactCalendarView.addEvent(ev)

        //Added event to the last startedAt March 05, 2022
        if (allCycles.size !=  0) {
            Log.i(TAG, "allCycles is not empty!!")
//            val cycle = allCycles[0]
//            Log.i(TAG, )
//            for (cycle in allCycles) {
//                val temp = cycle.getStartedAt()
//                Log.i(TAG, "Inside the for loop")
//                val ev = Event(Color.CYAN, temp)
//                compactCalendarView.addEvent(ev)
        } else {
            Log.i(TAG, "allCycles is empty!!")
        }

        //TODO : Try to show the cycle on the calendar on March 05 - March 12 and Feb 20 - Feb 25
//        if (allCycles != null) {
//            for (cycle in allCycles) {
//                var startedAt = cycle.getStartedAt()
//                var endedAt = cycle.getEndedAt()
//                val oneDayinMilliSec = 86400000.toLong()
//                for (day in startedAt..(endedAt+1) step oneDayinMilliSec) {
//                    Log.i(TAG, "Reach this for loop")
//                    ev = Event(Color.CYAN, 1644739200000)
//                    compactCalendarView.addEvent(ev)
//                }
//            }
//        }

        // Query for events on Sun, 07 Jun 2015 GMT.
        // Time is not relevant when querying for events, since events are returned by day.
        // So you can pass in any arbitrary DateTime and you will receive all events for that day.
        val events: List<Event> =
            compactCalendarView.getEvents(1433701251000L) // can also take a Date object

        // events has size 2 with the 2 events inserted previously
        Log.d(TAG, "Events: $events")


        // define a listener to receive callbacks when certain events happen.
        compactCalendarView.setListener(object : CompactCalendarView.CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
                val events: List<Event> = compactCalendarView.getEvents(dateClicked)
                var temp = dateClicked.time
                Log.d(TAG, "Day was clicked: $dateClicked with events $events")
                Toast.makeText(this@Calendar, "$temp", Toast.LENGTH_SHORT).show()
                showPopup(compactCalendarView)
                //TODO: change position of the popup
                //TODO: fetch user data when view daily input (only if exists)
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                Log.d(TAG, "Month was scrolled to: $firstDayOfNewMonth")
                var temp1 = firstDayOfNewMonth.time //returns milliseconds
                //milliseconds to Date class
                Toast.makeText(this@Calendar, "$firstDayOfNewMonth", Toast.LENGTH_SHORT).show()
                mYEdited = firstDayOfNewMonth.toString()
                mmmmYYYY.setText(mYEdited.substring(4,8) + mYEdited.substring(24,28))

            }

        })
    }

    private fun fetchCycles() {
        //Specify the class query
        val query : ParseQuery<Cycle> = ParseQuery.getQuery(Cycle::class.java)
        //Find all objects
        //This is line is added because user is a pointer
        query.include(Cycle.KEY_USER)
        //Only return cycles from currently signed in user
        query.whereEqualTo(Cycle.KEY_USER, ParseUser.getCurrentUser())
        query.addDescendingOrder(Cycle.KEY_STARTED_AT)

        //Only return the most recent 5 cycles
        query.setLimit(2)

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
                            allCycles.addAll(cycles)
                            for (cycle in allCycles) {
                                Log.i(
                                    TAG, "Cycle startedAt: " + cycle.getStartedAt().toString()
                                            + ", endedAt: " + cycle.getEndedAt().toString()
                                            + ", username:" + cycle.getUser()?.username
                                )
                            }

                            //TODO: notifyChanged to rerender the calendarView
                        }
                    }

                }
            })
        }

    private fun showPopup(v : CompactCalendarView){
        val popupMenu: PopupMenu = PopupMenu(wrapper, v, Gravity.FILL_VERTICAL) //gravity.right? or sliding window?
        popupMenu.menuInflater.inflate(R.menu.popup_menu_calendar, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.action_viewDI -> {
                    Toast.makeText(this@Calendar,"You Clicked : " + item.title,Toast.LENGTH_SHORT).show()
                    gotoDailyInputActivity()
                }
                R.id.action_addDI -> {
                    Toast.makeText(this@Calendar,"You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                    gotoDailyInputActivity()
                }
            }
            true
        })
        popupMenu.show()
    }

    private fun gotoDailyInputActivity() {
        val intent = Intent(this, DailyInputActivity::class.java)
        startActivity(intent)
    }

    companion object{
        var TAG = "Calendar"
        val MONTHS = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
            "Aug", "Sep", "Oct", "Nov", "Dec")
        var allCycles: MutableList<Cycle> = mutableListOf()
    }
}