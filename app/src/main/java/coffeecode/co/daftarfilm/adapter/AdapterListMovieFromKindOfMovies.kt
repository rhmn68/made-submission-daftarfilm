package coffeecode.co.daftarfilm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.model.movie.MovieResponse
import coffeecode.co.daftarfilm.model.movie.Movies
import coffeecode.co.daftarfilm.storage.HawkStorage
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_list_movies_from_kind_of_movies.view.*
import java.text.DecimalFormat

class AdapterListMovieFromKindOfMovies(private val context: Context, private val movieResponse: MovieResponse?, private val listener: (Movies) -> Unit)
    : RecyclerView.Adapter<AdapterListMovieFromKindOfMovies.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.item_list_movies_from_kind_of_movies, parent, false)
        )

    override fun getItemCount(): Int = movieResponse?.movies!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(context, movieResponse?.movies?.get(position), listener)
    }

    class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        fun bindItem(
            context: Context,
            movie: Movies?,
            listener: (Movies) -> Unit
        ) {
            val hawkStorage = HawkStorage(context)
            val secureBaseUrl = hawkStorage.getImageConfig().images?.secureBaseUrl
            val posterSize = hawkStorage.getImageConfig().images?.posterSizes?.get(1)

            val urlImagePoster = secureBaseUrl + posterSize + movie?.posterPath

            val decimalFormat = DecimalFormat("#.#")

            if (movie != null){
                Glide.with(view).load(urlImagePoster).into(itemView.ivMovie)
                itemView.ratingBarKindOfMovie.rating = movie.movieRate()
                itemView.tvTotalRating.text = decimalFormat.format(movie.movieRate())

                if (movie.releaseDate != null){
                    itemView.tvReleaseDate.text = movie.releaseDate
                }else{
                    itemView.tvReleaseDate.text = movie.firstAirDate
                }

                if (movie.originalTitle != null){
                    itemView.tvTittleMovie.text = movie.originalTitle
                }else{
                    itemView.tvTittleMovie.text = movie.originalName
                }

                for (element in movie.genreIds!!){
                    for (j in hawkStorage.getGenres().genres!!.indices){
                        if (hawkStorage.getGenres().genres!![j]?.id == element){
                            itemView.tvGenre.text = hawkStorage.getGenres().genres!![j]?.name
                        }
                    }
                }

                view.setOnClickListener { listener(movie) }
            }
        }
    }
}