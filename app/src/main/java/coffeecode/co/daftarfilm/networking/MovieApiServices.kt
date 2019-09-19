package coffeecode.co.daftarfilm.networking

import coffeecode.co.daftarfilm.model.imageconfig.ImageConfigResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import coffeecode.co.daftarfilm.BuildConfig
import coffeecode.co.daftarfilm.model.movie.MovieResponse

interface MovieApiServices{

    /**
     * IMAGE CONFIG API
     * BASE_URL :
     * @throws BuildConfig.BASE_URL_MOVIE_DB
     * */

    @GET("configuration")
    fun getConfiguration(@Query("api_key") apiKey: String?): Observable<ImageConfigResponse>

    @GET("movie/now_playing")
    fun getMovieNowPlaying(@Query("api_key") apiKey: String?): Observable<MovieResponse>

    @GET("movie/popular")
    fun getMoviePopular(@Query("api_key") apiKey: String?): Observable<MovieResponse>
}