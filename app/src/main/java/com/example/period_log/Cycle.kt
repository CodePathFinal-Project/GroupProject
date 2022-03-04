package com.example.period_log

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.Parse
import com.parse.ParseUser

//import com.parse.ParseUser

//user : User
//startedAt : Int (milliseconds)
//endedAt : Int (milliseconds)
@ParseClassName("Cycle")
class Cycle : ParseObject(){

    //TODO: getStartedAt, setStartedAt, getEndedAt, setEndedAt, getUser, setUser
    fun getStartedAt(): Int? {
        return getInt(KEY_STARTEDAT)
    }
    fun setStartedAt(startAt: Int) {
        put(KEY_STARTEDAT,startAt )
    }

    fun getEndedAt(): Int? {
        return getInt(KEY_ENDEDAT)
    }
    fun setEndedAt(endedAt: Int) {
        put(KEY_ENDEDAT, endedAt)
    }

    fun getUser() : ParseUser? {
        return getParseUser(KEY_USER)
    }
    fun setUser(user: ParseUser) {
        put(KEY_USER, user)
    }


    companion object {
        const val KEY_STARTEDAT = "startedAt"
        const val KEY_ENDEDAT = "endedAt"
        const val KEY_USER = "user"
    }
}