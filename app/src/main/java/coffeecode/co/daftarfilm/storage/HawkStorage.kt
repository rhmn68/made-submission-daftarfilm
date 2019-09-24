package coffeecode.co.daftarfilm.storage

import android.content.Context
import coffeecode.co.daftarfilm.model.genres.GenreResponse
import coffeecode.co.daftarfilm.model.imageconfig.ImageConfigResponse
import com.orhanobut.hawk.Hawk

class HawkStorage (val context: Context?){

    companion object{
        const val KEY_IMAGE_CONFIG = "KEY_IMAGE_CONFIG"
        const val KEY_GENRES = "KEY_GENRES"
    }

    fun instance(){
        Hawk.init(context).build()
    }

    fun setImageConfig(imageConfigResponse: ImageConfigResponse){
        Hawk.put(KEY_IMAGE_CONFIG, imageConfigResponse)
    }

    fun getImageConfig() : ImageConfigResponse{
        return Hawk.get(KEY_IMAGE_CONFIG)
    }

    fun setGenres(genreResponse: GenreResponse){
        Hawk.put(KEY_GENRES, genreResponse)
    }

    fun getGenres(): GenreResponse{
        return Hawk.get(KEY_GENRES)
    }

    fun deleteAll(){
        Hawk.deleteAll()
    }
}