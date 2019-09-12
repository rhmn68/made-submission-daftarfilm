package coffeecode.co.daftarfilm.repo

import coffeecode.co.daftarfilm.BuildConfig
import coffeecode.co.daftarfilm.model.image_config.ImageConfigResponse
import coffeecode.co.daftarfilm.networking.ApiServices
import io.reactivex.schedulers.Schedulers

class MainRepository {
    companion object{
        fun getDataImage() : ImageConfigResponse{
            var imageConfigResponse = ImageConfigResponse()
            ApiServices.getMovieServices()
                .getImageConfig(BuildConfig.TOKEN_MOVIE_DB)
                .observeOn(Schedulers.io())
                .subscribe({
                    imageConfigResponse = it
                },{
                    it.printStackTrace()
                })

            return imageConfigResponse
        }
    }
}