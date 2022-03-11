package com.example.period_log

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.SeekBar

class DailyInputActivity : AppCompatActivity() {

    lateinit var startDate: EditText
    lateinit var endDate: EditText
    lateinit var crampsSeekBar: SeekBar
    lateinit var acneSeekBar: SeekBar
    lateinit var headacheSeekBar: SeekBar
    lateinit var fatigueSeekBar: SeekBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daily_input_layout)

        crampsSeekBar = findViewById(R.id.crampsBar)
        acneSeekBar = findViewById(R.id.acneBar)
        headacheSeekBar = findViewById(R.id.headacheBar)
        fatigueSeekBar = findViewById(R.id.fatigueBar)

        //TODO: Change the startData and endData to ToggleButton
        //TODO: Might need to have find a way to persist the turn on of the Toggle Button

        //TODO: write saveStartDate -> post a new object with endDate - 1 to the server or delete an object from the server
        //TODO: write saveEndData -> look for the object with endDate -1 and update the endDate
    }
}