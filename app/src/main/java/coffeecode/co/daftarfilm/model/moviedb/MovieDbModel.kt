package coffeecode.co.daftarfilm.model.moviedb

import coffeecode.co.daftarfilm.model.movie.Movies
import java.io.Serializable

data class MovieDbModel(
    val id: Int? = null,
    val movies: Movies? = null
): Serializable