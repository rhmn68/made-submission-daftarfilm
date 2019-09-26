package coffeecode.co.daftarfilm.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coffeecode.co.daftarfilm.model.movie.MovieResponse

class SeeAllRepo {
    private val dataMovieResponse = MutableLiveData<MovieResponse>()
    private val isLoading = MutableLiveData<Boolean>()

    fun getDataMovieResponse(movieResponse: MovieResponse?): LiveData<MovieResponse>?{
        isLoading.postValue(true)

        if (movieResponse != null){
            isLoading.postValue(false)
            dataMovieResponse.postValue(movieResponse)
        }else{
            isLoading.postValue(false)
            dataMovieResponse.postValue(null)
        }
        return dataMovieResponse
    }

    fun getIsLoading(): LiveData<Boolean>?{
        return isLoading
    }
}