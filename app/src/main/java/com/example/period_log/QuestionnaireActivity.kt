package com.example.period_log

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class QuestionnaireActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.questionnaire_layout)

        val saveBtn = findViewById<Button>(R.id.saveBtn)
        val periodLengthEt = findViewById<EditText>(R.id.periodLengthEt)
        val cycleLengthEt = findViewById<EditText>(R.id.cycleLengthEt)

    }
}