package coffeecode.co.daftarfilm.features.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coffeecode.co.daftarfilm.model.image_config.ImageConfigResponse

class MainViewModel : ViewModel(){
    private val imageConfigResponse = MutableLiveData<ImageConfigResponse>()


}