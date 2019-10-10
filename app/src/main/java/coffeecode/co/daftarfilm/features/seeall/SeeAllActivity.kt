package coffeecode.co.daftarfilm.features.seeall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.adapter.AdapterVerticalListMovies
import coffeecode.co.daftarfilm.database.MovieHelper
import coffeecode.co.daftarfilm.features.detail.MovieDetailActivity
import coffeecode.co.daftarfilm.model.movie.MovieResponse
import coffeecode.co.daftarfilm.model.movie.Movies
import coffeecode.co.daftarfilm.viewmodel.SeeAllViewModel
import kotlinx.android.synthetic.main.activity_see_all.*
import org.jetbrains.anko.startActivity

class SeeAllActivity : AppCompatActivity() {

    companion object{
        const val KEY_MOVIE_RESPONSE = "KEY_MOVIE_RESPONSE"
        const val KEY_TITTLE = "KEY_TITTLE"
    }

    private lateinit var adapterVerticalListMovies: AdapterVerticalListMovies
    private var seeAllViewModel: SeeAllViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_all)

        getDataIntent()
        initSwipeRefresh()
    }

    private fun initSwipeRefresh() {
        swipeRefreshSeeAll.setOnRefreshListener {
            swipeRefreshSeeAll.isRefreshing = false
            getDataIntent()
        }
    }

    private fun getDataIntent() {
        val movieResponse = intent.getSerializableExtra(KEY_MOVIE_RESPONSE) as MovieResponse
        val tittle = intent.getStringExtra(KEY_TITTLE)

        getAllDataViewModel(movieResponse)
        initToolbar(tittle)
        onClick()
    }

    private fun getAllDataViewModel(movieResponse: MovieResponse) {
        seeAllViewModel = ViewModelProviders.of(this).get(SeeAllViewModel::class.java).apply {
            getDataIsLoading()?.observe(this@SeeAllActivity, Observer { isLoading ->
                if (isLoading){
                    showLoading()
                }else{
                    hideLoading()
                }
            })

            getDataMovieResponse(movieResponse)?.observe(this@SeeAllActivity, Observer {
                setAdapterMovie(it)
            })
        }
    }

    private fun hideLoading() {
        swipeRefreshSeeAll.isRefreshing = false
    }

    private fun showLoading() {
        swipeRefreshSeeAll.isRefreshing = true
    }

    private fun onClick() {
        tbSeeAll.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setAdapterMovie(movieResponse: MovieResponse) {
        val movieHelper = MovieHelper.getInstance(this)
        movieHelper.open()

        if (movieResponse.movies != null){
            adapterVerticalListMovies = AdapterVerticalListMovies(this){
                if (it.originalTitle != null){
                    startActivity<MovieDetailActivity>(
                        MovieDetailActivity.KEY_MOVIE_RESPONSE to it,
                        MovieDetailActivity.KEY_IS_MOVIE to true)
                }else{
                    startActivity<MovieDetailActivity>(
                        MovieDetailActivity.KEY_MOVIE_RESPONSE to it,
                        MovieDetailActivity.KEY_IS_MOVIE to false)
                }
            }
            adapterVerticalListMovies.addMovieHelper(movieHelper)
            adapterVerticalListMovies.listMovies = movieResponse.movies!!

            rvSeeAll.layoutManager = LinearLayoutManager(this)
            rvSeeAll.adapter = adapterVerticalListMovies
        }
    }

    private fun initToolbar(tittle: String?) {
        setSupportActionBar(tbSeeAll)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tbSeeAll.title = tittle
    }
}
