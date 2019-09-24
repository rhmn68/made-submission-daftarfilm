package coffeecode.co.daftarfilm.features.main.subfeatures.movies


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
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment() {

    private lateinit var adapterKindOfMovies: AdapterKindOfMovies
    private var movieViewModel: MovieViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllDataMoviesViewModel()
        initSwipeRefreshMovies()
    }

    private fun initSwipeRefreshMovies() {
        swipeRefreshMovies.setOnRefreshListener {
            swipeRefreshMovies.isRefreshing = false
            getAllDataMoviesViewModel()
        }
    }

    private fun getAllDataMoviesViewModel() {
        movieViewModel = ViewModelProviders.of(activity!!).get(MovieViewModel::class.java).apply {
            getDataKindOfMoviesForMovies()?.observe(this@MoviesFragment, Observer {
                if (it != null){
                    setAdapterMoviesList(it)
                }
            })

            getIsLoading()?.observe(this@MoviesFragment, Observer {isLoading ->
                if (isLoading){
                    showLoading()
                }else{
                    hideLoading()
                }
            })
        }
    }

    private fun showLoading(){
        swipeRefreshMovies.isRefreshing = true
    }

    private fun hideLoading(){
        swipeRefreshMovies.isRefreshing = false
    }

    private fun setAdapterMoviesList(listKindOfMovies: List<KindOfMovies>) {
        adapterKindOfMovies = AdapterKindOfMovies(activity!!)
        adapterKindOfMovies.setData(listKindOfMovies)

        rvMoviesList.layoutManager = LinearLayoutManager(activity)
        rvMoviesList.adapter = adapterKindOfMovies
    }

}
