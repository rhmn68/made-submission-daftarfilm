package coffeecode.co.daftarfilm.features.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.adapter.AdapterCast
import coffeecode.co.daftarfilm.utils.Config
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_detail_movie.*
import org.jetbrains.anko.toast


@Suppress("CAST_NEVER_SUCCEEDS")
class MovieDetailActivity : AppCompatActivity(), YouTubePlayer.OnInitializedListener {

    companion object{
        private const val RECOVERY_REQUEST = 1
    }

    private lateinit var adapterCast: AdapterCast
    private lateinit var fragYoutube: YouTubePlayerSupportFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        initView()
        setAdapterCast()
        initYoutubeView()
        initToolbar()
        onClick()
    }

    private fun onClick() {
        tbMovieDetail.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initView() {
        Glide.with(this).load(R.drawable.example_poster_w154)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
            .into(ivPosterBlurMovieDetail)
    }

    private fun initToolbar() {
        setSupportActionBar(tbMovieDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initYoutubeView() {
        fragYoutube = supportFragmentManager.findFragmentById(R.id.youtubeView) as YouTubePlayerSupportFragment
        fragYoutube.initialize(Config.YOUTUBE_API_KEY, this)
    }

    private fun setAdapterCast() {
        adapterCast = AdapterCast()
        adapterCast.notifyDataSetChanged()

        rvCast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCast.adapter = adapterCast
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
        if(!wasRestored){
            player?.cueVideo("zqUopiAYdRg")
        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider?, errorReason: YouTubeInitializationResult?) {
        if (errorReason!!.isUserRecoverableError) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show()
        } else {
            toast("Error youtube")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RECOVERY_REQUEST){
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this)
        }
    }

    private fun getYouTubePlayerProvider(): YouTubePlayerSupportFragment {
        return fragYoutube
    }
}
