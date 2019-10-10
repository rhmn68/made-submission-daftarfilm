package coffeecode.co.daftarfilm.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.apicallback.ApiCallBack
import coffeecode.co.daftarfilm.datasource.DataSource
import coffeecode.co.daftarfilm.model.kindofmovies.KindOfMovies
import coffeecode.co.daftarfilm.model.movie.MovieResponse

class MovieRepo(val context: Context) {
    private var dataSource = DataSource(context)

    private val dataKindOfMoviesForHome = MutableLiveData<List<KindOfMovies>>()
    private var dataMovieNowPlaying = MutableLiveData<MovieResponse>()

    private val isLoading = MutableLiveData<Boolean>()
    private val errorMessage = MutableLiveData<String>()

    fun getDataKindOfMoviesForHome(): LiveData<List<KindOfMovies>>?{
        val listKindOfMovies = mutableListOf<KindOfMovies>()
        isLoading.postValue(true)

        //Get data movie popular
        dataSource.getDataMoviePopular(object : ApiCallBack.MoviesApiCallback{
            override fun onDataLoaded(movieResponse: MovieResponse) {
                isLoading.postValue(false)
                listKindOfMovies.add(KindOfMovies(context.resources.getString(R.string.movie_popular),movieResponse))
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
        dataSource.getDataMovieTopRated(object : ApiCallBack.MoviesApiCallback{
            override fun onDataLoaded(movieResponse: MovieResponse) {
                isLoading.postValue(false)
                listKindOfMovies.add(KindOfMovies(context.getString(R.string.movie_top_rated),movieResponse))
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
        dataSource.getDataTvPopular(object : ApiCallBack.MoviesApiCallback{
            override fun onDataLoaded(movieResponse: MovieResponse) {
                isLoading.postValue(false)
                listKindOfMovies.add(KindOfMovies(context.getString(R.string.tv_popular),movieResponse))
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
        dataSource.getDataTvTopRated(object : ApiCallBack.MoviesApiCallback{
            override fun onDataLoaded(movieResponse: MovieResponse) {
                isLoading.postValue(false)
                listKindOfMovies.add(KindOfMovies(context.getString(R.string.tv_top_rated),movieResponse))
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

    fun getDataNowPlaying(): LiveData<MovieResponse>{
        isLoading.postValue(true)

        dataSource.getDataNowPlaying(object : ApiCallBack.MoviesApiCallback{
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

    fun getErrorMessage(): LiveData<String>?{
        return errorMessage
    }

    fun getIsLoading(): LiveData<Boolean>?{
        return isLoading
    }
}