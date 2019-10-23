package coffeecode.co.daftarfilm.database

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class MovieColumns: BaseColumns{
        companion object{
            const val TABLE_MOVIE = "movie"
            const val TABLE_TV = "tv"
            const val ID = "id"
            const val MOVIE_RESPONSE = "movie_response"
            const val TV_RESPONSE = "tv_response"
        }
    }
}