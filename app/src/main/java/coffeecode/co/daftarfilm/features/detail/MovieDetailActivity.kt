package coffeecode.co.daftarfilm.features.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.adapter.AdapterCast
import coffeecode.co.daftarfilm.model.credits.CreditsResponse
import coffeecode.co.daftarfilm.model.detail.MovieDetailResponse
import coffeecode.co.daftarfilm.model.movie.Movies
import coffeecode.co.daftarfilm.model.tvdetail.TvDetailResponse
import coffeecode.co.daftarfilm.model.video.VideoResponse
import coffeecode.co.daftarfilm.storage.HawkStorage
import coffeecode.co.daftarfilm.utils.Config
import coffeecode.co.daftarfilm.viewmodel.MovieDetailViewModel
import coffeecode.co.daftarfilm.viewmodel.TvDetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_detail_movie.*
import org.jetbrains.anko.toast
import java.lang.StringBuilder

class MovieDetailActivity : AppCompatActivity(){
    companion object{
        private const val RECOVERY_REQUEST = 1
        const val KEY_MOVIE_RESPONSE = "KEY_MOVIE_RESPONSE"
        const val KEY_IS_MOVIE = "KEY_IS_MOVIE"
    }

    private lateinit var adapterCast: AdapterCast
    private lateinit var fragYoutube: YouTubePlayerSupportFragment
    private var movieDetailViewModel : MovieDetailViewModel? = null
    private var tvDetailViewModel: TvDetailViewModel? = null
    private val hawkStorage = HawkStorage(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        getDataIntent()
        onClick()
    }

    private fun getDataIntent() {
        val movie = intent.getSerializableExtra(KEY_MOVIE_RESPONSE) as Movies
        val isMovie = intent.getBooleanExtra(KEY_IS_MOVIE, false)

        if (isMovie){
            getAllDataMovieDetail(movie)
            initToolbar(getString(R.string.movie))
        }else{
            getAllDataTvDetail(movie)
            initToolbar(getString(R.string.television))
        }
    }

