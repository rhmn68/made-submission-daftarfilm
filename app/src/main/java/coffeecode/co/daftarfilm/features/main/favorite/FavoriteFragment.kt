package coffeecode.co.daftarfilm.features.main.favorite


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coffeecode.co.daftarfilm.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_favorite.view.*

class FavoriteFragment : Fragment() {

    companion object{
        private const val KEY_POSITION_TAB = "KEY_POSITION_TAB"
    }

    private var positionTab = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_favorite, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTabLayout()
        if (savedInstanceState == null){
            openMovieFragment()
        }else{
            val position = savedInstanceState.getInt(KEY_POSITION_TAB)
            tabLayoutFavorite.getTabAt(position)?.select()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_POSITION_TAB, positionTab)
    }

    private fun openMovieFragment() {
        tabLayoutFavorite.getTabAt(0)?.select()
    }

    private fun initTabLayout() {

        tabLayoutFavorite.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> openFragment(FavoriteMovieFragment())
                    1 -> openFragment(FavoriteTvFragment())
                    else -> openFragment(FavoriteMovieFragment())
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> openFragment(FavoriteMovieFragment())
                    1 -> openFragment(FavoriteTvFragment())
                    else -> openFragment(FavoriteMovieFragment())
                }
                positionTab = tab?.position!!
            }

        })
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment, fragment, fragment::class.java.simpleName)
        transaction?.addToBackStack(fragment::class.java.simpleName)
        transaction?.commit()
    }
}
