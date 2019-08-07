package coffeecode.co.daftarfilm.networking

import coffeecode.co.daftarfilm.BuildConfig

object ApiServices {

    fun getMovieServices() : MovieApiServices{
        return RetrofitClient
            .getClient(BuildConfig.BASE_URL_MOVIE_DB + BuildConfig.API_VERSION)
            .create(MovieApiServices::class.java)
    }
}