package coffeecode.co.daftarfilm.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import coffeecode.co.daftarfilm.BuildConfig
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.features.main.home.HomeFragment
import coffeecode.co.daftarfilm.features.main.movies.MoviesFragment
import coffeecode.co.daftarfilm.features.main.profile.ProfileFragment
import coffeecode.co.daftarfilm.features.main.search.SearchFragment
import coffeecode.co.daftarfilm.features.main.tvshows.TvShowsFragment
import coffeecode.co.daftarfilm.networking.ApiServices
import coffeecode.co.daftarfilm.storage.HawkStorage
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.support.v4.toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var hawkStorage: HawkStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initHawkStorage()
        detectLanguage()
        getConfigurationAndSaveToStorage()
        getGenresAndSaveToStorage()
        initBottomNavigation()
        openHomeFragment()
    }

    override fun onResume() {
        super.onResume()
        detectLanguage()
    }

    private fun detectLanguage() {
        val language = Locale.getDefault().language
        if (language == "in"){
            hawkStorage.setLanguage("id")
        }else if (language == "en"){
            hawkStorage.setLanguage("en-US")
        }
    }

    private fun initHawkStorage() {
        hawkStorage = HawkStorage(this)
        hawkStorage.instance()
    }

    private fun getGenresAndSaveToStorage() {
        ApiServices.getMovieServices()
                .getGenres(BuildConfig.TOKEN_MOVIE_DB, hawkStorage.getLanguage())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it != null){
                        hawkStorage.setGenres(it)
                    }
                },{
                    it.printStackTrace()
                })
    }

    private fun getConfigurationAndSaveToStorage() {
        ApiServices.getMovieServices()
            .getConfiguration(BuildConfig.TOKEN_MOVIE_DB)
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it != null){
                    hawkStorage.setImageConfig(it)
                }
            },{
                it.printStackTrace()
            })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        checkIfHomeFragment()
        openHomeFragment()
    }

    private fun checkIfHomeFragment() {
        val selectedItem = btmNavigationMain.menu.findItem(btmNavigationMain.selectedItemId)
        if(selectedItem.itemId == R.id.action_home){
            finish()
        }
    }

    private fun openHomeFragment() {
        btmNavigationMain.selectedItemId = R.id.action_home
    }

    private fun initBottomNavigation() {
        btmNavigationMain.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_home -> {
                    openFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_search -> {
                    openFragment(SearchFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_movies -> {
                    openFragment(MoviesFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_tv_shows -> {
                    openFragment(TvShowsFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_profile -> {
                    openFragment(ProfileFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            return@OnNavigationItemSelectedListener true
        })
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameMain, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
