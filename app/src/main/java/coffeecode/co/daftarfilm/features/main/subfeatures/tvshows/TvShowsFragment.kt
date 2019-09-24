package coffeecode.co.daftarfilm.features.main.subfeatures.tvshows


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.adapter.AdapterKindOfMovies
import coffeecode.co.daftarfilm.features.main.subfeatures.viewmodel.MovieViewModel
import coffeecode.co.daftarfilm.model.kindofmovies.KindOfMovies
import kotlinx.android.synthetic.main.fragment_tv_shows.*

class TvShowsFragment : Fragment() {

    private lateinit var adapterKindOfMovies: AdapterKindOfMovies
    private var movieViewModel: MovieViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_tv_shows, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllDataTvShows()
        initSwipeRefreshTvShows()
    }

    private fun initSwipeRefreshTvShows() {
        swipeRefreshTvShows.setOnRefreshListener {
            swipeRefreshTvShows.isRefreshing = false
            getAllDataTvShows()
        }
    }

    private fun getAllDataTvShows() {
        movieViewModel = ViewModelProviders.of(activity!!).get(MovieViewModel::class.java).apply {
            getDataKindOfMoviesForTvShows()?.observe(this@TvShowsFragment, Observer {
                if (it != null){
                    setAdapterTvShowsList(it)
                }
            })

            getIsLoading()?.observe(this@TvShowsFragment, Observer {isLoading ->
                if (isLoading){
                    showLoading()
                }else{
                    hideLoading()
                }
            })
        }
    }

    private fun showLoading(){
        swipeRefreshTvShows.isRefreshing = true
    }

    private fun hideLoading(){
        swipeRefreshTvShows.isRefreshing = false
    }

    private fun setAdapterTvShowsList(listKindOfMovies: List<KindOfMovies>) {
        adapterKindOfMovies = AdapterKindOfMovies(activity!!)
        adapterKindOfMovies.setData(listKindOfMovies)

        rvTvShowsList.layoutManager = LinearLayoutManager(activity)
        rvTvShowsList.adapter = adapterKindOfMovies
    }
}
