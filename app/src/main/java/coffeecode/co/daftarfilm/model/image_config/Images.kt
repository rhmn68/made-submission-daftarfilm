package coffeecode.co.daftarfilm.model.image_config

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class Images(

	@field:SerializedName("poster_sizes")
	val posterSizes: List<String?>? = null,

	@field:SerializedName("secure_base_url")
	val secureBaseUrl: String? = null,

	@field:SerializedName("backdrop_sizes")
	val backdropSizes: List<String?>? = null,

	@field:SerializedName("base_url")
	val baseUrl: String? = null,

	@field:SerializedName("logo_sizes")
	val logoSizes: List<String?>? = null,

	@field:SerializedName("still_sizes")
	val stillSizes: List<String?>? = null,

	@field:SerializedName("profile_sizes")
	val profileSizes: List<String?>? = null
)