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

    fun getCramp() : Any? {
        return get(KEY_CRAMP)
    }
    fun setCramp(cramp: Int) {
        put(KEY_CRAMP, cramp)
    }

    fun getFatigue() : Any? {
        return get(KEY_FATIGUE)
    }
    fun setFatigue(fatigue: Int) {
        put(KEY_FATIGUE, fatigue)
    }

    fun getEnergy() : Any? {
        return get(KEY_ENERGY)
    }
    fun setEnergy(energy: Int) {
        put(KEY_ENERGY, energy)
    }

    fun getAcne() : Any? {
        return get(KEY_ACNE)
    }
    fun setAcne(acne: Int) {
        put(KEY_ACNE, acne)
    }


    companion object {
        const val KEY_USER = "user"
        const val KEY_CYCLE = "cycle"
        const val KEY_CRAMP = "cramp"
        const val KEY_FATIGUE = "fatigue"
        const val KEY_ENERGY = "energy"
        const val KEY_ACNE = "acne"
    }
}