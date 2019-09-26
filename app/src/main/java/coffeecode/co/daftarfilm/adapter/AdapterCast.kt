package coffeecode.co.daftarfilm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.model.credits.CastItem
import coffeecode.co.daftarfilm.model.credits.CreditsResponse
import coffeecode.co.daftarfilm.storage.HawkStorage
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_cast.view.*

class AdapterCast(private val context: Context): RecyclerView.Adapter<AdapterCast.ViewHolder>(){

    private var creditsResponse = CreditsResponse()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false))

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(context, creditsResponse.cast?.get(position))
    }

    fun setData(creditsResponse: CreditsResponse?){
        if (creditsResponse != null){
            this.creditsResponse = creditsResponse
        }
        notifyDataSetChanged()
    }

    class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        fun bindItem(context: Context, castItem: CastItem?) {
            val hawkStorage = HawkStorage(context)
            val secureBaseUrl = hawkStorage.getImageConfig().images?.secureBaseUrl
            val sizeProfile = hawkStorage.getImageConfig().images?.profileSizes?.get(0)
            val urlProfilePath = secureBaseUrl + sizeProfile + castItem?.profilePath

            itemView.tvNameCast.text = castItem?.name

            itemView.tvCharacterCast.text = castItem?.character

            Glide.with(view).load(urlProfilePath).into(itemView.ivProfileCast)
        }
    }
}