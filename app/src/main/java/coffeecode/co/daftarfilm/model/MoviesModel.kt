package coffeecode.co.daftarfilm.model

import android.os.Parcel
import android.os.Parcelable

data class MoviesModel(
        val id: Int? = null,
        val imagePoster: Int? = null,
        val imageBanner: Int? = null,
        val title: String? = null,
        val year: String? = null,
        val genre: String? = null,
        val time: String? = null,
        val rating: Int? = null,
        val desc: String? = null,
        val nameDirector : String? = null,
        val nameStars: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(imagePoster)
        parcel.writeValue(imageBanner)
        parcel.writeString(title)
        parcel.writeString(year)
        parcel.writeString(genre)
        parcel.writeString(time)
        parcel.writeValue(rating)
        parcel.writeString(desc)
        parcel.writeString(nameDirector)
        parcel.writeString(nameStars)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MoviesModel> {
        override fun createFromParcel(parcel: Parcel): MoviesModel {
            return MoviesModel(parcel)
        }

        override fun newArray(size: Int): Array<MoviesModel?> {
            return arrayOfNulls(size)
        }
    }
}