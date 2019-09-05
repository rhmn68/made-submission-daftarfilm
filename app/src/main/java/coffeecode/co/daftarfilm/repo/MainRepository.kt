package coffeecode.co.daftarfilm.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coffeecode.co.daftarfilm.BuildConfig
import coffeecode.co.daftarfilm.model.image_config.ImageConfigResponse
import coffeecode.co.daftarfilm.networking.ApiServices
import io.reactivex.schedulers.Schedulers

class MainRepository {
    private val imageConfigResponse = MutableLiveData<ImageConfigResponse>()

    fun getImageConfig() : LiveData<ImageConfigResponse>{
        ApiServices.getMovieServices()
            .getImageConfig(BuildConfig.TOKEN_MOVIE_DB)
            .observeOn(Schedulers.io())
            .subscribe({
                imageConfigResponse.value = it
            },{
                it.printStackTrace()
            })

        return imageConfigResponse
    }
}