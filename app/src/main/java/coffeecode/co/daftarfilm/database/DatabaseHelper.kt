package coffeecode.co.daftarfilm.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import coffeecode.co.daftarfilm.database.DatabaseContract.MovieColumns.Companion.MOVIE_RESPONSE
import coffeecode.co.daftarfilm.database.DatabaseContract.MovieColumns.Companion.TABLE_MOVIE
import coffeecode.co.daftarfilm.database.DatabaseContract.MovieColumns.Companion.TABLE_TV
import coffeecode.co.daftarfilm.database.DatabaseContract.MovieColumns.Companion.ID
import coffeecode.co.daftarfilm.database.DatabaseContract.MovieColumns.Companion.TV_RESPONSE

internal class DatabaseHelper(context: Context)
    :SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "dbmovie.db"

        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_MOVIE = "CREATE TABLE $TABLE_MOVIE" +
                " ($ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $MOVIE_RESPONSE TEXT NOT NULL)"

        private const val SQL_CREATE_TABLE_TV = "CREATE TABLE $TABLE_TV" +
                " ($ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $TV_RESPONSE TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_TV)
        db?.execSQL(SQL_CREATE_TABLE_MOVIE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MOVIE")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TV")
        onCreate(db)
    }

}