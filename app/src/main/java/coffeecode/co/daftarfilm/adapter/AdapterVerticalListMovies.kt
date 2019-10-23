package coffeecode.co.daftarfilm.adapter

import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.database.DatabaseContract
import coffeecode.co.daftarfilm.database.MovieHelper
import coffeecode.co.daftarfilm.helper.MappingHelper
import coffeecode.co.daftarfilm.model.kindofmovies.KindOfMovies
import coffeecode.co.daftarfilm.model.movie.Movies
import coffeecode.co.daftarfilm.model.moviedb.MovieDbModel
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
                                private val typeMovie: String,
                                private val movieHelper: MovieHelper,
                                private val listener: (Movies) -> Unit)
    : RecyclerView.Adapter<AdapterVerticalListMovies.ViewHolder>(){

    var listMovies = ArrayList<Movies>()
        set(listMovies){
            if (listMovies.size > 0){
                this.listMovies.clear()
            }
            this.listMovies.addAll(listMovies                                                                               )
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.item_vertical_list_movies, parent, false)
        )

    override fun getItemCount(): Int = this.listMovies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listMovies[position], position, listener)
    }

    fun removeItem(position: Int){
        this.listMovies.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listMovies.size)
    }

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        private val hawkStorage = HawkStorage(context)
        fun bindItem(
                movie: Movies?,
                position: Int,
                listener: (Movies) -> Unit
        ) {

            val secureBaseUrl = hawkStorage.getImageConfig().images?.secureBaseUrl
            val posterSize = hawkStorage.getImageConfig().images?.posterSizes?.get(1)

            val urlImagePoster = secureBaseUrl + posterSize + movie?.posterPath

            val decimalFormat = DecimalFormat("#.#")

            if (movie != null){
                Glide.with(view).load(urlImagePoster).into(itemView.ivMovie)
                setTitleMovie(movie)
                setReleaseMovie(movie)
                setGenresMovie(movie)
                itemView.ratingBarKindOfMovie.rating = movie.movieRate()
                itemView.tvTotalRating.text = decimalFormat.format(movie.movieRate())
                itemView.tvVoteCount.text = movie.voteCount.toString()

                checkMovieOrTv(movie, position)
                view.setOnClickListener { listener(movie) }
            }
        }

        private fun checkMovieOrTv(movie: Movies, position: Int) {
            if (typeMovie == KindOfMovies.TYPE_MOVIE){
                checkFavoriteMovieFromDb(movie, position)
                onClickMovie(movie, position)
            }else{
                checkFavoriteTvFromDb(movie, position)
                onClickTv(movie, position)
            }
        }

        private fun onClickTv(movie: Movies, position: Int) {
            itemView.btnFav.setOnClickListener {
                if (movie.isFavorite!!){
                    movie.isFavorite = false
                    deleteFromDatabaseTvById(movie)
                    removeItem(position)
                }else{
                    movie.isFavorite = true
                    addToTvDatabase(movie)
                }
                checkFavorite(movie)
            }
        }

        private fun checkFavoriteTvFromDb(movie: Movies, position: Int) {
            GlobalScope.launch(Dispatchers.Main) {
                val tv = async(Dispatchers.IO) {
                    val cursor = movieHelper.queryAllTv()
                    MappingHelper.mapCursorTvToArrayList(cursor)
                }
                val dataTv = tv.await()
                if (dataTv.size > 0){
                    for (dataDbTv: MovieDbModel in dataTv){
                        if (dataDbTv.movies?.id == movie.id && dataDbTv.movies?.isFavorite == true){
                            listMovies[position].isFavorite = true
                            showFavoriteButton()
                        }
                    }
                }else{
                    showNotFavoriteButton()
                }
            }
        }

        private fun onClickMovie(movie: Movies, position: Int) {
            itemView.btnFav.setOnClickListener {
                if (movie.isFavorite!!){
                    movie.isFavorite = false
                    deleteFromDatabaseMovieById(movie)
                    removeItem(position)
                }else{
                    movie.isFavorite = true
                    addToMovieDatabase(movie)
                }
                checkFavorite(movie)
            }
        }

        private fun checkFavoriteMovieFromDb(movie: Movies, position: Int) {
            GlobalScope.launch(Dispatchers.Main) {
                val movies = async(Dispatchers.IO) {
                    val cursor = movieHelper.queryAllMovie()
                    MappingHelper.mapCursorMovieToArrayList(cursor)
                }
                val dataMovies = movies.await()
                if (dataMovies.size > 0){
                    for (dataDbMovie: MovieDbModel in dataMovies){
                        if (dataDbMovie.movies?.id == movie.id){
                            listMovies[position].isFavorite = true
                            showFavoriteButton()
                        }
                    }
                }else{
                    showNotFavoriteButton()
                }
            }
        }

        private fun showFavoriteButton(){
            itemView.btnFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_primary_24dp))
        }

        private fun showNotFavoriteButton(){
            itemView.btnFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_border_primary_24dp))
        }

        private fun checkFavorite(movie: Movies) {
            if (movie.isFavorite!!){
                showFavoriteButton()
            }else{
                showNotFavoriteButton()
            }
        }

        private fun setGenresMovie(movie: Movies) {
            for (element in movie.genreIds!!){
                for (j in hawkStorage.getGenres().genres!!.indices){
                    if (hawkStorage.getGenres().genres!![j]?.id == element){
                        itemView.tvGenre.text = hawkStorage.getGenres().genres!![j]?.name
                    }
                }
            }
        }

        private fun setReleaseMovie(movie: Movies) {
            if (movie.releaseDate != null){
                itemView.tvReleaseDate.text = movie.releaseDate
            }else{
                itemView.tvReleaseDate.text = movie.firstAirDate
            }
        }

        private fun setTitleMovie(movie: Movies) {
            if (movie.originalTitle != null){
                itemView.tvTittleMovie.text = movie.originalTitle
            }else{
                itemView.tvTittleMovie.text = movie.originalName
            }
        }

        private fun deleteFromDatabaseMovieById(movie: Movies) {
            GlobalScope.launch(Dispatchers.Main) {
                val movies = async(Dispatchers.IO) {
                    val cursor = movieHelper.queryAllMovie()
                    MappingHelper.mapCursorMovieToArrayList(cursor)
                }
                val dataMovies = movies.await()

                if (dataMovies.size > 0){
                    for (i in dataMovies.indices){
                        if (dataMovies[i].movies?.id == movie.id){
                            val result = movieHelper.deleteMovieById(dataMovies[i].id.toString()).toLong()
                            if (result > 0){
                                context.toast(context.getString(R.string.success_remove_favorite))
                            }else{
                                context.toast(context.getString(R.string.failed_remove_favorite))
                            }
                        }
                    }
                }
            }
        }

        private fun deleteFromDatabaseTvById(movie: Movies) {
            GlobalScope.launch(Dispatchers.Main) {
                val tv = async(Dispatchers.IO) {
                    val cursor = movieHelper.queryAllTv()
                    MappingHelper.mapCursorTvToArrayList(cursor)
                }
                val dataTv = tv.await()
                if (dataTv.size > 0){
                    for (dataDbTv: MovieDbModel in dataTv){
                        if (dataDbTv.movies?.id == movie.id){
                            val result = movieHelper.deleteTvById(dataDbTv.id.toString()).toLong()
                            if (result > 0){
                                context.toast(context.getString(R.string.success_remove_favorite))
                            }else{
                                context.toast(context.getString(R.string.failed_remove_favorite))
                            }
                        }
                    }
                }
            }
        }

        private fun addToMovieDatabase(movie: Movies){
            val values = ContentValues()
            val movieString = Gson().toJson(movie)
            values.put(DatabaseContract.MovieColumns.MOVIE_RESPONSE, movieString)

            val result = movieHelper.insertMovie(values)
            if (result > 0){
                context.toast(context.getString(R.string.success_add_movie_favorite))
            }else{
                context.toast(context.getString(R.string.failed_add_movie_favorite))
            }
        }

        private fun addToTvDatabase(movie: Movies){
            val values = ContentValues()
            val tvString = Gson().toJson(movie)
            values.put(DatabaseContract.MovieColumns.TV_RESPONSE, tvString)

            val result = movieHelper.insertTv(values)
            if (result > 0){
                context.toast(context.getString(R.string.success_add_tv_favorite))
            }else{
                context.toast(context.getString(R.string.failed_add_tv_favorite))
            }
        }

    }
}