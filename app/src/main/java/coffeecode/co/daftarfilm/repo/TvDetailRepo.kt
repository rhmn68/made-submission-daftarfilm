package coffeecode.co.daftarfilm.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coffeecode.co.daftarfilm.apicallback.ApiCallBack
import coffeecode.co.daftarfilm.datasource.DataSource
import coffeecode.co.daftarfilm.model.credits.CreditsResponse
import coffeecode.co.daftarfilm.model.tvdetail.TvDetailResponse
import coffeecode.co.daftarfilm.model.video.VideoResponse

class TvDetailRepo(context: Context) {
    private val dataSource = DataSource(context)
    private val dataDetailTv = MutableLiveData<TvDetailResponse>()
    private val dataCreditsTv = MutableLiveData<CreditsResponse>()
    private val dataVideoTv = MutableLiveData<VideoResponse>()
    private var isLoading = MutableLiveData<Boolean>()
    private var errorMessage = MutableLiveData<String>()

    fun getDataDetailTv(tvId: Int?): LiveData<TvDetailResponse>?{
        if (tvId != null){
            isLoading.postValue(true)

            dataSource.getDataTvDetail(tvId, object : ApiCallBack.TvDetailCallback{
                override fun onDataLoaded(response: TvDetailResponse) {
                    isLoading.postValue(false)
                    dataDetailTv.postValue(response)
                }

                override fun onDataEmpty() {
                    isLoading.postValue(false)
                    dataDetailTv.postValue(null)
                }

                override fun onError(message: String) {
                    isLoading.postValue(false)
                    errorMessage.postValue(message)
                }

            })
        }

        return dataDetailTv
    }

    fun getDataCreditsTv(tvId: Int?): LiveData<CreditsResponse>?{
        if (tvId != null){
            isLoading.postValue(true)

            dataSource.getDataTvCredits(tvId, object : ApiCallBack.MoviesCreditsApiCallback{
                override fun onDataLoaded(movieCredits: CreditsResponse) {
                    isLoading.postValue(false)
                    dataCreditsTv.postValue(movieCredits)
                }

                override fun onDataEmpty() {
                    isLoading.postValue(false)
                    dataCreditsTv.postValue(null)
                }

                override fun onError(message: String) {
                    isLoading.postValue(false)
                    errorMessage.postValue(message)
                }

            })
        }

        return dataCreditsTv
    }

    fun getDataVideoTv(tvId: Int?): LiveData<VideoResponse>?{
        if (tvId != null){
            isLoading.postValue(true)

            dataSource.getDataTvVideo(tvId, object : ApiCallBack.MoviesVideoApiCallback{
                override fun onDataLoaded(videoResponse: VideoResponse) {
                    isLoading.postValue(false)
                    dataVideoTv.postValue(videoResponse)
                }


                override fun onDataEmpty() {
                    isLoading.postValue(false)
                    dataVideoTv.postValue(null)
                }

                override fun onError(message: String) {
                    isLoading.postValue(false)
                    errorMessage.postValue(message)
                }

            })
        }
        return dataVideoTv
    }

    fun getErrorMessage(): LiveData<String>?{
        return errorMessage
    }

    fun getIsLoading(): LiveData<Boolean>?{
        return isLoading
    }
}