package coffeecode.co.daftarfilm.features.main.subfeatures.home

import coffeecode.co.daftarfilm.BuildConfig
import coffeecode.co.daftarfilm.networking.ApiServices
import coffeecode.co.daftarfilm.view.DataSource
import io.reactivex.schedulers.Schedulers

class HomeRepo {
    fun getDataNowPlaying(movieApiCallback: DataSource.MoviesApiCallback){
        ApiServices.getMovieServices()
            .getMovieNowPlaying(BuildConfig.TOKEN_MOVIE_DB)
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

    fun getDataMoviePopular(movieApiCallback: DataSource.MoviesApiCallback){
        ApiServices.getMovieServices()
            .getMoviePopular(BuildConfig.TOKEN_MOVIE_DB)
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
}