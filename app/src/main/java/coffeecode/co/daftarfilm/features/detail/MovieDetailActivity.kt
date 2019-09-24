package coffeecode.co.daftarfilm.features.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.adapter.AdapterCast
import coffeecode.co.daftarfilm.model.credits.CreditsResponse
import coffeecode.co.daftarfilm.model.detail.MovieDetailResponse
import coffeecode.co.daftarfilm.model.movie.Movies
import coffeecode.co.daftarfilm.model.video.VideoResponse
import coffeecode.co.daftarfilm.storage.HawkStorage
import coffeecode.co.daftarfilm.utils.Config
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
    }

    private lateinit var adapterCast: AdapterCast
    private lateinit var fragYoutube: YouTubePlayerSupportFragment
    private var movieDetailViewModel : MovieDetailViewModel? = null
    private val hawkStorage = HawkStorage(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        initToolbar()
        getDataIntent()
        onClick()
    }

    private fun getDataIntent() {
        val movie = intent.getSerializableExtra(KEY_MOVIE_RESPONSE) as Movies

        getAllDataMovieDetail(movie)
    }

    private fun getAllDataMovieDetail(movie: Movies?) {
        movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel::class.java).apply {
            getDataDetailMovie(movie?.id)?.observe(this@MovieDetailActivity, Observer {
                initView(it)
            })

            getDataCreditsMovie(movie?.id)?.observe(this@MovieDetailActivity, Observer {
                initCredits(it)
                setAdapterCast(it)
            })

            getDataVideo(movie?.id)?.observe(this@MovieDetailActivity, Observer {
                initYoutubeView(it)
            })
        }

    }

    private fun initCredits(creditsResponse: CreditsResponse?) {
        val featureCrews = arrayOf("Writer", "Story", "Screenplay")

        for (i in creditsResponse?.crew?.indices!!){
            if (creditsResponse.crew[i]?.job == "Director"){
                tvDirectorDetailMovie.text = creditsResponse.crew[i]?.name
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

    private fun initToolbar() {
        setSupportActionBar(tbMovieDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initYoutubeView(videoResponse: VideoResponse) {
        fragYoutube = supportFragmentManager.findFragmentById(R.id.youtubeView) as YouTubePlayerSupportFragment

        fragYoutube.initialize(Config.YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
                if(!wasRestored){
                    if (videoResponse.results!!.isNotEmpty()){
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

    private fun setAdapterCast(creditsResponse: CreditsResponse?) {
        adapterCast = AdapterCast(this)
        adapterCast.setData(creditsResponse)

        rvCast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCast.adapter = adapterCast
    }
}
