package coffeecode.co.daftarfilm.model.movie

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieResponse(

	@field:SerializedName("dates")
	val dates: Dates? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
    var movies: ArrayList<Movies?>? = null,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
): Serializable