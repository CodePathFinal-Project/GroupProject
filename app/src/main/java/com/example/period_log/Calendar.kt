package com.example.period_log

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import java.util.*
import java.util.Calendar
import java.time.LocalDateTime

// TODO: add settings button to the calendar view

class Calendar : AppCompatActivity() {

    lateinit var mmmmYYYY : TextView
    lateinit var btnSettings: ImageButton
    val wrapper: Context = ContextThemeWrapper(this, R.style.PopupMenuStyle)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        mmmmYYYY = findViewById(R.id.tvMonth)
        mmmmYYYY.setText(MONTHS[Calendar.getInstance().get(Calendar.MONTH)] + ' '+ Calendar.getInstance().get(Calendar.YEAR).toString())

        //SETTINGS BUTTON
        btnSettings = findViewById<ImageButton>(R.id.cvSettings)
        btnSettings.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }


        val compactCalendarView: CompactCalendarView = findViewById(R.id.compactcalendar_view) as CompactCalendarView
        // Set first day of week to Monday, defaults to Monday so calling setFirstDayOfWeek is not necessary
        // Use constants provided by Java Calendar class

        // Add event on Feb 22, 2022
        val ev1 = Event(Color.GREEN, 1645516800000, "Some extra data that I want to store.")
        compactCalendarView.addEvent(ev1)

        // Added event Feb 23, 2022
        val ev2 = Event(Color.GREEN, 1645603200000)
        compactCalendarView.addEvent(ev2)

        // Added event Feb 23 ,2022
        val ev3 = Event(Color.CYAN, 1644739200000)
        compactCalendarView.addEvent(ev3)

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
//                val events: List<Event> = compactCalendarView.getEvents(dateClicked)
                temp = dateClicked.time
                mYEdited = dateClicked.toString()
                Log.d(TAG, "Day was clicked: $dateClicked with events $events")
                Toast.makeText(this@Calendar, "$temp", Toast.LENGTH_SHORT).show()
                showPopup(compactCalendarView)
                //TODO: change position of the popup
                //TODO: fetch user data when view daily input (only if exists)
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                Log.d(TAG, "Month was scrolled to: $firstDayOfNewMonth")
                temp1 = firstDayOfNewMonth.time //returns milliseconds
                //milliseconds to Date class
                Toast.makeText(this@Calendar, "$firstDayOfNewMonth", Toast.LENGTH_SHORT).show()
                mYEdited = firstDayOfNewMonth.toString()
                mmmmYYYY.setText(mYEdited.substring(4,8) + mYEdited.substring(24,28))

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
        var TAG = "HI"
        val MONTHS = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
            "Aug", "Sep", "Oct", "Nov", "Dec")
        var temp : Long = 0
        var temp1 : Long = 0
        var mYEdited : String =""
    }
}