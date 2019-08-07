package coffeecode.co.daftarfilm.features.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.features.main.model.DaftarFilmModel
import com.bumptech.glide.Glide


class DaftarFilmAdapter(private val context: Context) : BaseAdapter(){

    private var listDaftarFilm : List<DaftarFilmModel>? = null

    fun setListDaftarFilm(listDaftarFilm: List<DaftarFilmModel>){
        this.listDaftarFilm = listDaftarFilm
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_film, parent, false)
        }

        val viewHolder = ViewHolder(view)
        val itemDaftarFilm = getItem(position) as DaftarFilmModel
        viewHolder.bind(itemDaftarFilm)
        return view
    }

    override fun getItem(position: Int): Any = listDaftarFilm!![position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = listDaftarFilm!!.size

    inner class ViewHolder(view: View?) {
        private lateinit var tvTitle: TextView
        private lateinit var tvRating: TextView
        private lateinit var tvDesc: TextView
        private lateinit var ivImage: ImageView

        init {
            if (view != null){
                tvTitle = view.findViewById(R.id.tvTitle)
                tvRating = view.findViewById(R.id.tvRating)
                tvDesc = view.findViewById(R.id.tvDesc)
                ivImage = view.findViewById(R.id.ivImage)
            }
        }

        fun bind(daftarFilmModel: DaftarFilmModel){
            tvTitle.text = daftarFilmModel.title
            tvDesc.text = daftarFilmModel.description
            tvRating.text = daftarFilmModel.rating.toString()
            Glide.with(context).load(daftarFilmModel.image).into(ivImage)
        }
    }
}


