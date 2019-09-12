package coffeecode.co.daftarfilm.features.detail.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coffeecode.co.daftarfilm.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setDataView()
    }

    private fun setDataView() {
////        val itemDaftarFilm = intent.getParcelableExtra<DaftarFilmModel>(MainActivity.EXTRA_DAFTAR_FILM)
//
//        Glide.with(this).load(itemDaftarFilm.image).into(ivImage)
//        tvTitle.text = itemDaftarFilm.title
//        tvRating.text = itemDaftarFilm.rating.toString()
//        tvDesc.text = itemDaftarFilm.description
    }
}
