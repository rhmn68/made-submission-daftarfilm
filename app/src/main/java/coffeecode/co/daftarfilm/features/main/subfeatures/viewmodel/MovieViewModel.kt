package coffeecode.co.daftarfilm.features.main.subfeatures.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coffeecode.co.daftarfilm.features.main.subfeatures.repo.MovieRepo
import coffeecode.co.daftarfilm.model.kindofmovies.KindOfMovies
import coffeecode.co.daftarfilm.model.movie.MovieResponse
import coffeecode.co.daftarfilm.view.DataSource

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private var movieRepo: MovieRepo = MovieRepo()
    private var dataMovieNowPlaying = MutableLiveData<MovieResponse>()
    private var dataKindOfMoviesForHome = MutableLiveData<List<KindOfMovies>>()
    private var dataKindOfMoviesForTvShows = MutableLiveData<List<KindOfMovies>>()
    private var dataKindOfMoviesForMovies = MutableLiveData<List<KindOfMovies>>()

    private val isLoading = MutableLiveData<Boolean>()
    private val errorMessage = MutableLiveData<String>()

    fun setShowLoading(){
        isLoading.postValue(true)
    }

    fun setHideLoading(){
        isLoading.postValue(false)
    }

    fun getDataNowPlaying(): LiveData<MovieResponse>? {
        movieRepo.getDataNowPlaying(object : DataSource.MoviesApiCallback{
            override fun onDataLoaded(movieResponse: MovieResponse) {
                isLoading.postValue(false)
                dataMovieNowPlaying.postValue(movieResponse)
            }

            override fun onDataEmpty() {
                isLoading.postValue(false)
                dataMovieNowPlaying.postValue(null)
            }

            override fun onError(message: String) {
                isLoading.postValue(false)
                errorMessage.postValue(message)
            }
        })

        return dataMovieNowPlaying
    }

    fun getDataKindOfMoviesForHome(): LiveData<List<KindOfMovies>>?{
        val listKindOfMovies = mutableListOf<KindOfMovies>()

        //Reset all data
        dataKindOfMoviesForHome.postValue(null)

        //Get data movie up coming
        movieRepo.getDataMovieUpComing(object : DataSource.MoviesApiCallback{
            override fun onDataLoaded(movieResponse: MovieResponse) {
                isLoading.postValue(false)
                listKindOfMovies.add(KindOfMovies("Movie Up Coming",movieResponse))
                dataKindOfMoviesForHome.postValue(listKindOfMovies)
            }

            override fun onDataEmpty() {
                isLoading.postValue(false)
                dataKindOfMoviesForHome.postValue(null)
            }

            override fun onError(message: String) {
                isLoading.postValue(false)
                errorMessage.postValue(message)
            }

        })

        //Get data movie popular
        movieRepo.getDataMoviePopular(object : DataSource.MoviesApiCallback{
            override fun onDataLoaded(movieResponse: MovieResponse) {
                isLoading.postValue(false)
                listKindOfMovies.add(KindOfMovies("Movie Popular",movieResponse))
                dataKindOfMoviesForHome.postValue(listKindOfMovies)
            }

            override fun onDataEmpty() {
                isLoading.postValue(false)
                dataKindOfMoviesForHome.postValue(null)
            }

            override fun onError(message: String) {
                isLoading.postValue(false)
                errorMessage.postValue(message)
            }

        })

        //Get data movie
        movieRepo.getDataMovieTopRated(object : DataSource.MoviesApiCallback{
            override fun onDataLoaded(movieResponse: MovieResponse) {
                isLoading.postValue(false)
                listKindOfMovies.add(KindOfMovies("Movie Top Rated",movieResponse))
                dataKindOfMoviesForHome.postValue(listKindOfMovies)
            }

            override fun onDataEmpty() {
                isLoading.postValue(false)
                dataKindOfMoviesForHome.postValue(null)
            }

            override fun onError(message: String) {
                isLoading.postValue(false)
                errorMessage.postValue(message)
            }
        })

        // Get data tv popular
        movieRepo.getDataTvPopular(object : DataSource.MoviesApiCallback{
            override fun onDataLoaded(movieResponse: MovieResponse) {
                isLoading.postValue(false)
                listKindOfMovies.add(KindOfMovies("Tv Popular",movieResponse))
                dataKindOfMoviesForHome.postValue(listKindOfMovies)
            }

            override fun onDataEmpty() {
                isLoading.postValue(false)
                dataKindOfMoviesForHome.postValue(null)
            }

            override fun onError(message: String) {
                isLoading.postValue(false)
                errorMessage.postValue(message)
            }

        })

        //Get data tv top rated
        movieRepo.getDataTvTopRated(object : DataSource.MoviesApiCallback{
            override fun onDataLoaded(movieResponse: MovieResponse) {
                isLoading.postValue(false)
                listKindOfMovies.add(KindOfMovies("Tv Top Rated",movieResponse))
                dataKindOfMoviesForHome.postValue(listKindOfMovies)
            }

            override fun onDataEmpty() {
                isLoading.postValue(false)
                dataKindOfMoviesForHome.postValue(null)
            }

            override fun onError(message: String) {
                isLoading.postValue(false)
                errorMessage.postValue(message)
            }

        })

        //Get data tv discover
        movieRepo.getDataTvDiscover(object : DataSource.MoviesApiCallback{
            override fun onDataLoaded(movieResponse: MovieResponse) {
                isLoading.postValue(false)
                listKindOfMovies.add(KindOfMovies("Tv Discover",movieResponse))
                dataKindOfMoviesForHome.postValue(listKindOfMovies)
            }

            override fun onDataEmpty() {
                isLoading.postValue(false)
                dataKindOfMoviesForHome.postValue(null)
            }

            override fun onError(message: String) {
                isLoading.postValue(false)
                errorMessage.postValue(message)
            }

        })

        return dataKindOfMoviesForHome
    }

    fun getDataKindOfMoviesForTvShows(): LiveData<List<KindOfMovies>>?{
        val listKindOfMovies = mutableListOf<KindOfMovies>()

        //Reset all data
        dataKindOfMoviesForTvShows.postValue(null)

        // Get data tv popular
        movieRepo.getDataTvPopular(object : DataSource.MoviesApiCallback{
            override fun onDataLoaded(movieResponse: MovieResponse) {
                isLoading.postValue(false)
                listKindOfMovies.add(KindOfMovies("Tv Popular",movieResponse))
                dataKindOfMoviesForTvShows.postValue(listKindOfMovies)
            }

            override fun onDataEmpty() {
                isLoading.postValue(false)
                dataKindOfMoviesForTvShows.postValue(null)
            }

            override fun onError(message: String) {
                isLoading.postValue(false)
                errorMessage.postValue(message)
            }

        })

        //Get data tv top rated
        movieRepo.getDataTvTopRated(object : DataSource.MoviesApiCallback{
            override fun onDataLoaded(movieResponse: MovieResponse) {
                isLoading.postValue(false)
                listKindOfMovies.add(KindOfMovies("Tv Top Rated",movieResponse))
                dataKindOfMoviesForTvShows.postValue(listKindOfMovies)
            }

            override fun onDataEmpty() {
                isLoading.postValue(false)
                dataKindOfMoviesForTvShows.postValue(null)
            }

            override fun onError(message: String) {
                isLoading.postValue(false)
                errorMessage.postValue(message)
            }

        })

        //Get data tv discover
        movieRepo.getDataTvDiscover(object : DataSource.MoviesApiCallback{
            override fun onDataLoaded(movieResponse: MovieResponse) {
                isLoading.postValue(false)
                listKindOfMovies.add(KindOfMovies("Tv Discover",movieResponse))
                dataKindOfMoviesForTvShows.postValue(listKindOfMovies)
            }

            override fun onDataEmpty() {
                isLoading.postValue(false)
                dataKindOfMoviesForTvShows.postValue(null)
            }

            override fun onError(message: String) {
                isLoading.postValue(false)
                errorMessage.postValue(message)
            }

        })

        return dataKindOfMoviesForTvShows
    }

    fun getDataKindOfMoviesForMovies(): LiveData<List<KindOfMovies>>?{
        val listKindOfMovies = mutableListOf<KindOfMovies>()

        //Reset all data
        dataKindOfMoviesForMovies.postValue(null)

        //Get data movie up coming
        movieRepo.getDataMovieUpComing(object : DataSource.MoviesApiCallback{
            override fun onDataLoaded(movieResponse: MovieResponse) {
                isLoading.postValue(false)
                listKindOfMovies.add(KindOfMovies("Movie Up Coming",movieResponse))
                dataKindOfMoviesForMovies.postValue(listKindOfMovies)
            }

            override fun onDataEmpty() {
                isLoading.postValue(false)
                dataKindOfMoviesForMovies.postValue(null)
            }

            override fun onError(message: String) {
                isLoading.postValue(false)
                errorMessage.postValue(message)
            }

        })

        //Get data movie popular
        movieRepo.getDataMoviePopular(object : DataSource.MoviesApiCallback{
            override fun onDataLoaded(movieResponse: MovieResponse) {
                isLoading.postValue(false)
                listKindOfMovies.add(KindOfMovies("Movie Popular",movieResponse))
                dataKindOfMoviesForMovies.postValue(listKindOfMovies)
            }

            override fun onDataEmpty() {
                isLoading.postValue(false)
                dataKindOfMoviesForMovies.postValue(null)
            }

            override fun onError(message: String) {
                isLoading.postValue(false)
                errorMessage.postValue(message)
            }

        })

        //Get data movie top rated
        movieRepo.getDataMovieTopRated(object : DataSource.MoviesApiCallback{
            override fun onDataLoaded(movieResponse: MovieResponse) {
                isLoading.postValue(false)
                listKindOfMovies.add(KindOfMovies("Movie Top Rated",movieResponse))
                dataKindOfMoviesForMovies.postValue(listKindOfMovies)
            }

            override fun onDataEmpty() {
                isLoading.postValue(false)
                dataKindOfMoviesForMovies.postValue(null)
            }

            override fun onError(message: String) {
                isLoading.postValue(false)
                errorMessage.postValue(message)
            }
        })

        //Get data movie top rated
        movieRepo.getDataNowPlaying(object : DataSource.MoviesApiCallback{
            override fun onDataLoaded(movieResponse: MovieResponse) {
                isLoading.postValue(false)
                listKindOfMovies.add(KindOfMovies("Movie Now Playing",movieResponse))
                dataKindOfMoviesForMovies.postValue(listKindOfMovies)
            }

            override fun onDataEmpty() {
                isLoading.postValue(false)
                dataKindOfMoviesForMovies.postValue(null)
            }

            override fun onError(message: String) {
                isLoading.postValue(false)
                errorMessage.postValue(message)
            }
        })

        return dataKindOfMoviesForMovies
    }

    fun getErrorMessage(): LiveData<String>?{
        return errorMessage
    }

    fun getIsLoading(): LiveData<Boolean>?{
        return isLoading
    }
}