    private fun getAllDataMovieDetail(movie: Movies?) {
        movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel::class.java).apply {
            getDataDetailMovie(movie?.id)?.observe(this@MovieDetailActivity, Observer {
                initView(it)
            })

            getDataCreditsMovie(movie?.id)?.observe(this@MovieDetailActivity, Observer {
                if (it != null){
                    setAdapterCast(it)
                    initCredits(it)
                }
            })

            getDataVideo(movie?.id)?.observe(this@MovieDetailActivity, Observer {
                initYoutubeView(it)
            })

            getIsLoading()?.observe(this@MovieDetailActivity, Observer { isLoading ->
                if (isLoading){
                    showLoading()
                }else{
                    hideLoading()
                }
            })
        }

    }

    private fun getAllDataTvDetail(movie: Movies) {
        tvDetailViewModel = ViewModelProviders.of(this).get(TvDetailViewModel::class.java).apply {
            getIsLoading()?.observe(this@MovieDetailActivity, Observer {isLoading ->
                if (isLoading){
                    showLoading()
                }else{
                    hideLoading()
                }
            })

            getDataDetailTv(movie.id)?.observe(this@MovieDetailActivity, Observer {
                if(it != null){
                    initTvDetailView(it)
                }
            })

            getDataCreditsTv(movie.id)?.observe(this@MovieDetailActivity, Observer {
                if (it != null){
                    setAdapterCast(it)
                    initCredits(it)
                }
            })

            getDataVideoTv(movie.id)?.observe(this@MovieDetailActivity, Observer {
                initYoutubeView(it)
            })
        }
    }

    private fun initTvDetailView(tvDetailResponse: TvDetailResponse?) {
        val secureBaseUrl = hawkStorage.getImageConfig().images?.secureBaseUrl
        val backDropSize = hawkStorage.getImageConfig().images?.backdropSizes?.get(1)
        val posterSize = hawkStorage.getImageConfig().images?.posterSizes?.get(1)

        val urlImageBackdrop = secureBaseUrl + backDropSize + tvDetailResponse?.backdropPath
        val urlImagePoster = secureBaseUrl + posterSize + tvDetailResponse?.posterPath

        tvTittleDetailMovie.text = tvDetailResponse?.originalName

        tvOverviewDetailMovie.text = tvDetailResponse?.overview

        Glide.with(this).load(urlImageBackdrop).into(ivBackDropMovieDetail)

        Glide.with(this).load(urlImagePoster).into(ivPosterDetailMovie)

        Glide.with(this).load(urlImagePoster)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
            .into(ivPosterBlurMovieDetail)

        val allGenre = StringBuilder()
        for (genre in tvDetailResponse?.genres!!){
            allGenre.append("${genre?.name}, ")
        }

        tvGenre.text = allGenre
    }

    private fun hideLoading() {
        pbDetailMovie.visibility = View.GONE
    }

    private fun showLoading() {
        pbDetailMovie.visibility = View.VISIBLE
    }

    private fun initCredits(creditsResponse: CreditsResponse?) {
        val featureCrews = arrayOf("Writer", "Story", "Screenplay")

        for (i in creditsResponse?.crew?.indices!!){
            if (creditsResponse.crew[i]?.job == "Director"){
                tvDirectorDetailMovie.text = creditsResponse.crew[i]?.name
                tvJobDirector.text = creditsResponse.crew[i]?.job
            }
            for (featureCrew in featureCrews){
                if (creditsResponse.crew[i]?.job == featureCrew){
                    tvFeatureCrewDetailMovie.text = creditsResponse.crew[i]?.name
                    tvJobFeatureCrewDetailMovie.text = creditsResponse.crew[i]?.job
                }
            }
        }
    }

    private fun onClick() {
        tbMovieDetail.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initView(movieDetailResponse: MovieDetailResponse) {
        val secureBaseUrl = hawkStorage.getImageConfig().images?.secureBaseUrl
        val backDropSize = hawkStorage.getImageConfig().images?.backdropSizes?.get(1)
        val posterSize = hawkStorage.getImageConfig().images?.posterSizes?.get(1)

        val urlImageBackdrop = secureBaseUrl + backDropSize + movieDetailResponse.backdropPath
        val urlImagePoster = secureBaseUrl + posterSize + movieDetailResponse.posterPath

        tvTittleDetailMovie.text = movieDetailResponse.originalTitle

        tvOverviewDetailMovie.text = movieDetailResponse.overview

        Glide.with(this).load(urlImageBackdrop).into(ivBackDropMovieDetail)

        Glide.with(this).load(urlImagePoster).into(ivPosterDetailMovie)

        Glide.with(this).load(urlImagePoster)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
            .into(ivPosterBlurMovieDetail)

        val allGenre = StringBuilder()
        for (genre in movieDetailResponse.genres!!){
            allGenre.append("${genre?.name}, ")
        }

        tvGenre.text = allGenre
    }

    private fun initToolbar(tittle: String) {
        setSupportActionBar(tbMovieDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tbMovieDetail.title = tittle
    }

    private fun initYoutubeView(videoResponse: VideoResponse) {
        fragYoutube = supportFragmentManager.findFragmentById(R.id.youtubeView) as YouTubePlayerSupportFragment

        if (videoResponse.results != null){
            fragYoutube.initialize(Config.YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener{
                override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
                    if(!wasRestored){
                        if (videoResponse.results.isNotEmpty()){
                            player?.cueVideo(videoResponse.results[0]?.key)
                        }
                    }
                }

                override fun onInitializationFailure(provider: YouTubePlayer.Provider?, errorReason: YouTubeInitializationResult?) {
                    if (errorReason!!.isUserRecoverableError) {
                        errorReason.getErrorDialog(this@MovieDetailActivity, RECOVERY_REQUEST).show()
                    } else {
                        toast("Error youtube")
                    }
                }

            })
        }
    }

    private fun setAdapterCast(creditsResponse: CreditsResponse?) {
        if (creditsResponse != null){
            adapterCast = AdapterCast(this)
            adapterCast.setData(creditsResponse)

            rvCast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            rvCast.adapter = adapterCast
        }
    }
}
