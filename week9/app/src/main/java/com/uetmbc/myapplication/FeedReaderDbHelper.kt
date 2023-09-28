package com.uetmbc.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.uetmbc.myapplication.FeedReaderContract.FeedEntry

class FeedReaderDbHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_CREATE_ENTRIES_UPGRADE);
    }

    companion object {
        private val SQL_CREATE_ENTRIES = "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                FeedEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                FeedEntry.COLUMN_NAME_SUBTITLE + " TEXT)"

        private val SQL_CREATE_ENTRIES_UPGRADE = "CREATE TABLE IF NOT EXISTS" + FeedEntry.TABLE_NAME + " (" +
                FeedEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                FeedEntry.COLUMN_NAME_SUBTITLE + " TEXT)"

        public val DATABASE_NAME = "bai9";
        public val DATABASE_VERSION = 4;
    }
}
