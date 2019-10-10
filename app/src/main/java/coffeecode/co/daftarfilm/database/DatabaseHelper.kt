package coffeecode.co.daftarfilm.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import coffeecode.co.daftarfilm.database.DatabaseContract.MovieColumns.Companion.MOVIE_RESPONSE
import coffeecode.co.daftarfilm.database.DatabaseContract.MovieColumns.Companion.TABLE_NAME
import coffeecode.co.daftarfilm.database.DatabaseContract.MovieColumns.Companion._ID

internal class DatabaseHelper(context: Context)
    :SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "dbmovie"

        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_MOVIE = "CREATE TABLE $TABLE_NAME" +
                " ($_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $MOVIE_RESPONSE TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_MOVIE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}