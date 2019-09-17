package coffeecode.co.daftarfilm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coffeecode.co.daftarfilm.R

class AdapterListMovieFromKindOfMovies(private val listener: (String) -> Unit): RecyclerView.Adapter<AdapterListMovieFromKindOfMovies.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.item_list_movies_from_kind_of_movies, parent, false)
        )

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listener)
    }

    class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        fun bindItem(listener: (String) -> Unit) {
            view.setOnClickListener { listener("") }
        }
    }
}