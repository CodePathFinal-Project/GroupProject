package com.example.period_log

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class DailyInputActivity : AppCompatActivity() {

    lateinit var logDate: TextView
//    lateinit var startDate: EditText
//    lateinit var endDate: EditText
    lateinit var crampsSeekBar: SeekBar
    lateinit var acneSeekBar: SeekBar
    lateinit var headacheSeekBar: SeekBar
    lateinit var fatigueSeekBar: SeekBar
    lateinit var btnSave: Button
    lateinit var dailyInput: DailyInput

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
//        startDate =findViewById(R.id.etStartDate)
//        endDate =findViewById(R.id.etEndDate)


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
            dailyInput.setAcne(acneValue)
            dailyInput.setCramp(crampValue)
            dailyInput.setFatigue(fatigueValue)
            dailyInput.setHeadache(headacheValue)
        }




        //TODO: Change the startData and endData to ToggleButton
        //TODO: Might need to have find a way to persist the turn on of the Toggle Button

        //TODO: write saveStartDate -> post a new object with endDate - 1 to the server or delete an object from the server
        //TODO: write saveEndData -> look for the object with endDate -1 and update the endDate
    }
}