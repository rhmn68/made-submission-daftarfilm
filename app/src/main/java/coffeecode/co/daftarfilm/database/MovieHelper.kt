package coffeecode.co.daftarfilm.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import coffeecode.co.daftarfilm.database.DatabaseContract.MovieColumns.Companion.TABLE_MOVIE
import coffeecode.co.daftarfilm.database.DatabaseContract.MovieColumns.Companion.ID
import coffeecode.co.daftarfilm.database.DatabaseContract.MovieColumns.Companion.TABLE_TV
import java.sql.SQLException

class MovieHelper(context: Context){
    companion object{
        private const val DATABASE_TABLE_MOVIE = TABLE_MOVIE
        private const val DATABASE_TABLE_TV = TABLE_TV
        private lateinit var dataBaseHelper : DatabaseHelper
        private lateinit var database: SQLiteDatabase
        private var INSTANCE: MovieHelper? = null

        fun getInstance(context: Context): MovieHelper{
            if (INSTANCE == null){
                synchronized(SQLiteOpenHelper::class.java){
                    if (INSTANCE == null){
                        INSTANCE = MovieHelper(context)
                    }
                }
            }
            return INSTANCE as MovieHelper
        }
    }

    init {
        dataBaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open(){
        database = dataBaseHelper.writableDatabase
    }

    fun close(){
        dataBaseHelper.close()

        if (database.isOpen){
            database.close()
        }
    }

    fun queryAllMovie(): Cursor?{
        return database.query(
            DATABASE_TABLE_MOVIE,
            null,
            null,
            null,
            null,
            null,
            "$ID ASC",
            null
        )
    }

    fun insertMovie(values: ContentValues?): Long{
        return database.insert(DATABASE_TABLE_MOVIE, null, values)
    }

    fun deleteMovieById(id: String): Int{
        return database.delete(TABLE_MOVIE, "$ID = $id", null)
    }

    fun queryAllTv(): Cursor?{
        return database.query(
            DATABASE_TABLE_TV,
            null,
            null,
            null,
            null,
            null,
            "$ID ASC",
            null
        )
    }

    fun insertTv(values: ContentValues?): Long{
        return database.insert(DATABASE_TABLE_TV, null, values)
    }

    fun deleteTvById(id: String): Int{
        return database.delete(DATABASE_TABLE_TV, "$ID = $id", null)
    }
}