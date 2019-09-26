package coffeecode.co.daftarfilm.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coffeecode.co.daftarfilm.apicallback.ApiCallBack
import coffeecode.co.daftarfilm.datasource.DataSource
import coffeecode.co.daftarfilm.model.credits.CreditsResponse
import coffeecode.co.daftarfilm.model.detail.MovieDetailResponse
import coffeecode.co.daftarfilm.model.video.VideoResponse

class MovieDetailRepo(context: Context) {
    private val dataSource = DataSource(context)
    private var dataDetailMovie = MutableLiveData<MovieDetailResponse>()
    private var dataCreditsMovie = MutableLiveData<CreditsResponse>()
    private val dataVideo = MutableLiveData<VideoResponse>()
    private var isLoading = MutableLiveData<Boolean>()
    private var errorMessage = MutableLiveData<String>()

    fun getDataDetailMovie(movieId: Int?): LiveData<MovieDetailResponse>?{
        if (movieId != null){
            isLoading.postValue(true)

            dataSource.getDataMovieDetail(movieId, object : ApiCallBack.MoviesDetailApiCallback{
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
            isLoading.postValue(true)

            dataSource.getDataMovieCredits(movieId, object : ApiCallBack.MoviesCreditsApiCallback{
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
            isLoading.postValue(true)

            dataSource.getDataVideo(movieId, object : ApiCallBack.MoviesVideoApiCallback{
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

    fun getErrorMessage(): LiveData<String>?{
        return errorMessage
    }

    fun getIsLoading(): LiveData<Boolean>?{
        return isLoading
    }
}