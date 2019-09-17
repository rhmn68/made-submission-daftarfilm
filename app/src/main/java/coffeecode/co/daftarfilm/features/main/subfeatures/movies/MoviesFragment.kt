package coffeecode.co.daftarfilm.features.main.subfeatures.movies


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.adapter.AdapterKindOfMovies
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment() {

    private lateinit var adapterKindOfMovies: AdapterKindOfMovies

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapterMoviesList()
    }

    private fun setAdapterMoviesList() {
        adapterKindOfMovies = AdapterKindOfMovies(activity!!)
        adapterKindOfMovies.notifyDataSetChanged()

        rvMoviesList.layoutManager = LinearLayoutManager(activity)
        rvMoviesList.adapter = adapterKindOfMovies
    }

}
