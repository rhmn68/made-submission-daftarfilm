package coffeecode.co.daftarfilm.features.main.subfeatures

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coffeecode.co.daftarfilm.features.main.subfeatures.home.HomeRepo
import coffeecode.co.daftarfilm.model.kindofmovies.KindOfMovies
import coffeecode.co.daftarfilm.model.movie.MovieResponse
import coffeecode.co.daftarfilm.view.DataSource

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private var homeRepo: HomeRepo =
        HomeRepo()
    private var dataMovieNowPlaying : MutableLiveData<MovieResponse>? = null
    private var dataMoviePopular: MutableLiveData<MovieResponse>? = null
    private var dataKindOfMovies = MutableLiveData<List<KindOfMovies>>()

    private val isLoading = MutableLiveData<Boolean>()
    private val errorMessage = MutableLiveData<String>()

    fun getDataNowPlaying(): LiveData<MovieResponse>? {
        isLoading.postValue(true)
        dataMovieNowPlaying = MutableLiveData()

        homeRepo.getDataNowPlaying(object : DataSource.MoviesApiCallback{
            override fun onDataLoaded(movieResponse: MovieResponse) {
                isLoading.postValue(false)
                dataMovieNowPlaying?.postValue(movieResponse)
            }

            override fun onDataEmpty() {
                isLoading.postValue(false)
                dataMovieNowPlaying?.postValue(null)
            }

            override fun onError(message: String) {
                isLoading.postValue(false)
                errorMessage.postValue(message)
            }
        })

        return dataMovieNowPlaying
    }

    fun getDataMoviePopular(): LiveData<MovieResponse>?{
        isLoading.postValue(true)
        dataMoviePopular = MutableLiveData()

        homeRepo.getDataMoviePopular(object : DataSource.MoviesApiCallback{
            override fun onDataLoaded(movieResponse: MovieResponse) {
                isLoading.postValue(false)
                dataMoviePopular?.postValue(movieResponse)
            }

            override fun onDataEmpty() {
                isLoading.postValue(false)
                dataMoviePopular?.postValue(null)
            }

            override fun onError(message: String) {
                isLoading.postValue(false)
                errorMessage.postValue(message)
            }
        })
        return  dataMoviePopular
    }

    fun setDataKindOfMovies(tittle: String, movieResponse: MovieResponse){
        val listKindOfMovies = mutableListOf<KindOfMovies>()
        listKindOfMovies.add(KindOfMovies(tittle, movieResponse))
        dataKindOfMovies.postValue(listKindOfMovies)
    }

    fun getDataKindOfMovies(): LiveData<List<KindOfMovies>>?{
        return dataKindOfMovies
    }

    fun getErrorMessage(): LiveData<String>?{
        return errorMessage
    }

    fun getIsLoading(): LiveData<Boolean>?{
        return isLoading
    }
}