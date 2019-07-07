package coffeecode.co.daftarfilm.data

import android.content.Context
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.model.MoviesModel
import kotlin.random.Random

class DataMovies(context: Context?){

    private val dataPoster = context?.resources?.obtainTypedArray(R.array.data_poster)
    private val dataBanner = context?.resources?.obtainTypedArray(R.array.data_banner)
    private val dataTitle = context?.resources?.getStringArray(R.array.data_title)
    private val dataYear = context?.resources?.getStringArray(R.array.data_year)
    private val dataGenre = context?.resources?.getStringArray(R.array.data_genre)
    private val dataTime = context?.resources?.getStringArray(R.array.data_time)
    private val dataRating = context?.resources?.getIntArray(R.array.data_rating)
    private val dataDesc = context?.resources?.getStringArray(R.array.data_desc)
    private val dataNameDirector = context?.resources?.getStringArray(R.array.data_name_director)
    private val dataNameStars = context?.resources?.getStringArray(R.array.data_name_stars)

    private var loopStatus = true
    private var itemsCount = 0
    private val tempNumbers = IntArray(dataTitle!!.size)

    fun getDataMovies() : List<MoviesModel>{
        val listMovies : MutableList<MoviesModel> = mutableListOf()
        while(loopStatus){
            val randomNum = Random.nextInt(tempNumbers.size) + 1

            if (!completed()){
                if (!duplicated(randomNum)){
                    tempNumbers[itemsCount] = randomNum
                    itemsCount++
                }else{
                    continue
                }
            }else{
                loopStatus = false
            }
        }

        for (i in 0 until tempNumbers.size){
            val j = tempNumbers[i] - 1
            listMovies.add(MoviesModel(
                tempNumbers[i],
                dataPoster?.getResourceId(j, 0),
                dataBanner?.getResourceId(j, 0),
                dataTitle?.get(j),
                dataYear?.get(j),
                dataGenre?.get(j),
                dataTime?.get(j),
                dataRating?.get(j),
                dataDesc?.get(j),
                dataNameDirector?.get(j),
                dataNameStars?.get(j)
            ))
        }
        return listMovies
    }

    fun getDataTvShows() : List<MoviesModel>{
        val listTvShows : MutableList<MoviesModel> = mutableListOf()
        while(loopStatus){
            val randomNum = Random.nextInt(tempNumbers.size) + 1

            if (!completed()){
                if (!duplicated(randomNum)){
                    tempNumbers[itemsCount] = randomNum
                    itemsCount++
                }else{
                    continue
                }
            }else{
                loopStatus = false
            }
        }

        for (i in 0 until tempNumbers.size){
            val j = tempNumbers[i] - 1
            listTvShows.add(MoviesModel(
                tempNumbers[i],
                dataPoster?.getResourceId(j, 0),
                dataBanner?.getResourceId(j, 0),
                dataTitle?.get(j),
                dataYear?.get(j),
                dataGenre?.get(j),
                dataTime?.get(j),
                dataRating?.get(j),
                dataDesc?.get(j),
                dataNameDirector?.get(j),
                dataNameStars?.get(j)
            ))
        }
        return listTvShows
    }

    private fun completed(): Boolean {
        var status = true
        for (i in 0 until tempNumbers.size){
            if (tempNumbers[i] == 0){
                status = false
                break
            }
        }
        return status
    }

    private fun duplicated(number: Int): Boolean {
        var status = false
        for (i in 0 until tempNumbers.size){
            if (tempNumbers[i] == number){
                status = true
                break
            }
        }
        return status
    }

}