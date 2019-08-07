package coffeecode.co.daftarfilm.networking

import coffeecode.co.daftarfilm.features.main.model.image_config.ImageConfigResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import coffeecode.co.daftarfilm.BuildConfig

interface MovieApiServices{

    /**
     * IMAGE CONFIG API
     * BASE_URL :
     * @throws BuildConfig.BASE_URL_MOVIE_DB
     * */
    @GET("configuration")
    fun getImageConfig(@Query("api_key") apiKey: String?): Observable<ImageConfigResponse>
}