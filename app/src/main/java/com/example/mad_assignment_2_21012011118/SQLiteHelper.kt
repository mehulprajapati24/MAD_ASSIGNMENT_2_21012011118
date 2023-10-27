package com.example.mad_assignment_2_21012011118

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "news_hub.db"
        private const val TABLE_NAME = "news_table"
        private const val IMG = "img"
        private const val TITLE = "title"
        private const val SOURCE = "source"
        private const val URL = "url"
    }

    override fun onCreate(db: SQLiteDatabase?) {
//        val createTableQuery = ("CREATE TABLE " + TABLE_NAME + "("
//                + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
//                + EMAIL + " TEXT)")
        val createTableQuery = "CREATE TABLE $TABLE_NAME($IMG TEXT, $TITLE TEXT, $SOURCE TEXT, $URL TEXT)"
        if (db != null) {
            db.execSQL(createTableQuery)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        }
    }

    fun insertNews(img: String, title: String, source: String, url: String) : Long
    {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(IMG, img)
        contentValues.put(TITLE, title)
        contentValues.put(SOURCE, source)
        contentValues.put(URL, url)

        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return success
    }


    @SuppressLint("Range")
    fun getNews() : MutableList<NewsItem>
    {
        val newsList = mutableListOf<NewsItem>()

        val db = readableDatabase
        var query = "SELECT * FROM $TABLE_NAME"
        var cursor : Cursor =db.rawQuery(query,null)

        while (cursor.moveToNext())
        {
            var img : String = cursor.getString(cursor.getColumnIndex(IMG))
            var title : String = cursor.getString(cursor.getColumnIndex(TITLE))
            var source : String = cursor.getString(cursor.getColumnIndex(SOURCE))
            var url: String = cursor.getString(cursor.getColumnIndex(URL))

            var item = NewsItem(img, title, source, url)
            newsList.add(item)
        }
        cursor.close()
        return newsList
    }

    @SuppressLint("Range")
    fun checkNews(titleNews: String) : String
    {
        var titleOfNews = ""
        val db = readableDatabase
        val columns = arrayOf(TITLE)
        val selection = "$TITLE = ?"
        val selectionArgs = arrayOf(titleNews)
        val cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null)
        if (cursor.moveToFirst()) {
            titleOfNews = cursor.getString(cursor.getColumnIndex(TITLE))
        }
        cursor.close()
        return titleOfNews
    }

    fun deleteNews(title : String) : Int
    {
        val db = writableDatabase
        val selection = "$TITLE = ?"
        val selectionArgs = arrayOf(title)

        val success = db.delete(TABLE_NAME, selection,selectionArgs)
        db.close()
        return success
    }

    fun deleteAllNews() : Int
    {
        val db = writableDatabase

        val success = db.delete(TABLE_NAME, null,null)
        db.close()
        return success
    }

}