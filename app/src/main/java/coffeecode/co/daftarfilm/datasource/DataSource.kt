package coffeecode.co.daftarfilm.datasource

import android.content.Context
import coffeecode.co.daftarfilm.BuildConfig
import coffeecode.co.daftarfilm.apicallback.ApiCallBack
import coffeecode.co.daftarfilm.model.kindofmovies.KindOfMovies
import coffeecode.co.daftarfilm.model.movie.MovieResponse
import coffeecode.co.daftarfilm.networking.ApiServices
import coffeecode.co.daftarfilm.storage.HawkStorage
import io.reactivex.Observable
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers

class DataSource(context: Context) {
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

    fun getDataMoviesAndTv(apiCallback: ApiCallBack.DataMovieAndTvApiCallback) {
        val movieTopRated = ApiServices.getMovieServices()
            .getMovieTopRated(BuildConfig.TOKEN_MOVIE_DB, hawkStorage.getLanguage())
            .subscribeOn(Schedulers.newThread())
        val tvTopRated = ApiServices.getTvShowsServices()
            .getTvTopRated(BuildConfig.TOKEN_MOVIE_DB, hawkStorage.getLanguage())
            .subscribeOn(Schedulers.newThread())
        val moviePopular = ApiServices.getMovieServices()
                .getMoviePopular(BuildConfig.TOKEN_MOVIE_DB, hawkStorage.getLanguage())
                .subscribeOn(Schedulers.newThread())
        val tvPopular =  ApiServices.getTvShowsServices()
                .getTvPopular(BuildConfig.TOKEN_MOVIE_DB, hawkStorage.getLanguage())
                .subscribeOn(Schedulers.newThread())

        Observable.zip(
                moviePopular,
                movieTopRated,
                tvPopular,
                tvTopRated,
                Function4<MovieResponse, MovieResponse, MovieResponse, MovieResponse, List<KindOfMovies>>{
                    t1, t2, t3, t4 ->
                    val listKindOfMovies = ArrayList<KindOfMovies>()
                    listKindOfMovies.add(KindOfMovies("Movie Popular", t1, KindOfMovies.TYPE_MOVIE))
                    listKindOfMovies.add(KindOfMovies("Movie Top Rated", t2, KindOfMovies.TYPE_MOVIE))
                    listKindOfMovies.add(KindOfMovies("Tv Popular", t3, KindOfMovies.TYPE_TV))
                    listKindOfMovies.add(KindOfMovies("Tv Top Rated", t4, KindOfMovies.TYPE_TV))
                    return@Function4 listKindOfMovies
                })
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it != null){
                        apiCallback.onDataLoaded(it)
                    }else{
                        apiCallback.onDataEmpty()
                    }
                },{
                    apiCallback.onError("${it.message}")
                })


    }

}