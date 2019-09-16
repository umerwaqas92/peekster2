package com.peekster.adapter

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB_Helper(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null, DB_VERSION) {

    companion object {
        val DB_VERSION = 1
        val DATABASE_NAME = "Peekster_Db"
        val TABLE_PEEKSTER = "peekter_content"

        val ID = "id"
        val NAME = "name"
        val THUMBNAILS = "thumbnails"
        val TIME = "time"
        val PATH = "path"
        val WATCH_LENGTH = "watch_length"
        val WATCH_TIME = "watch time"
        val TITLE = "title"
        val DESCRIPTION = "description"
        val RATING = "rating"
        val RElEASE_YEAR = "release_year"
        val CATEGORY = "category"
        val LENGTH = "length"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_Table=("CREATE TABLE" + TABLE_PEEKSTER+"("+ID + "INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE," + NAME + "TEXT," + THUMBNAILS + "BLOB," +
                TIME + "TEXT," + PATH + "TEXT," + WATCH_LENGTH + "TEXT," + WATCH_TIME + "TEXT," + TITLE + "TEXT,"+ DESCRIPTION + "TEXT," + RATING + "RATING," +
                RElEASE_YEAR + "TEXT," + CATEGORY + "TEXT," + LENGTH + "TEXT,")

        db!!.execSQL(CREATE_Table)
     }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS" + TABLE_PEEKSTER)
        onCreate(db)

     }

}
