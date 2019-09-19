package coffeecode.co.daftarfilm.view

import coffeecode.co.daftarfilm.model.movie.MovieResponse

interface DataSource {
    interface MoviesApiCallback{
        fun onDataLoaded(movieResponse: MovieResponse)
        fun onDataEmpty()
        fun onError(message: String)
    }
}