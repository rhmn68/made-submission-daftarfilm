package coffeecode.co.daftarfilm.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import coffeecode.co.daftarfilm.database.DatabaseContract.MovieColumns.Companion.TABLE_NAME
import coffeecode.co.daftarfilm.database.DatabaseContract.MovieColumns.Companion._ID
import java.sql.SQLException

class MovieHelper(context: Context){
    companion object{
        private const val DATABASE_TABLE = TABLE_NAME
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

    fun queryAll(): Cursor?{
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC",
            null
        )
    }

    fun queryById(id: String): Cursor{
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insert(values: ContentValues?): Long{
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int{
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int{
        return database.delete(TABLE_NAME, "$_ID = $id", null)
    }
}