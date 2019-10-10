package coffeecode.co.daftarfilm.database

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class MovieColumns: BaseColumns{
        companion object{
            const val TABLE_NAME = "movie"
            const val _ID = "_id"
            const val MOVIE_RESPONSE = "movie_response"
        }
    }
}