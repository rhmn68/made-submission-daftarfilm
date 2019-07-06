package coffeecode.co.daftarfilm.utils

object Utils{
    fun convertRating(rating: Int?) : Float{
        val resultRating = rating?.toFloat()?.div(2)
        return resultRating!!
    }
}