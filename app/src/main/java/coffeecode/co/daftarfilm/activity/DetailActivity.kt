package coffeecode.co.daftarfilm.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.model.DaftarFilmModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setDataView()
    }

    private fun setDataView() {
        val itemDaftarFilm = intent.getParcelableExtra<DaftarFilmModel>(MainActivity.EXTRA_DAFTAR_FILM)

        Glide.with(this).load(itemDaftarFilm.image).into(ivImage)
        tvTitle.text = itemDaftarFilm.title
        tvRating.text = itemDaftarFilm.rating.toString()
        tvDesc.text = itemDaftarFilm.description
    }
}
