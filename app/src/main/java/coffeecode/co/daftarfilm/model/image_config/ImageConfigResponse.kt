package coffeecode.co.daftarfilm.model.image_config

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ImageConfigResponse(

	@field:SerializedName("images")
	val images: Images? = null,

	@field:SerializedName("change_keys")
	val changeKeys: List<String?>? = null
)