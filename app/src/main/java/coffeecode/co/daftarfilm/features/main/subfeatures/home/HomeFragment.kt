package coffeecode.co.daftarfilm.features.main.subfeatures.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper

import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.adapter.AdapterNowPlaying
import coffeecode.co.daftarfilm.adapter.AdapterKindOfMovies
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var adapterNowPlaying: AdapterNowPlaying
    private lateinit var adapterKindOfMovies: AdapterKindOfMovies

    private val snapHelper = PagerSnapHelper()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwipeRefreshHome()
        setAdapterNowPlaying()
        setAdapterKindOfMovies()
    }

    private fun initSwipeRefreshHome() {
        swipeRefreshHome.setOnRefreshListener {
            swipeRefreshHome.isRefreshing = false
        }
    }

    private fun setAdapterKindOfMovies() {
        adapterKindOfMovies = AdapterKindOfMovies(activity!!)
        adapterKindOfMovies.notifyDataSetChanged()

        rvKindOfMovies.layoutManager = LinearLayoutManager(activity)
        rvKindOfMovies.adapter = adapterKindOfMovies
    }

    private fun setAdapterNowPlaying() {
        adapterNowPlaying = AdapterNowPlaying()
        adapterNowPlaying.notifyDataSetChanged()

        rvNowPlaying.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvNowPlaying.adapter = adapterNowPlaying
        snapHelper.attachToRecyclerView(rvNowPlaying)
        indicatorNowPlaying.attachTo(rvNowPlaying)
    }

}
