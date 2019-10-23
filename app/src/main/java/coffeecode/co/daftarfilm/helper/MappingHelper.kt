package coffeecode.co.daftarfilm.helper

import android.database.Cursor
import coffeecode.co.daftarfilm.database.DatabaseContract.MovieColumns.Companion.MOVIE_RESPONSE
import coffeecode.co.daftarfilm.database.DatabaseContract.MovieColumns.Companion.ID
import coffeecode.co.daftarfilm.database.DatabaseContract.MovieColumns.Companion.TV_RESPONSE
import coffeecode.co.daftarfilm.model.movie.Movies
import coffeecode.co.daftarfilm.model.moviedb.MovieDbModel
import com.google.gson.Gson

object MappingHelper {

    fun mapCursorMovieToArrayList(movieCursor: Cursor?): ArrayList<MovieDbModel>{
        val movieList = ArrayList<MovieDbModel>()
        while (movieCursor?.moveToNext()!!){
            val id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(ID))
            val dataMovie = movieCursor.getString(movieCursor.getColumnIndexOrThrow(MOVIE_RESPONSE))
            val movie = Gson().fromJson(dataMovie, Movies::class.java)
            movieList.add(MovieDbModel(id, movie))
        }
        return movieList
    }

    fun mapCursorTvToArrayList(tvCursor: Cursor?): ArrayList<MovieDbModel>{
        val tvList = ArrayList<MovieDbModel>()
        while (tvCursor?.moveToNext()!!){
            val id = tvCursor.getInt(tvCursor.getColumnIndexOrThrow(ID))
            val dataTv = tvCursor.getString(tvCursor.getColumnIndexOrThrow(TV_RESPONSE))
            val tv = Gson().fromJson(dataTv, Movies::class.java)
            tvList.add(MovieDbModel(id, tv))
        }
        return tvList
    }

}