package coffeecode.co.daftarfilm.datasource

import android.content.Context
import coffeecode.co.daftarfilm.BuildConfig
import coffeecode.co.daftarfilm.networking.ApiServices
import coffeecode.co.daftarfilm.apicallback.ApiCallBack
import coffeecode.co.daftarfilm.database.MovieHelper
import coffeecode.co.daftarfilm.helper.MappingHelper
import coffeecode.co.daftarfilm.model.movie.Movies
import coffeecode.co.daftarfilm.model.moviedb.MovieDbModel
import coffeecode.co.daftarfilm.storage.HawkStorage
import io.reactivex.schedulers.Schedulers

class DataSource(private val context: Context) {
    private val hawkStorage = HawkStorage(context)

    fun getDataNowPlaying(movieApiCallback: ApiCallBack.MoviesApiCallback){
        ApiServices.getMovieServices()
            .getMovieNowPlaying(BuildConfig.TOKEN_MOVIE_DB, hawkStorage.getLanguage())
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it!= null){
                    movieApiCallback.onDataLoaded(it)
                }else{
                    movieApiCallback.onDataEmpty()
                }
            },{
                it.printStackTrace()
                movieApiCallback.onError("${it.message}")
            })
    }

    fun getDataMoviePopular(movieApiCallback: ApiCallBack.MoviesApiCallback){
        ApiServices.getMovieServices()
            .getMoviePopular(BuildConfig.TOKEN_MOVIE_DB, hawkStorage.getLanguage())
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it != null){
                    movieApiCallback.onDataLoaded(it)
                }else{
                    movieApiCallback.onDataEmpty()
                }
            },{
                it.printStackTrace()
                movieApiCallback.onError("${it.message}")
            })
    }

    fun getDataMovieTopRated(movieApiCallback: ApiCallBack.MoviesApiCallback){
        ApiServices.getMovieServices()
            .getMovieTopRated(BuildConfig.TOKEN_MOVIE_DB, hawkStorage.getLanguage())
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it != null){
                    movieApiCallback.onDataLoaded(it)
                }else{
                    movieApiCallback.onDataEmpty()
                }
            },{
                it.printStackTrace()
                movieApiCallback.onError("${it.message}")
            })
    }

    fun getDataTvPopular(movieApiCallback: ApiCallBack.MoviesApiCallback){
        ApiServices.getTvShowsServices()
            .getTvPopular(BuildConfig.TOKEN_MOVIE_DB, hawkStorage.getLanguage())
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it != null){
                    movieApiCallback.onDataLoaded(it)
                }else{
                    movieApiCallback.onDataEmpty()
                }
            },{
                it.printStackTrace()
                movieApiCallback.onError("${it.message}")
            })
    }

    fun getDataTvTopRated(movieApiCallback: ApiCallBack.MoviesApiCallback){
        ApiServices.getTvShowsServices()
            .getTvTopRated(BuildConfig.TOKEN_MOVIE_DB, hawkStorage.getLanguage())
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it != null){
                    movieApiCallback.onDataLoaded(it)
                }else{
                    movieApiCallback.onDataEmpty()
                }
            },{
                it.printStackTrace()
                movieApiCallback.onError("${it.message}")
            })
    }

    fun getDataTvDiscover(movieApiCallback: ApiCallBack.MoviesApiCallback){
        ApiServices.getTvShowsServices()
            .getTvDiscover(BuildConfig.TOKEN_MOVIE_DB, hawkStorage.getLanguage())
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it != null){
                    movieApiCallback.onDataLoaded(it)
                }else{
                    movieApiCallback.onDataEmpty()
                }
            },{
                it.printStackTrace()
                movieApiCallback.onError("${it.message}")
            })
    }

    fun getDataMovieDetail(movieId: Int, apiCallback: ApiCallBack.MoviesDetailApiCallback){
        ApiServices.getMovieServices()
            .getDetailMovie(movieId, BuildConfig.TOKEN_MOVIE_DB, hawkStorage.getLanguage())
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it != null){
                    apiCallback.onDataLoaded(it)
                }else{
                    apiCallback.onDataEmpty()
                }
            },{
                it.printStackTrace()
                apiCallback.onError("${it.message}")
            })
    }

    fun getDataMovieCredits(movieId: Int, apiCallback: ApiCallBack.MoviesCreditsApiCallback){
        ApiServices.getMovieServices()
            .getCreditsMovie(movieId, BuildConfig.TOKEN_MOVIE_DB)
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it != null){
                    apiCallback.onDataLoaded(it)
                }else{
                    apiCallback.onDataEmpty()
                }
            },{
                it.printStackTrace()
                apiCallback.onError("${it.message}")
            })
    }

    fun getDataVideo(movieId: Int, apiCallback: ApiCallBack.MoviesVideoApiCallback){
        ApiServices.getMovieServices()
            .getVideoTrailer(movieId, BuildConfig.TOKEN_MOVIE_DB)
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it != null){
                    apiCallback.onDataLoaded(it)
                }else{
                    apiCallback.onDataEmpty()
                }
            },{
                it.printStackTrace()
                apiCallback.onError("${it.message}")
            })
    }

    fun getDataTvDetail(tvId: Int, apiCallback: ApiCallBack.TvDetailCallback){
        ApiServices.getTvShowsServices()
            .getTvDetail(tvId, BuildConfig.TOKEN_MOVIE_DB, hawkStorage.getLanguage())
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it != null){
                    apiCallback.onDataLoaded(it)
                }else{
                    apiCallback.onDataEmpty()
                }
            },{
                it.printStackTrace()
                apiCallback.onError("${it.message}")
            })
    }

    fun getDataTvVideo(tvId: Int, apiCallback: ApiCallBack.MoviesVideoApiCallback){
        ApiServices.getTvShowsServices()
            .getTvVideo(tvId, BuildConfig.TOKEN_MOVIE_DB)
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it != null){
                    apiCallback.onDataLoaded(it)
                }else{
                    apiCallback.onDataEmpty()
                }
            },{
                it.printStackTrace()
                apiCallback.onError("${it.message}")
            })
    }

    fun getDataTvCredits(tvId: Int, apiCallback: ApiCallBack.MoviesCreditsApiCallback){
        ApiServices.getTvShowsServices()
            .getCreditsTv(tvId, BuildConfig.TOKEN_MOVIE_DB, hawkStorage.getLanguage())
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it != null){
                    apiCallback.onDataLoaded(it)
                }else{
                    apiCallback.onDataEmpty()
                }
            },{
                it.printStackTrace()
                apiCallback.onError("${it.message}")
            })
    }

    fun getAllDataMovieFromSql(): ArrayList<MovieDbModel>?{
        val movieHelper = MovieHelper.getInstance(context)
        movieHelper.open()

        val cursor = movieHelper.queryAll()


        return MappingHelper.mapCursorMovieToArrayList(cursor)
    }
}