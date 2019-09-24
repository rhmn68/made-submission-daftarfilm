package coffeecode.co.daftarfilm.features.main.subfeatures.search


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.adapter.AdapterVerticalListMovies
import kotlinx.android.synthetic.main.fragment_search.*
import org.jetbrains.anko.support.v4.toast

class SearchFragment : Fragment() {

    private lateinit var adapterVerticalListMovies: AdapterVerticalListMovies

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setAdapterSearchMovie()
    }

    private fun setAdapterSearchMovie() {
        adapterVerticalListMovies = AdapterVerticalListMovies {
            toast("Hello")
        }
        adapterVerticalListMovies.notifyDataSetChanged()

        rvSearchMovie.layoutManager = LinearLayoutManager(activity)
        rvSearchMovie.adapter = adapterVerticalListMovies
    }

}
