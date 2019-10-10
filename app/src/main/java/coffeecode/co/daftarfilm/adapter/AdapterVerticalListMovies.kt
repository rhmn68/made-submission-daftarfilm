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
import kotlinx.android.synthetic.main.item_vertical_list_movies.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import java.text.DecimalFormat

class AdapterVerticalListMovies(private val context: Context,
                                private val listener: (Movies) -> Unit)
    : RecyclerView.Adapter<AdapterVerticalListMovies.ViewHolder>(){

    private var movieHelper: MovieHelper? = null
    var listMovies = ArrayList<Movies?>()
        set(listMovies){
            if (listMovies.size > 0){
                this.listMovies.clear()
            }
            this.listMovies.addAll(listMovies)
            notifyDataSetChanged()
        }

    private lateinit var adapterListMovieFromKindOfMovies: AdapterListMovieFromKindOfMovies

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.item_vertical_list_movies, parent, false)
        )

    override fun getItemCount(): Int = this.listMovies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(context, this.listMovies[position], position , listener)
    }

    fun addMovieHelper(movieHelper: MovieHelper){
        this.movieHelper = movieHelper
    }

    fun removeItem(position: Int){
        this.listMovies.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listMovies.size)
    }

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        private val dataMovies = DataSource(context).getAllDataMovieFromSql()

        fun bindItem(
            context: Context,
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

                if (movie.originalTitle != null){
                    itemView.tvTittleMovie.text = movie.originalTitle
                }else{
                    itemView.tvTittleMovie.text = movie.originalName
                }

                if (movie.releaseDate != null){
                    itemView.tvReleaseDate.text = movie.releaseDate
                }else{
                    itemView.tvReleaseDate.text = movie.firstAirDate
                }

                for (element in movie.genreIds!!){
                    for (j in hawkStorage.getGenres().genres!!.indices){
                        if (hawkStorage.getGenres().genres!![j]?.id == element){
                            itemView.tvGenre.text = hawkStorage.getGenres().genres!![j]?.name
                        }
                    }
                }

                itemView.ratingBarKindOfMovie.rating = movie.movieRate()

                itemView.tvTotalRating.text = decimalFormat.format(movie.movieRate())

                itemView.tvVoteCount.text = movie.voteCount.toString()

                checkFavoriteFromDb(movie)
                checkFavorite(movie)
                onClick(movie, position)
                view.setOnClickListener { listener(movie) }
            }
        }

        private fun onClick(movie: Movies, position: Int) {
            adapterListMovieFromKindOfMovies = AdapterListMovieFromKindOfMovies(context, movieHelper!!){

            }

            itemView.btnFav.setOnClickListener {
                if (movie.isFavorite!!){
                    movie.isFavorite = false
                    deleteFromDatabaseById(movie)
                    removeItem(position)
                }else{
                    movie.isFavorite = true
                    addToDatabase(movie)
                }
                checkFavorite(movie)
            }
        }

        private fun checkFavoriteFromDb(movie: Movies) {
            if (dataMovies?.size!! > 0){
                for (i in dataMovies.indices){
                    if (dataMovies[i].movies?.id == movie.id){
                        movie.isFavorite = dataMovies[i].movies?.isFavorite
                    }
                }
            }
        }

        private fun checkFavorite(movie: Movies) {
            if (movie.isFavorite!!){
                itemView.btnFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_primary_24dp))
            }else{
                itemView.btnFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_border_primary_24dp))
            }
        }

        private fun deleteFromDatabaseById(movie: Movies) {
            GlobalScope.launch(Dispatchers.Main) {
                val movies = async(Dispatchers.IO) {
                    val cursor = movieHelper?.queryAll()
                    MappingHelper.mapCursorMovieToArrayList(cursor)
                }
                val dataMovies = movies.await()

                if (dataMovies.size > 0){
                    for (i in dataMovies.indices){
                        if (dataMovies[i].movies?.id == movie.id){
                            val result = movieHelper?.deleteById(dataMovies[i].id.toString())?.toLong()
                            if (result!! > 0){
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

            val result = movieHelper?.insert(values)
            if (result!! > 0){
                context.toast(context.getString(R.string.success_add_favorite))
            }else{
                context.toast(context.getString(R.string.failed_add_favorite))
            }
        }
    }
}