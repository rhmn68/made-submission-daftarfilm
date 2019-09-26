package coffeecode.co.daftarfilm.networking.tvshowsapiservices

import coffeecode.co.daftarfilm.BuildConfig
import coffeecode.co.daftarfilm.model.credits.CreditsResponse
import coffeecode.co.daftarfilm.model.detail.MovieDetailResponse
import coffeecode.co.daftarfilm.model.movie.MovieResponse
import coffeecode.co.daftarfilm.model.tvdetail.TvDetailResponse
import coffeecode.co.daftarfilm.model.video.VideoResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowsApiServices{
    /**
     * IMAGE CONFIG API
     * BASE_URL :
     * @throws BuildConfig.BASE_URL_MOVIE_DB
     * */

    @GET("tv/popular")
    fun getTvPopular(@Query("api_key") apiKey: String?,
                     @Query("language") language: String?): Observable<MovieResponse>

    @GET("tv/top_rated")
    fun getTvTopRated(@Query("api_key") apiKey: String?,
                      @Query("language") language: String?): Observable<MovieResponse>

    @GET("discover/tv")
    fun getTvDiscover(@Query("api_key") apiKey: String?,
                      @Query("language") language: String?): Observable<MovieResponse>

    @GET("tv/{tv_id}")
    fun getTvDetail(@Path("tv_id") tvId: Int?,
                    @Query("api_key") apiKey: String?,
                    @Query("language") language: String?): Observable<TvDetailResponse>

    @GET("tv/{tv_id}/videos")
    fun getTvVideo(@Path("tv_id") tvId: Int?,
                   @Query("api_key") apiKey: String?): Observable<VideoResponse>

    @GET("tv/{tv_id}/credits")
    fun getCreditsTv(@Path("tv_id") movieId: Int,
                     @Query("api_key") apiKey: String?,
                     @Query("language") language: String?) : Observable<CreditsResponse>
}