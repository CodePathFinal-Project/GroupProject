package com.example.period_log

import com.parse.Parse
import android.app.Application
import com.parse.ParseObject

class PeriodLogApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        //To register the parse class
        ParseObject.registerSubclass(Cycle::class.java)
        ParseObject.registerSubclass(DailyInput::class.java)
        ParseObject.registerSubclass(UserPeriodAndCycleLength::class.java)

        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());
    }
}