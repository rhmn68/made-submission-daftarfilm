package coffeecode.co.daftarfilm.storage

import android.content.Context
import coffeecode.co.daftarfilm.features.main.model.image_config.ImageConfigResponse
import com.orhanobut.hawk.Hawk

class HawkStorage (val context: Context?){

    companion object{
        const val KEY_IMAGE_CONFIG = "KEY_IMAGE_CONFIG"
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

    fun deleteAll(){
        Hawk.deleteAll()
    }
}