package com.example.period_log

import com.parse.ParseClassName

//user : User
//startedAt : Date
//enddedAt : Date
@ParseClassName("Cycle")
class Cycle {

    //TODO: getStartedAt, setStartedAt, getEndedAt, setEndedAt, getUser, setUser

    companion object {
        const val KEY_STARTEDAT = "startedAt"
        const val KEY_ENDEDAT = "endedAt"
        const val KEY_USER = "user"
    }
}