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
import kotlinx.android.synthetic.main.item_kind_of_movies.view.*
import org.jetbrains.anko.startActivity

class AdapterKindOfMovies(private val context: Context) : RecyclerView.Adapter<AdapterKindOfMovies.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_kind_of_movies,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(context)
    }

    class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        private lateinit var adapterListMovieFromKindOfMovies: AdapterListMovieFromKindOfMovies
        private val snapHelper = PagerSnapHelper()

        fun bindItem(context: Context) {
            setAdapterListMoviesFromKindOfMovies(context)
        }

        private fun setAdapterListMoviesFromKindOfMovies(context: Context) {
            adapterListMovieFromKindOfMovies =
                AdapterListMovieFromKindOfMovies {
                    context.startActivity<MovieDetailActivity>()
                }
            adapterListMovieFromKindOfMovies.notifyDataSetChanged()

            itemView.rvListMoviesFromKindOfMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            itemView.rvListMoviesFromKindOfMovies.adapter = adapterListMovieFromKindOfMovies
            snapHelper.attachToRecyclerView(itemView.rvListMoviesFromKindOfMovies)
        }
    }
}