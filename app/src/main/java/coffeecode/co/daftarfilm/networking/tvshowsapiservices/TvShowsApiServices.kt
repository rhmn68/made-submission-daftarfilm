package coffeecode.co.daftarfilm.networking.tvshowsapiservices

import coffeecode.co.daftarfilm.BuildConfig
import coffeecode.co.daftarfilm.model.movie.MovieResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowsApiServices{
    /**
     * IMAGE CONFIG API
     * BASE_URL :
     * @throws BuildConfig.BASE_URL_MOVIE_DB
     * */

    @GET("tv/popular")
    fun getTvPopular(@Query("api_key") apiKey: String?): Observable<MovieResponse>

    @GET("tv/top_rated")
    fun getTvTopRated(@Query("api_key") apiKey: String?): Observable<MovieResponse>

    @GET("discover/tv")
    fun getTvDiscover(@Query("api_key") apiKey: String?): Observable<MovieResponse>
}