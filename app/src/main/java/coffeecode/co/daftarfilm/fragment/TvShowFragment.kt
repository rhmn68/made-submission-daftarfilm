package coffeecode.co.daftarfilm.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.activity.DetailActivity
import coffeecode.co.daftarfilm.adapter.MoviesAdapter
import coffeecode.co.daftarfilm.data.DataMovies
import kotlinx.android.synthetic.main.fragment_tv_show.*
import org.jetbrains.anko.support.v4.startActivity

class TvShowFragment : androidx.fragment.app.Fragment() {

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_tv_show, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter() {
        moviesAdapter = MoviesAdapter(DataMovies(activity).getDataTvShows()){
            startActivity<DetailActivity>(DetailActivity.KEY_DATA_MOVIES to it)
        }
        moviesAdapter.notifyDataSetChanged()

        rvTvShow.layoutManager = LinearLayoutManager(activity)
        rvTvShow.adapter = moviesAdapter
    }
}
