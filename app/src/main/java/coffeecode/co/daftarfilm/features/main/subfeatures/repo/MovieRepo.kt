package coffeecode.co.daftarfilm.features.main.subfeatures.repo

import coffeecode.co.daftarfilm.BuildConfig
import coffeecode.co.daftarfilm.networking.ApiServices
import coffeecode.co.daftarfilm.view.DataSource
import io.reactivex.schedulers.Schedulers

class MovieRepo {
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

    fun getDataMovieUpComing(movieApiCallback: DataSource.MoviesApiCallback){
        ApiServices.getMovieServices()
            .getMovieUpComing(BuildConfig.TOKEN_MOVIE_DB)
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

    fun getDataMovieTopRated(movieApiCallback: DataSource.MoviesApiCallback){
        ApiServices.getMovieServices()
            .getMovieTopRated(BuildConfig.TOKEN_MOVIE_DB)
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

    fun getDataMovieDiscover(movieApiCallback: DataSource.MoviesApiCallback){
        ApiServices.getMovieServices()
            .getMovieDiscover(BuildConfig.TOKEN_MOVIE_DB)
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

    fun getDataTvPopular(movieApiCallback: DataSource.MoviesApiCallback){
        ApiServices.getTvShowsServices()
            .getTvPopular(BuildConfig.TOKEN_MOVIE_DB)
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

    fun getDataTvTopRated(movieApiCallback: DataSource.MoviesApiCallback){
        ApiServices.getTvShowsServices()
            .getTvTopRated(BuildConfig.TOKEN_MOVIE_DB)
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

    fun getDataTvDiscover(movieApiCallback: DataSource.MoviesApiCallback){
        ApiServices.getTvShowsServices()
            .getTvDiscover(BuildConfig.TOKEN_MOVIE_DB)
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