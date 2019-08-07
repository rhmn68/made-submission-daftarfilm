package coffeecode.co.daftarfilm.features.main.model.image_config

import com.google.gson.annotations.SerializedName

data class ImageConfigResponse(

	@field:SerializedName("images")
	val images: Images? = null,

	@field:SerializedName("change_keys")
	val changeKeys: List<String?>? = null
)