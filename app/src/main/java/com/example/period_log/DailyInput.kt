package com.example.period_log

import com.parse.ParseClassName

//user : User
//cycle : Cycle
//cramp : Int
//fatigue : Int
//energy : Int
//acne : Int
@ParseClassName("DailyInput")
class DailyInput {
    //TODO: getUser, setUser, getCycle, setCycle, getCramp, setCramp, getfatigue, setfatigue
    //TODO: getEnergy, setEnergy, getAnce, setAnce

    companion object {
        const val KEY_USER = "user"
        const val KEY_CYCLE = "cycle"
        const val KEY_CRAMP = "cramp"
        const val KEY_FATIGUE = "fatigue"
        const val KEY_ENERGY = "energy"
        const val KEY_acne = "acne"
    }
}