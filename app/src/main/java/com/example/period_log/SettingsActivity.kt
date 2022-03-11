package com.example.period_log

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_layout)

        var buttonAccount = findViewById<Button>(R.id.buttonAccount)
        buttonAccount.setOnClickListener {
            gotoAccountSettingsActivity()
        }

        var buttonCycle = findViewById<Button>(R.id.buttonCycle)
        buttonCycle.setOnClickListener {
            gotoCycleSettingsActivity()
        }
    }

    companion object {
        const val TAG ="SettingsActivity"
    }

    private fun gotoAccountSettingsActivity() {
        val intent = Intent(this, AccountSettingsActivity::class.java)
        startActivity(intent)
    }

    private fun gotoCycleSettingsActivity() {
        Log.d(Calendar.TAG, "Cycle button clicked should direct to CycleSettings")
        Toast.makeText(this, "cycle btn", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, CycleSettingsActivity::class.java)
        startActivity(intent)

    }

}

