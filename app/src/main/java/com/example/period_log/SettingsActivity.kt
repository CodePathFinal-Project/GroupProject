package com.example.period_log

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
        val intent = Intent(this, CycleSettingsActivity::class.java)
        startActivity(intent)
    }

}

