package coffeecode.co.daftarfilm.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.model.MoviesModel
import coffeecode.co.daftarfilm.utils.Utils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.layout_desc_credits.*
import kotlinx.android.synthetic.main.layout_detail_desc.*
import org.jetbrains.anko.startActivity

class DetailActivity : AppCompatActivity() {

    companion object{
        const val KEY_DATA_MOVIES = "KEY_DATA_MOVIES"
    }

    private lateinit var moviesModel: MoviesModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setToolbar()
        getDataIntent()
    }

    private fun getDataIntent() {
        if (intent!=null){
            moviesModel = intent.getParcelableExtra(KEY_DATA_MOVIES)
            setView(moviesModel)
        }
    }

    private fun setView(moviesModel: MoviesModel?) {
        Glide.with(this).load(moviesModel?.imagePoster).into(ivPoster)

        tvTitle.text = moviesModel?.title
        tvYear.text = moviesModel?.year
        tvGenre.text = moviesModel?.genre
        tvTime.text = moviesModel?.time
        tvNameDirector.text = moviesModel?.nameDirector
        tvNameStars.text = moviesModel?.nameStars
        tvDesc.text = moviesModel?.desc

        rbMovies.rating = Utils.convertRating(moviesModel?.rating)

        Log.d("coba", "rating : ${Utils.convertRating(moviesModel?.rating)}")
    }


    private fun setToolbar() {
        setSupportActionBar(toolBar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolBar.setNavigationOnClickListener {
            startActivity<MainActivity>()
        }
    }
}
