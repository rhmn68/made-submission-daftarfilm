package coffeecode.co.daftarfilm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coffeecode.co.daftarfilm.R

class AdapterVerticalListMovies(private val listener: (String) -> Unit): RecyclerView.Adapter<AdapterVerticalListMovies.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.item_vertical_list_movies, parent, false)
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