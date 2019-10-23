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
import coffeecode.co.daftarfilm.features.detail.MovieDetailActivity
import coffeecode.co.daftarfilm.helper.MappingHelper
import coffeecode.co.daftarfilm.model.kindofmovies.KindOfMovies
import coffeecode.co.daftarfilm.model.movie.Movies
import coffeecode.co.daftarfilm.model.moviedb.MovieDbModel
import kotlinx.android.synthetic.main.fragment_favorite_tv.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class FavoriteTvFragment : Fragment() {

    companion object{
        private const val EXTRA_STATE_TV = "EXTRA_STATE_TV"
    }

    private lateinit var adapterVerticalListMovies: AdapterVerticalListMovies
    private lateinit var movieHelper: MovieHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDatabase()
        initAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_favorite_tv, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null){
            getDataTvFromDb()
        }else{
            val list = savedInstanceState.getParcelableArrayList<Movies>(EXTRA_STATE_TV)
            if (list != null){
                adapterVerticalListMovies.listMovies = list
            }
        }
    }

    private fun initAdapter() {
        adapterVerticalListMovies = AdapterVerticalListMovies(ctx, KindOfMovies.TYPE_TV, movieHelper){
            startActivity<MovieDetailActivity>(
                MovieDetailActivity.KEY_MOVIE_RESPONSE to it,
                MovieDetailActivity.KEY_IS_MOVIE to false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE_TV, adapterVerticalListMovies.listMovies)
    }

    override fun onDestroy() {
        super.onDestroy()
        movieHelper.close()
    }

    private fun getDataTvFromDb() {
        GlobalScope.launch(Dispatchers.Main) {
            val tv = async(Dispatchers.IO) {
                val cursor = movieHelper.queryAllTv()
                MappingHelper.mapCursorTvToArrayList(cursor)
            }

            val dataTv = tv.await()
            val listTv = ArrayList<Movies>()

            listTv.clear()

            if (dataTv.size > 0){
                for (data: MovieDbModel in dataTv){
                    data.movies?.let { listTv.add(it) }
                }
                adapterVerticalListMovies.listMovies = listTv

                rvFavTv.layoutManager = LinearLayoutManager(ctx)
                rvFavTv.adapter = adapterVerticalListMovies

                rvFavTv.visibility = View.VISIBLE
                tvDataNull.visibility = View.GONE
            }else{
                rvFavTv.visibility = View.GONE
                tvDataNull.visibility = View.VISIBLE
            }
        }
    }

    private fun initDatabase() {
        movieHelper = MovieHelper.getInstance(activity!!)
        movieHelper.open()
    }

}
