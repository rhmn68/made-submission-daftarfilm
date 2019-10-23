package coffeecode.co.daftarfilm.model.kindofmovies

import coffeecode.co.daftarfilm.model.movie.MovieResponse

data class KindOfMovies(
    val tittle: String? = null,
    val movieResponse: MovieResponse? = null,
    val typeMovie: String? = null
){
    companion object{
        const val TYPE_MOVIE = "type_movie"
        const val TYPE_TV = "type_tv"
    }
}