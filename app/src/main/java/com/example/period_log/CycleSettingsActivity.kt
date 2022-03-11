package com.example.period_log

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class CycleSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cycle_layout)

        val saveCycle = findViewById<Button>(R.id.saveCycle)
        val PeriodLengthET_setting = findViewById<EditText>(R.id.PeriodLengthET_setting)
        val CycleLengthET_setting = findViewById<EditText>(R.id.CycleLengthET_setting)

        PeriodLengthET_setting.setOnClickListener(){
            Log.i(TAG, PeriodLengthET_setting.text.toString()) // logcat message for debugging
            val periodLengthEt_value = PeriodLengthET_setting.text.toString().toInt() // stores user input as integer
        }

        CycleLengthET_setting.setOnClickListener(){
            Log.i(TAG, CycleLengthET_setting.text.toString())
            val CycleLengthET_setting_value = CycleLengthET_setting.text.toString().toInt()
        }
    }

    companion object {
        const val TAG ="CycleSettingsActivity"
    }
}