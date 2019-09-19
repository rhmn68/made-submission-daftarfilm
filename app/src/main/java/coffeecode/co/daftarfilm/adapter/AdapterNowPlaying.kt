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
import kotlinx.android.synthetic.main.item_now_playing.view.*

class AdapterNowPlaying(private val context: Context, private val movieResponse: MovieResponse, private val listener: (Movies) -> Unit)
    : RecyclerView.Adapter<AdapterNowPlaying.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_now_playing, parent, false))

    override fun getItemCount(): Int = movieResponse.movies!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(context, movieResponse.movies?.get(position), listener)
    }

    class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        fun bindItem(
            context: Context,
            movie: Movies?,
            listener: (Movies) -> Unit
        ) {
            val hawkStorage = HawkStorage(context)
            val secureBaseUrl = hawkStorage.getImageConfig().images?.secureBaseUrl
            val backDropSize = hawkStorage.getImageConfig().images?.backdropSizes?.get(1)
            val posterSize = hawkStorage.getImageConfig().images?.posterSizes?.get(1)

            val urlImageBackdrop = secureBaseUrl + backDropSize + movie?.backdropPath
            val urlImagePoster = secureBaseUrl + posterSize + movie?.posterPath

            Glide.with(view).load(urlImageBackdrop).into(itemView.ivBackDropNowPlaying)
            Glide.with(view).load(urlImagePoster).into(itemView.ivPosterNowPlaying)
            itemView.tvTittleNowPlaying.text = movie?.originalTitle
            itemView.tvDescNowPlaying.text = movie?.overview

                view.setOnClickListener {
                if (movie != null) {
                    listener(movie)
                }
            }
        }
    }
}