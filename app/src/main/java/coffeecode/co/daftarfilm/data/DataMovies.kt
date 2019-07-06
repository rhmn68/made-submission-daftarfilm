package coffeecode.co.daftarfilm.data

import android.content.Context
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.model.MoviesModel
import java.util.*
import kotlin.random.Random

class DataMovies(private val context: Context?){

    fun getDataMovies() : List<MoviesModel>{
        val listMovies : MutableList<MoviesModel> = mutableListOf()
        val dataPoster = context?.resources?.obtainTypedArray(R.array.data_poster)
        val dataBanner = context?.resources?.obtainTypedArray(R.array.data_banner)
        val dataTitle = context?.resources?.getStringArray(R.array.data_title)
        val dataYear = context?.resources?.getStringArray(R.array.data_year)
        val dataGenre = context?.resources?.getStringArray(R.array.data_genre)
        val dataTime = context?.resources?.getStringArray(R.array.data_time)
        val dataRating = context?.resources?.getIntArray(R.array.data_rating)
        val dataDesc = context?.resources?.getStringArray(R.array.data_desc)
        val dataNameDirector = context?.resources?.getStringArray(R.array.data_name_director)
        val dataNameStars = context?.resources?.getStringArray(R.array.data_name_stars)

        if (dataTitle != null) {
            for (i in dataTitle.indices){
                val random = Random.nextInt(0, dataTitle.size)
                listMovies.add(MoviesModel(
                        random,
                        dataPoster?.getResourceId(random, 0),
                        dataBanner?.getResourceId(random, 0),
                        dataTitle[random],
                        dataYear?.get(random),
                        dataGenre?.get(random),
                        dataTime?.get(random),
                        dataRating?.get(random),
                        dataDesc?.get(random),
                        dataNameDirector?.get(random),
                        dataNameStars?.get(random)
                ))
            }
        }

        return listMovies
    }

    fun getDataTvShows() : List<MoviesModel>{
        val listTvShows : MutableList<MoviesModel> = mutableListOf()
        val dataPoster = context?.resources?.obtainTypedArray(R.array.data_poster)
        val dataBanner = context?.resources?.obtainTypedArray(R.array.data_banner)
        val dataTitle = context?.resources?.getStringArray(R.array.data_title)
        val dataYear = context?.resources?.getStringArray(R.array.data_year)
        val dataGenre = context?.resources?.getStringArray(R.array.data_genre)
        val dataTime = context?.resources?.getStringArray(R.array.data_time)
        val dataRating = context?.resources?.getIntArray(R.array.data_rating)
        val dataDesc = context?.resources?.getStringArray(R.array.data_desc)
        val dataNameDirector = context?.resources?.getStringArray(R.array.data_name_director)
        val dataNameStars = context?.resources?.getStringArray(R.array.data_name_stars)

        if (dataTitle != null) {
            for (i in dataTitle.indices){
                val random = Random.nextInt(0, dataTitle.size)
                listTvShows.add(MoviesModel(
                        random,
                        dataPoster?.getResourceId(random, 0),
                        dataBanner?.getResourceId(random, 0),
                        dataTitle[random],
                        dataYear?.get(random),
                        dataGenre?.get(random),
                        dataTime?.get(random),
                        dataRating?.get(random),
                        dataDesc?.get(random),
                        dataNameDirector?.get(random),
                        dataNameStars?.get(random)
                ))
            }
        }

        return listTvShows
    }

}