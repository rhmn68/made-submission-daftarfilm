package coffeecode.co.daftarfilm.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import coffeecode.co.daftarfilm.R
import coffeecode.co.daftarfilm.adapter.TabMoviesAdapter
import coffeecode.co.daftarfilm.fragment.MoviesFragment
import coffeecode.co.daftarfilm.fragment.TvShowFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tabMoviesAdapter: TabMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setToolbar()
        setUpViewPager()
    }

    private fun setToolbar() {
        setSupportActionBar(toolBar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings){
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpViewPager() {
        tabMoviesAdapter = TabMoviesAdapter(supportFragmentManager)
        tabMoviesAdapter.setData(MoviesFragment(), getString(R.string.movies))
        tabMoviesAdapter.setData(TvShowFragment(), getString(R.string.tv_shows))

        vpMain.adapter = tabMoviesAdapter

        tlMain.setupWithViewPager(vpMain)
    }
}
