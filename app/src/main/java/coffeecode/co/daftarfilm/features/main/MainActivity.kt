package coffeecode.co.daftarfilm.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.features.main.subfeatures.home.HomeFragment
import coffeecode.co.daftarfilm.features.main.subfeatures.movies.MoviesFragment
import coffeecode.co.daftarfilm.features.main.subfeatures.profile.ProfileFragment
import coffeecode.co.daftarfilm.features.main.subfeatures.search.SearchFragment
import coffeecode.co.daftarfilm.features.main.subfeatures.tvshows.TvShowsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBottomNavigation()
        openHomeFragment()
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
