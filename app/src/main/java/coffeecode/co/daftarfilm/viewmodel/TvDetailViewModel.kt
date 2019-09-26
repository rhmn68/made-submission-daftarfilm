package coffeecode.co.daftarfilm.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import coffeecode.co.daftarfilm.model.credits.CreditsResponse
import coffeecode.co.daftarfilm.model.tvdetail.TvDetailResponse
import coffeecode.co.daftarfilm.model.video.VideoResponse
import coffeecode.co.daftarfilm.repo.TvDetailRepo

class TvDetailViewModel(application: Application): AndroidViewModel(application){
    private val tvDetailRepo = TvDetailRepo(application)
    private var dataDetailTv : LiveData<TvDetailResponse>? = null
    private var dataCreditsTv : LiveData<CreditsResponse>? = null
    private var dataVideoTv : LiveData<VideoResponse>? = null
    private var isLoading : LiveData<Boolean>? = null
    private var errorMessage : LiveData<String>? = null

    fun getDataDetailTv(tvId: Int?): LiveData<TvDetailResponse>?{
        if (dataDetailTv == null){
            dataDetailTv = tvDetailRepo.getDataDetailTv(tvId)
        }

        return dataDetailTv
    }

    fun getDataCreditsTv(tvId: Int?): LiveData<CreditsResponse>?{
        if (dataCreditsTv == null){
            dataCreditsTv = tvDetailRepo.getDataCreditsTv(tvId)
        }
        return dataCreditsTv
    }

    fun getDataVideoTv(tvId: Int?): LiveData<VideoResponse>?{
        if (dataVideoTv == null){
            dataVideoTv = tvDetailRepo.getDataVideoTv(tvId)
        }
        return dataVideoTv
    }

    fun getErrorMessage(): LiveData<String>?{
        if (errorMessage == null){
            errorMessage = tvDetailRepo.getErrorMessage()
        }
        return errorMessage
    }

    fun getIsLoading(): LiveData<Boolean>?{
        if (isLoading == null){
            isLoading = tvDetailRepo.getIsLoading()
        }
        return isLoading
    }
}