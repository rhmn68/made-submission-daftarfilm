package coffeecode.co.daftarfilm.features.main.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coffeecode.co.daftarfilm.features.main.model.image_config.ImageConfigResponse

class MainViewModel : ViewModel(){
    private val imageConfigResponse = MutableLiveData<ImageConfigResponse>()


}