package coffeecode.co.daftarfilm.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import coffeecode.co.daftarfilm.model.credits.CreditsResponse
import coffeecode.co.daftarfilm.model.detail.MovieDetailResponse
import coffeecode.co.daftarfilm.model.video.VideoResponse
import coffeecode.co.daftarfilm.repo.MovieDetailRepo

class MovieDetailViewModel(application: Application): AndroidViewModel(application){
    private val movieDetailRepo = MovieDetailRepo(application)

    private var dataDetailMovie : LiveData<MovieDetailResponse>? = null
    private var dataCreditsMovie : LiveData<CreditsResponse>? = null
    private var dataVideo : LiveData<VideoResponse>? = null
    private var isLoading : LiveData<Boolean>? = null
    private var errorMessage : LiveData<String>? = null

    fun getDataDetailMovie(movieId: Int?): LiveData<MovieDetailResponse>?{
        if (dataDetailMovie == null){
            dataDetailMovie = movieDetailRepo.getDataDetailMovie(movieId)
        }

        return dataDetailMovie
    }

    fun getDataCreditsMovie(movieId: Int?): LiveData<CreditsResponse>?{
        if (dataCreditsMovie == null){
            dataCreditsMovie = movieDetailRepo.getDataCreditsMovie(movieId)
        }

        return dataCreditsMovie
    }

    fun getDataVideo(movieId: Int?): LiveData<VideoResponse>?{
        if (dataVideo == null){
            dataVideo = movieDetailRepo.getDataVideo(movieId)
        }

        return dataVideo
    }

    fun getErrorMessage(): LiveData<String>?{
        if (errorMessage == null){
            errorMessage = movieDetailRepo.getErrorMessage()
        }
        return errorMessage
    }

    fun getIsLoading(): LiveData<Boolean>?{
        if (isLoading == null){
            isLoading = movieDetailRepo.getIsLoading()
        }
        return isLoading
    }
}