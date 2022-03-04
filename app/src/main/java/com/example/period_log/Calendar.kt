package com.example.period_log

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import java.util.*


class Calendar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
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
        compactCalendarView.addEvent(ev2)

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
                Log.d(TAG, "Day was clicked: $dateClicked with events $events")
                Toast.makeText(this@Calendar, "Date $dateClicked was clicked", Toast.LENGTH_LONG).show()
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                Log.d(TAG, "Month was scrolled to: $firstDayOfNewMonth")
                Toast.makeText(this@Calendar, "Month scrolled $firstDayOfNewMonth", Toast.LENGTH_LONG).show()
            }

        })
    }
    companion object{
        var TAG = "HI"
    }
}