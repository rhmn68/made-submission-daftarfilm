package coffeecode.co.daftarfilm.model.movie

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Dates(

	@field:SerializedName("maximum")
	val maximum: String? = null,

	@field:SerializedName("minimum")
	val minimum: String? = null
): Serializable