package coffeecode.co.daftarfilm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.features.detail.MovieDetailActivity
import coffeecode.co.daftarfilm.model.kindofmovies.KindOfMovies
import coffeecode.co.daftarfilm.model.movie.MovieResponse
import kotlinx.android.synthetic.main.item_kind_of_movies.view.*
import org.jetbrains.anko.startActivity

class AdapterKindOfMovies(private val context: Context, private val listKindOfMovies: List<KindOfMovies>)
    : RecyclerView.Adapter<AdapterKindOfMovies.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_kind_of_movies,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = listKindOfMovies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(context, listKindOfMovies[position])
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private lateinit var adapterListMovieFromKindOfMovies: AdapterListMovieFromKindOfMovies
        private val snapHelper = PagerSnapHelper()

        fun bindItem(
            context: Context,
            itemKindOfMovies: KindOfMovies
        ) {
            itemView.tvTittleKindOfMovies.text = itemKindOfMovies.tittle

            setAdapterListMoviesFromKindOfMovies(itemKindOfMovies.movieResponse, context)
        }

        private fun setAdapterListMoviesFromKindOfMovies(
            movieResponse: MovieResponse?,
            context: Context
        ) {
            adapterListMovieFromKindOfMovies =
                AdapterListMovieFromKindOfMovies(context, movieResponse) {
                    context.startActivity<MovieDetailActivity>()
                }
            adapterListMovieFromKindOfMovies.notifyDataSetChanged()

            itemView.rvListMoviesFromKindOfMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            itemView.rvListMoviesFromKindOfMovies.adapter = adapterListMovieFromKindOfMovies
            snapHelper.attachToRecyclerView(itemView.rvListMoviesFromKindOfMovies)
        }
    }
}