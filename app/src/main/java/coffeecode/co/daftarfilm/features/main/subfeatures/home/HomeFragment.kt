package coffeecode.co.daftarfilm.features.main.subfeatures.home


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper

import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.adapter.AdapterNowPlaying
import coffeecode.co.daftarfilm.adapter.AdapterKindOfMovies
import coffeecode.co.daftarfilm.features.detail.MovieDetailActivity
import coffeecode.co.daftarfilm.features.main.subfeatures.MovieViewModel
import coffeecode.co.daftarfilm.model.kindofmovies.KindOfMovies
import coffeecode.co.daftarfilm.model.movie.MovieResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class HomeFragment : Fragment() {

    private lateinit var adapterNowPlaying: AdapterNowPlaying
    private lateinit var adapterKindOfMovies: AdapterKindOfMovies

    private val snapHelper = PagerSnapHelper()
    private var movieViewModel: MovieViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwipeRefreshHome()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getAllDataHomeViewModel()
    }

    private fun showLoading(){
        swipeRefreshHome.isRefreshing = true
    }

    private fun hideLoading(){
        swipeRefreshHome.isRefreshing = false
    }

    private fun getAllDataHomeViewModel() {
        movieViewModel = ViewModelProviders.of(activity!!).get(MovieViewModel::class.java).apply {

            getIsLoading()?.observe(activity!!, Observer {isLoading ->
                toast("loading: $isLoading")
                if (isLoading == true){
                    showLoading()
                }else{
                    hideLoading()
                }
            })

            getDataNowPlaying()?.observe(activity!!, Observer {movieResponse ->
                setAdapterNowPlaying(movieResponse)
            })

            getDataMoviePopular()?.observe(activity!!, Observer { movieResponse ->
                movieViewModel?.setDataKindOfMovies("Movie Popular", movieResponse)
            })

            getDataKindOfMovies()?.observe(activity!!, Observer { listKindOfMovies ->
                setAdapterKindOfMovies(listKindOfMovies)
            })
        }
    }

    private fun initSwipeRefreshHome() {
        swipeRefreshHome.setOnRefreshListener {
            getAllDataHomeViewModel()
            swipeRefreshHome.isRefreshing = false
        }
    }

    private fun setAdapterKindOfMovies(listKindOfMovies: List<KindOfMovies>) {
        adapterKindOfMovies = AdapterKindOfMovies(activity!!, listKindOfMovies)
        adapterKindOfMovies.notifyDataSetChanged()

        rvKindOfMovies.layoutManager = LinearLayoutManager(activity)
        rvKindOfMovies.adapter = adapterKindOfMovies
    }

    private fun setAdapterNowPlaying(movieResponse: MovieResponse) {
        adapterNowPlaying = AdapterNowPlaying(activity!!, movieResponse){
            startActivity<MovieDetailActivity>()
        }
        adapterNowPlaying.notifyDataSetChanged()

        rvNowPlaying.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvNowPlaying.adapter = adapterNowPlaying
        snapHelper.attachToRecyclerView(rvNowPlaying)
        indicatorNowPlaying.attachTo(rvNowPlaying)
    }
}
