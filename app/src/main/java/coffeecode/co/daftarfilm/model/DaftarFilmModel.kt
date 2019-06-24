package coffeecode.co.daftarfilm.model

import android.os.Parcel
import android.os.Parcelable

data class DaftarFilmModel(
    val title: String? = null,
    val rating: Int? = null,
    val description: String? = null,
    val image: Int? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeValue(rating)
        parcel.writeString(description)
        parcel.writeValue(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DaftarFilmModel> {
        override fun createFromParcel(parcel: Parcel): DaftarFilmModel {
            return DaftarFilmModel(parcel)
        }

        override fun newArray(size: Int): Array<DaftarFilmModel?> {
            return arrayOfNulls(size)
        }
    }
}