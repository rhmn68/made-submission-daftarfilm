package coffeecode.co.daftarfilm.features.main

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import coffeecode.co.daftarfilm.BuildConfig
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.features.main.home.HomeFragment
import coffeecode.co.daftarfilm.features.main.profile.ProfileFragment
import coffeecode.co.daftarfilm.features.main.favorite.FavoriteFragment
import coffeecode.co.daftarfilm.networking.ApiServices
import coffeecode.co.daftarfilm.storage.HawkStorage
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private companion object{
        private const val ID_NAVIGATION = "ID_NAVIGATION"
    }

    private lateinit var hawkStorage: HawkStorage
    private var idNavigation: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initHawkStorage()
        detectLanguage()
        getConfigurationAndSaveToStorage()
        getGenresAndSaveToStorage()
        initBottomNavigation()
        if (savedInstanceState == null){
            openHomeFragment()
        }else{
            val id = savedInstanceState.getInt(ID_NAVIGATION)
            btmNavigationMain.selectedItemId = id
        }
    }

    override fun onResume() {
        super.onResume()
        detectLanguage()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ID_NAVIGATION, idNavigation)
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
                R.id.action_favorite -> {
                    openFragment(FavoriteFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_profile -> {
                    openFragment(ProfileFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            Log.d("coba", "id : ${it.itemId}")
            idNavigation = it.itemId
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
