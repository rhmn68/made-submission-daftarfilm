package coffeecode.co.daftarfilm.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.adapter.DaftarFilmAdapter
import coffeecode.co.daftarfilm.model.DaftarFilmModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var daftarFilmAdapter : DaftarFilmAdapter
    private val listDaftarFilm : MutableList<DaftarFilmModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setData()
        setAdapter()
        onClick()
    }

    private fun onClick() {
        lvDaftarFilm.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(Companion.EXTRA_DAFTAR_FILM, listDaftarFilm[position])
            startActivity(intent)
        }

    }

    private fun setAdapter() {
        daftarFilmAdapter = DaftarFilmAdapter(this)
        daftarFilmAdapter.setListDaftarFilm(listDaftarFilm)

        lvDaftarFilm.adapter = daftarFilmAdapter
    }

    private fun setData() {
        val title = resources.getStringArray(R.array.data_title)
        val desc = resources.getStringArray(R.array.data_desc)
        val rating = resources.getIntArray(R.array.data_rating)
        val image = resources.obtainTypedArray(R.array.data_image)

        for (i in title.indices){
            listDaftarFilm.add(
                DaftarFilmModel(
                    title[i],
                    rating[i],
                    desc[i],
                    image.getResourceId(i, -1))
            )
        }
    }

    companion object {
        const val EXTRA_DAFTAR_FILM = "EXTRA_DAFTAR_FILM"
    }
}
