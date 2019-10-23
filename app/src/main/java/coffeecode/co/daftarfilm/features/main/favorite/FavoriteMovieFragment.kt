package coffeecode.co.daftarfilm.features.main.favorite


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.adapter.AdapterVerticalListMovies
import coffeecode.co.daftarfilm.database.MovieHelper
import coffeecode.co.daftarfilm.features.detail.MovieDetailActivity
import coffeecode.co.daftarfilm.helper.MappingHelper
import coffeecode.co.daftarfilm.model.kindofmovies.KindOfMovies
import coffeecode.co.daftarfilm.model.movie.Movies
import coffeecode.co.daftarfilm.model.moviedb.MovieDbModel
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_favorite_movie.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.Android
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

class FavoriteMovieFragment : Fragment() {

    companion object{
        private const val EXTRA_STATE_MOVIE = "EXTRA_STATE_MOVIE"
    }

    private lateinit var movieHelper: MovieHelper
    private lateinit var adapter: AdapterVerticalListMovies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMovieHelper()
        initAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_favorite_movie, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null){
            getDataFromMovieDb()
        }else{
            val list = savedInstanceState.getParcelableArrayList<Movies>(EXTRA_STATE_MOVIE)
            if (list != null){
                adapter.listMovies = list
                showData()
            }else{
                hideData()
            }
        }
    }

    private fun initAdapter() {
        adapter = AdapterVerticalListMovies(ctx, KindOfMovies.TYPE_MOVIE, movieHelper){
            startActivity<MovieDetailActivity>(
                MovieDetailActivity.KEY_MOVIE_RESPONSE to it,
                MovieDetailActivity.KEY_IS_MOVIE to true)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE_MOVIE, adapter.listMovies)
    }

    override fun onDestroy() {
        super.onDestroy()
        movieHelper.close()
    }

    private fun initMovieHelper() {
        movieHelper = MovieHelper.getInstance(activity!!)
        movieHelper.open()
    }

    private fun getDataFromMovieDb() {
        GlobalScope.launch(Dispatchers.Main) {
            val movies = async(Dispatchers.IO) {
                val cursor = movieHelper.queryAllMovie()
                MappingHelper.mapCursorMovieToArrayList(cursor)
            }

            val dataMovies = movies.await()
            val listMovies = ArrayList<Movies>()

            listMovies.clear()

            if (dataMovies.size > 0){
                for (data: MovieDbModel in  dataMovies){
                    data.movies?.let { listMovies.add(it) }
                }

                adapter.listMovies = listMovies

                rvFavMovie.layoutManager = LinearLayoutManager(activity)
                rvFavMovie.adapter = adapter

                showData()
            }else{
                 hideData()
            }
        }
    }

    private fun showData(){
        tvDataNull.visibility = View.GONE
        rvFavMovie.visibility = View.VISIBLE
    }

    private fun hideData(){
        tvDataNull.visibility = View.VISIBLE
        rvFavMovie.visibility = View.GONE
    }
}
