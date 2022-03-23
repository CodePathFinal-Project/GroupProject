package com.example.period_log

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser

@ParseClassName("UserPeriodAndCycleLength")
class UserPeriodAndCycleLength : ParseObject() {

    fun getPeriodLength(): Int {
        return getInt(KEY_PERIOD_LENGTH)
    }
    fun setPeriodLength(periodLength: Int) {
        put(KEY_PERIOD_LENGTH, periodLength )
    }

    fun getCycleLength(): Int {
        return getInt(KEY_CYCLE_LENGTH)
    }
    fun setCycleLength(cycleLength: Int) {
        put(KEY_CYCLE_LENGTH, cycleLength)
    }

    fun getUser(): ParseUser? {
        return getParseUser(KEY_OBJECT_ID)
    }
    fun setUser(user: ParseUser) {
        put(KEY_USER, user)
    }

    companion object {
        const val KEY_PERIOD_LENGTH = "periodLength"
        const val KEY_CYCLE_LENGTH = "cycleLength"
        const val KEY_USER = "user"
    }
}