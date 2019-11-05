package com.peekster.database.movie

import android.content.Context
import com.couchbase.lite.Database
import com.couchbase.lite.DatabaseConfiguration

class database_utilss {
    companion object{
        fun getDatabase(c:Context):Database{
            val config = DatabaseConfiguration(c)
            val database = Database("peekster_db", config)
            return database
        }
    }
}