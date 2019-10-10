package coffeecode.co.daftarfilm.features.main.favorite


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.adapter.AdapterVerticalListMovies
import coffeecode.co.daftarfilm.database.MovieHelper
import coffeecode.co.daftarfilm.datasource.DataSource
import coffeecode.co.daftarfilm.features.detail.MovieDetailActivity
import coffeecode.co.daftarfilm.model.movie.MovieResponse
import coffeecode.co.daftarfilm.model.movie.Movies
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.toast

class FavoriteFragment : Fragment() {

    private lateinit var adapterVerticalListMovies: AdapterVerticalListMovies
    private lateinit var movieHelper: MovieHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_favorite, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDatabase()
        setAdapterSearchMovie()
    }

    private fun initDatabase() {
        movieHelper = MovieHelper.getInstance(activity!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        movieHelper.close()
    }

    private fun setAdapterSearchMovie() {
        movieHelper.open()
        val dataMovies = DataSource(activity!!).getAllDataMovieFromSql()
        val listMovie = ArrayList<Movies?>()
        for (i in dataMovies?.indices!!){
            dataMovies[i].movies?.let { listMovie.add(it) }
        }
        adapterVerticalListMovies = AdapterVerticalListMovies(activity!!) {
            if (it.originalTitle != null){
                context?.startActivity<MovieDetailActivity>(
                    MovieDetailActivity.KEY_MOVIE_RESPONSE to it,
                    MovieDetailActivity.KEY_IS_MOVIE to true)
            }else{
                context?.startActivity<MovieDetailActivity>(
                    MovieDetailActivity.KEY_MOVIE_RESPONSE to it,
                    MovieDetailActivity.KEY_IS_MOVIE to false)
            }
        }
        adapterVerticalListMovies.listMovies = listMovie
        adapterVerticalListMovies.addMovieHelper(movieHelper)
        adapterVerticalListMovies.notifyDataSetChanged()

        rvFavMovie.layoutManager = LinearLayoutManager(activity)
        rvFavMovie.adapter = adapterVerticalListMovies
    }

}
