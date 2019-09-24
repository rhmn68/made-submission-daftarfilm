package coffeecode.co.daftarfilm.networking

import coffeecode.co.daftarfilm.BuildConfig
import coffeecode.co.daftarfilm.networking.movieapiservices.MovieApiServices
import coffeecode.co.daftarfilm.networking.tvshowsapiservices.TvShowsApiServices

object ApiServices {

    fun getMovieServices() : MovieApiServices {
        return RetrofitClient
            .getClient(BuildConfig.BASE_URL_MOVIE_DB + BuildConfig.API_VERSION)
            .create(MovieApiServices::class.java)
    }

    fun getTvShowsServices() : TvShowsApiServices {
        return RetrofitClient
            .getClient(BuildConfig.BASE_URL_MOVIE_DB + BuildConfig.API_VERSION)
            .create(TvShowsApiServices::class.java)
    }
}