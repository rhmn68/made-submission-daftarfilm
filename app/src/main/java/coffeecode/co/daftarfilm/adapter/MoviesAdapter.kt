package coffeecode.co.daftarfilm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.model.MoviesModel
import coffeecode.co.daftarfilm.utils.Utils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movies.view.*
import kotlinx.android.synthetic.main.layout_desc_item_movies.view.*

class MoviesAdapter(private val itemsMovies: List<MoviesModel>,private val listener : (MoviesModel) -> Unit)
    : RecyclerView.Adapter<MoviesAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false))

    override fun getItemCount(): Int = itemsMovies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(itemsMovies[position], listener)
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        fun bindItem(itemMovie : MoviesModel, listener: (MoviesModel) -> Unit){
            Glide.with(view).load(itemMovie.imagePoster).into(itemView.ivPosterContainer)
            itemView.tvTitle.text = itemMovie.title
            itemView.tvYear.text = itemMovie.year
            itemView.tvGenre.text = itemMovie.genre
            itemView.rbMovies.rating = Utils.convertRating(itemMovie.rating)

            view.setOnClickListener { listener(itemMovie) }
        }
    }
}