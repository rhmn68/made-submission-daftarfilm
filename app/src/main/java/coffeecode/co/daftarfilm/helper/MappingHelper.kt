package coffeecode.co.daftarfilm.helper

import android.database.Cursor
import coffeecode.co.daftarfilm.database.DatabaseContract.MovieColumns.Companion.MOVIE_RESPONSE
import coffeecode.co.daftarfilm.database.DatabaseContract.MovieColumns.Companion._ID
import coffeecode.co.daftarfilm.model.movie.Movies
import coffeecode.co.daftarfilm.model.moviedb.MovieDbModel
import com.google.gson.Gson

object MappingHelper {

    fun mapCursorMovieToArrayList(movieCursor: Cursor?): ArrayList<MovieDbModel>{
        val movieList = ArrayList<MovieDbModel>()
        while (movieCursor?.moveToNext()!!){
            val id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(_ID))
            val dataMovie = movieCursor.getString(movieCursor.getColumnIndexOrThrow(MOVIE_RESPONSE))
            val movie = Gson().fromJson(dataMovie, Movies::class.java)
            movieList.add(MovieDbModel(id, movie))
        }
        return movieList
    }

}