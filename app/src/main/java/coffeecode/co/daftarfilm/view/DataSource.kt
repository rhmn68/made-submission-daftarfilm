package coffeecode.co.daftarfilm.view

import coffeecode.co.daftarfilm.model.credits.CreditsResponse
import coffeecode.co.daftarfilm.model.detail.MovieDetailResponse
import coffeecode.co.daftarfilm.model.movie.MovieResponse
import coffeecode.co.daftarfilm.model.video.VideoResponse

interface DataSource {
    interface MoviesApiCallback{
        fun onDataLoaded(movieResponse: MovieResponse)
        fun onDataEmpty()
        fun onError(message: String)
    }

    interface MoviesDetailApiCallback{
        fun onDataLoaded(movieDetailResponse: MovieDetailResponse)
        fun onDataEmpty()
        fun onError(message: String)
    }

    interface MoviesCreditsApiCallback{
        fun onDataLoaded(movieCredits: CreditsResponse)
        fun onDataEmpty()
        fun onError(message: String)
    }

    interface MoviesVideoApiCallback{
        fun onDataLoaded(videoResponse: VideoResponse)
        fun onDataEmpty()
        fun onError(message: String)
    }
}