package coffeecode.co.daftarfilm.features.detail

import coffeecode.co.daftarfilm.BuildConfig
import coffeecode.co.daftarfilm.networking.ApiServices
import coffeecode.co.daftarfilm.view.DataSource
import io.reactivex.schedulers.Schedulers

class MovieDetailRepo {

    fun getDataMovieDetail(movieId: Int, apiCallback: DataSource.MoviesDetailApiCallback){
        ApiServices.getMovieServices()
            .getDetailMovie(movieId, BuildConfig.TOKEN_MOVIE_DB)
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

    fun getDataMovieCredits(movieId: Int, apiCallback: DataSource.MoviesCreditsApiCallback){
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

    fun getDataVideo(movieId: Int, apiCallback: DataSource.MoviesVideoApiCallback){
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
}