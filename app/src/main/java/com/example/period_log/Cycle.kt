package com.example.period_log

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser

//user : User
//startedAt : Int (milliseconds)
//endedAt : Int (milliseconds)
@ParseClassName("Cycle")
class Cycle : ParseObject(){

    //TODO: getStartedAt, setStartedAt, getEndedAt, setEndedAt, getUser, setUser
    fun getStartedAt(): Long {
        return getLong(KEY_STARTED_AT)
    }
    fun setStartedAt(startAt: Long) {
        put(KEY_STARTED_AT,startAt )
    }

    fun getEndedAt(): Long {
        return getLong(KEY_ENDED_AT)
    }
    fun setEndedAt(endedAt: Long) {
        put(KEY_ENDED_AT, endedAt)
    }

    fun getUser() : ParseUser? {
        return getParseUser(KEY_USER)
    }
    fun setUser(user: ParseUser) {
        put(KEY_USER, user)
    }

    companion object {
        const val KEY_STARTED_AT = "startedAt"
        const val KEY_ENDED_AT = "endedAt"
        const val KEY_USER = "user"
    }
}