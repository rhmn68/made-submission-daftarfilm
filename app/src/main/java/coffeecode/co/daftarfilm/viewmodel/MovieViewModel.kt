package coffeecode.co.daftarfilm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import coffeecode.co.daftarfilm.model.kindofmovies.KindOfMovies
import coffeecode.co.daftarfilm.model.movie.MovieResponse
import coffeecode.co.daftarfilm.repo.MovieRepo

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRepo = MovieRepo(application)
    private var dataMovieNowPlaying : LiveData<MovieResponse>? = null
    private var dataKindOfMoviesForHome : LiveData<List<KindOfMovies>>? = null
    private var dataKindOfMoviesForTvShows : LiveData<List<KindOfMovies>>? = null
    private var dataKindOfMoviesForMovies : LiveData<List<KindOfMovies>>? = null
    private var isLoading : LiveData<Boolean>? = null
    private var errorMessage : LiveData<String>? = null

    fun getDataNowPlaying(): LiveData<MovieResponse>? {
        if (dataMovieNowPlaying == null){
            dataMovieNowPlaying = movieRepo.getDataNowPlaying()
        }

        return dataMovieNowPlaying
    }

    fun getDataKindOfMoviesForHome(): LiveData<List<KindOfMovies>>?{
        if (dataKindOfMoviesForHome == null){
            dataKindOfMoviesForHome = movieRepo.getDataKindOfMoviesForHome()
        }

        return dataKindOfMoviesForHome
    }

    fun getDataKindOfMoviesForTvShows(): LiveData<List<KindOfMovies>>?{
        if (dataKindOfMoviesForTvShows == null){
            dataKindOfMoviesForTvShows = movieRepo.getDataKindOfMoviesForTvShows()
        }

        return dataKindOfMoviesForTvShows
    }

    fun getDataKindOfMoviesForMovies(): LiveData<List<KindOfMovies>>?{
        if (dataKindOfMoviesForMovies == null){
            dataKindOfMoviesForMovies = movieRepo.getDataKindOfMoviesForMovies()
        }
        return dataKindOfMoviesForMovies
    }

    fun getErrorMessage(): LiveData<String>?{
        if (errorMessage == null){
            errorMessage = movieRepo.getErrorMessage()
        }
        return errorMessage
    }

    fun getIsLoading(): LiveData<Boolean>?{
        if (isLoading == null){
            isLoading = movieRepo.getIsLoading()
        }
        return isLoading
    }
}
