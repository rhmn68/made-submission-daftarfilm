package coffeecode.co.daftarfilm.features.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coffeecode.co.daftarfilm.model.credits.CreditsResponse
import coffeecode.co.daftarfilm.model.detail.MovieDetailResponse
import coffeecode.co.daftarfilm.model.video.VideoResponse
import coffeecode.co.daftarfilm.view.DataSource

class MovieDetailViewModel(application: Application): AndroidViewModel(application){
    private var dataDetailMovie = MutableLiveData<MovieDetailResponse>()
    private var dataCreditsMovie = MutableLiveData<CreditsResponse>()
    private var isLoading = MutableLiveData<Boolean>()
    private var errorMessage = MutableLiveData<String>()
    private val movieDetailRepo = MovieDetailRepo()
    private val dataVideo = MutableLiveData<VideoResponse>()

    fun showLoading(){
        isLoading.postValue(true)
    }

    fun hideLoading(){
        isLoading.postValue(false)
    }

    fun getDataDetailMovie(movieId: Int?): LiveData<MovieDetailResponse>?{
        if (movieId != null) {
            movieDetailRepo.getDataMovieDetail(movieId, object : DataSource.MoviesDetailApiCallback{
                override fun onDataLoaded(movieDetailResponse: MovieDetailResponse) {
                    isLoading.postValue(false)
                    dataDetailMovie.postValue(movieDetailResponse)
                }

                override fun onDataEmpty() {
                    isLoading.postValue(false)
                    dataDetailMovie.postValue(null)
                }

                override fun onError(message: String) {
                    isLoading.postValue(false)
                    errorMessage.postValue(message)
                }
            })
        }

        return dataDetailMovie
    }

    fun getDataCreditsMovie(movieId: Int?): LiveData<CreditsResponse>?{
        if (movieId != null){
            movieDetailRepo.getDataMovieCredits(movieId, object : DataSource.MoviesCreditsApiCallback{
                override fun onDataLoaded(movieCredits: CreditsResponse) {
                    isLoading.postValue(false)
                    dataCreditsMovie.postValue(movieCredits)
                }

                override fun onDataEmpty() {
                    isLoading.postValue(false)
                    dataCreditsMovie.postValue(null)
                }

                override fun onError(message: String) {
                    isLoading.postValue(false)
                    errorMessage.postValue(message)
                }

            })
        }

        return dataCreditsMovie
    }

    fun getDataVideo(movieId: Int?): LiveData<VideoResponse>?{
        if (movieId != null){
            movieDetailRepo.getDataVideo(movieId, object : DataSource.MoviesVideoApiCallback{
                override fun onDataLoaded(videoResponse: VideoResponse) {
                    dataVideo.postValue(videoResponse)
                }

                override fun onDataEmpty() {
                    dataVideo.postValue(null)
                }

                override fun onError(message: String) {
                    isLoading.postValue(false)
                    errorMessage.postValue(message)
                }

            })
        }

        return dataVideo
    }
}