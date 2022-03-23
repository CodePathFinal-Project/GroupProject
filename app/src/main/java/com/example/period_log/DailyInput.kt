package com.example.period_log

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser

//user : User
//cycle : Cycle
//cramp : Int
//fatigue : Int
//energy : Int
//acne : Int
@ParseClassName("DailyInput")
class DailyInput : ParseObject() {

    fun getDate() : Any? {
        return get(KEY_DATE)
    }

    fun setDate(date: Long){
        put(KEY_DATE, date)
    }

    fun getUser(): ParseUser? {
        return getParseUser(KEY_USER)
    }
    fun setUser(user: ParseUser) {
        put(KEY_USER, user)
    }

    fun getCycle() : ParseObject? {
        return getParseObject(KEY_CYCLE)
    }
    fun setCycle(cycle: Cycle) {
        put(KEY_CYCLE, cycle)
    }

    fun getCramp() : Int {
        return getInt(KEY_CRAMP)
    }
    fun setCramp(cramp: Int) {
        put(KEY_CRAMP, cramp)
    }

    fun getFatigue() : Int {
        return getInt(KEY_FATIGUE)
    }
    fun setFatigue(fatigue: Int) {
        put(KEY_FATIGUE, fatigue)
    }

    fun getHeadache() : Int {
        return getInt(KEY_HEADACHE)
    }
    fun setHeadache(headache: Int) {
        put(KEY_HEADACHE, headache)
    }

    fun getAcne() : Int {
        return getInt(KEY_ACNE)
    }
    fun setAcne(acne: Int) {
        put(KEY_ACNE, acne)
    }

    override fun setObjectId(id: String){
        setObjectId(id)
    }


    companion object {
        const val KEY_USER = "user"
        const val KEY_CYCLE = "cycle"
        const val KEY_CRAMP = "cramp"
        const val KEY_FATIGUE = "fatigue"
        const val KEY_HEADACHE = "headache"
        const val KEY_ACNE = "acne"
        const val KEY_DATE = "date"
    }
}