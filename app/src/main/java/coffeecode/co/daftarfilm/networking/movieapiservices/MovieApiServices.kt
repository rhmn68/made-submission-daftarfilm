package coffeecode.co.daftarfilm.networking.movieapiservices

import coffeecode.co.daftarfilm.model.imageconfig.ImageConfigResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import coffeecode.co.daftarfilm.BuildConfig
import coffeecode.co.daftarfilm.model.credits.CreditsResponse
import coffeecode.co.daftarfilm.model.detail.MovieDetailResponse
import coffeecode.co.daftarfilm.model.genres.GenreResponse
import coffeecode.co.daftarfilm.model.movie.MovieResponse
import coffeecode.co.daftarfilm.model.video.VideoResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Path

interface MovieApiServices{

    /**
     * IMAGE CONFIG API
     * BASE_URL :
     * @throws BuildConfig.BASE_URL_MOVIE_DB
     * */

    @GET("configuration")
    fun getConfiguration(@Query("api_key") apiKey: String?): Observable<ImageConfigResponse>

    @GET("genre/movie/list")
    fun getGenres(@Query("api_key") apiKey: String?,
                  @Query("language") language: String?): Observable<GenreResponse>

    @GET("movie/now_playing")
    fun getMovieNowPlaying(@Query("api_key") apiKey: String?,
                           @Query("language") language: String?): Observable<MovieResponse>

    @GET("movie/popular")
    fun getMoviePopular(@Query("api_key") apiKey: String?,
                        @Query("language") language: String?): Observable<MovieResponse>

    @GET("movie/upcoming")
    fun getMovieUpComing(@Query("api_key") apiKey: String?,
                         @Query("language") language: String?): Single<MovieResponse>

    @GET("movie/top_rated")
    fun getMovieTopRated(@Query("api_key") apiKey: String?,
                         @Query("language") language: String?): Observable<MovieResponse>

    @GET("discover/movie")
    fun getMovieDiscover(@Query("api_key") apiKey: String?,
                         @Query("language") language: String?): Observable<MovieResponse>

    @GET("movie/{movie_id}")
    fun getDetailMovie(@Path("movie_id") movieId: Int,
                       @Query("api_key") apiKey: String?,
                       @Query("language") language: String?) : Observable<MovieDetailResponse>

    @GET("movie/{movie_id}/credits")
    fun getCreditsMovie(@Path("movie_id") movieId: Int,
                        @Query("api_key") apiKey: String?) : Observable<CreditsResponse>

    @GET("movie/{movie_id}/videos")
    fun getVideoTrailer(@Path("movie_id") movieId: Int,
                        @Query("api_key") apiKey: String?) : Observable<VideoResponse>
}