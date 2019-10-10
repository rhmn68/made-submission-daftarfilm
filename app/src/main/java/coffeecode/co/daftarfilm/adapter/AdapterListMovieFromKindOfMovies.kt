package coffeecode.co.daftarfilm.adapter

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.database.DatabaseContract
import coffeecode.co.daftarfilm.database.MovieHelper
import coffeecode.co.daftarfilm.datasource.DataSource
import coffeecode.co.daftarfilm.helper.MappingHelper
import coffeecode.co.daftarfilm.model.movie.Movies
import coffeecode.co.daftarfilm.storage.HawkStorage
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.item_list_movies_from_kind_of_movies.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import java.text.DecimalFormat

class AdapterListMovieFromKindOfMovies(private val context: Context,
                                       private val movieHelper: MovieHelper,
                                       private val listener: (Movies) -> Unit)
    : RecyclerView.Adapter<AdapterListMovieFromKindOfMovies.ViewHolder>(){

    var listMovies = ArrayList<Movies?>()
        set(listMovies){
            if (this.listMovies.size > 0){
                this.listMovies.clear()
            }
            this.listMovies.addAll(listMovies)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.item_list_movies_from_kind_of_movies, parent, false)
        )

    override fun getItemCount(): Int = this.listMovies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(this.listMovies[position],position, listener)
    }

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){

        fun bindItem(
            movie: Movies?,
            position: Int,
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

                checkFavoriteFromDb(movie)
                onClick(movie, position)
                view.setOnClickListener { listener(movie) }
            }
        }

        fun checkFavorite(movie: Movies) {
            if (movie.isFavorite!!){
                itemView.btnFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_primary_24dp))
            }else{
                itemView.btnFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_border_primary_24dp))
            }
        }

        private fun checkFavoriteFromDb(movie: Movies) {
            GlobalScope.launch(Dispatchers.Main) {
                val movies = async(Dispatchers.IO) {
                    val cursor = movieHelper.queryAll()
                    MappingHelper.mapCursorMovieToArrayList(cursor)
                }
                val dataMovies = movies.await()
                if (dataMovies.size > 0){
                    for (i in dataMovies.indices){
                        if (dataMovies[i].movies?.id == movie.id){
                            movie.isFavorite = dataMovies[i].movies?.isFavorite
                            checkFavorite(movie)
                        }
                    }
                }else{
                    checkFavorite(movie)
                }
            }
        }

        private fun onClick(movie: Movies, position: Int) {
            itemView.btnFav.setOnClickListener {
                if (movie.isFavorite!!){
                    movie.isFavorite = false
                    deleteFromDatabaseById(movie, position)
                }else{
                    movie.isFavorite = true
                    addToDatabase(movie)
                }
                notifyDataSetChanged()
            }
        }

        private fun deleteFromDatabaseById(movie: Movies, position: Int) {
            GlobalScope.launch(Dispatchers.Main) {
                val movies = async(Dispatchers.IO) {
                    val cursor = movieHelper.queryAll()
                    MappingHelper.mapCursorMovieToArrayList(cursor)
                }
                val dataMovies = movies.await()

                if (dataMovies.size > 0){
                    for (i in dataMovies.indices){
                        if (dataMovies[i].movies?.id == movie.id){
                            val result = movieHelper.deleteById(dataMovies[i].id.toString()).toLong()
                            if (result > 0){
                                context.toast(context.getString(R.string.success_remove_favorite))
                                checkFavoriteFromDb(movie)
                            }else{
                                context.toast(context.getString(R.string.failed_remove_favorite))
                            }
                        }
                    }
                }
            }
        }

        private fun addToDatabase(movie: Movies){
            val values = ContentValues()
            val movieString = Gson().toJson(movie)
            values.put(DatabaseContract.MovieColumns.MOVIE_RESPONSE, movieString)

            val result = movieHelper.insert(values)
            if (result > 0){
                context.toast(context.getString(R.string.success_add_favorite))
                checkFavoriteFromDb(movie)
            }else{
                context.toast(context.getString(R.string.failed_add_favorite))
            }
        }
    }
}