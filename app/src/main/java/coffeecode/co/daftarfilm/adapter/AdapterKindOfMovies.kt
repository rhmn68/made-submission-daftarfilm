package coffeecode.co.daftarfilm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.database.MovieHelper
import coffeecode.co.daftarfilm.features.detail.MovieDetailActivity
import coffeecode.co.daftarfilm.features.seeall.SeeAllActivity
import coffeecode.co.daftarfilm.model.kindofmovies.KindOfMovies
import coffeecode.co.daftarfilm.model.movie.MovieResponse
import kotlinx.android.synthetic.main.item_kind_of_movies.view.*
import org.jetbrains.anko.startActivity

class AdapterKindOfMovies(private val context: Context, private val movieHelper: MovieHelper)
    : RecyclerView.Adapter<AdapterKindOfMovies.ViewHolder>(){

    private val listKindOfMovies = mutableListOf<KindOfMovies>()

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
        holder.bindItem(context, listKindOfMovies[position], movieHelper)
    }

    fun setData(dataKindOfMovies: List<KindOfMovies>?){
        if (dataKindOfMovies != null){
            if (listKindOfMovies.size > 0){
                listKindOfMovies.clear()
            }
            listKindOfMovies.addAll(dataKindOfMovies)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private lateinit var adapterListMovieFromKindOfMovies: AdapterListMovieFromKindOfMovies
        private val snapHelper = PagerSnapHelper()

        fun bindItem(
            context: Context,
            itemKindOfMovies: KindOfMovies,
            movieHelper: MovieHelper
        ) {
            itemView.tvTittleKindOfMovies.text = itemKindOfMovies.tittle

            setAdapterListMoviesFromKindOfMovies(itemKindOfMovies.movieResponse, context, movieHelper)
            onClick(context, itemKindOfMovies)
        }

        private fun setAdapterListMoviesFromKindOfMovies(
            movieResponse: MovieResponse?,
            context: Context,
            movieHelper: MovieHelper
        ) {
            adapterListMovieFromKindOfMovies =
                AdapterListMovieFromKindOfMovies(context, movieHelper) {
                    if (it.originalTitle != null){
                        context.startActivity<MovieDetailActivity>(
                            MovieDetailActivity.KEY_MOVIE_RESPONSE to it,
                            MovieDetailActivity.KEY_IS_MOVIE to true)
                    }else{
                        context.startActivity<MovieDetailActivity>(
                            MovieDetailActivity.KEY_MOVIE_RESPONSE to it,
                            MovieDetailActivity.KEY_IS_MOVIE to false)
                    }

                }
            adapterListMovieFromKindOfMovies.listMovies = movieResponse?.movies!!

            itemView.rvListMoviesFromKindOfMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            itemView.rvListMoviesFromKindOfMovies.adapter = adapterListMovieFromKindOfMovies

            snapHelper.attachToRecyclerView(itemView.rvListMoviesFromKindOfMovies)
        }

        private fun onClick(
            context: Context,
            itemKindOfMovies: KindOfMovies
        ) {
            itemView.tvSeeAllKindOfMovies.setOnClickListener {
                context.startActivity<SeeAllActivity>(
                    SeeAllActivity.KEY_TITTLE to itemKindOfMovies.tittle,
                    SeeAllActivity.KEY_MOVIE_RESPONSE to itemKindOfMovies.movieResponse
                )
            }
        }
    }
}