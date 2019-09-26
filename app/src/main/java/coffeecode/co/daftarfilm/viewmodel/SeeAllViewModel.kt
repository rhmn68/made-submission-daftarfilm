package coffeecode.co.daftarfilm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import coffeecode.co.daftarfilm.model.movie.MovieResponse
import coffeecode.co.daftarfilm.repo.SeeAllRepo

class SeeAllViewModel (application: Application): AndroidViewModel(application){
    private val seeAllRepo = SeeAllRepo()
    private var dataMovieResponse : LiveData<MovieResponse>? = null
    private var isLoading: LiveData<Boolean>? = null

    fun getDataMovieResponse(movieResponse: MovieResponse?): LiveData<MovieResponse>?{
        if (dataMovieResponse == null){
            dataMovieResponse = seeAllRepo.getDataMovieResponse(movieResponse)
        }
        return dataMovieResponse
    }

    fun getDataIsLoading(): LiveData<Boolean>?{
        if (isLoading == null){
            isLoading = seeAllRepo.getIsLoading()
        }
        return isLoading
    }
}