package coffeecode.co.daftarfilm.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class TabMoviesAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private var listFragment : MutableList<Fragment> = mutableListOf()
    private var listTitleFragment : MutableList<String> = mutableListOf()

    fun setData(fragment: Fragment, title:String){
        listFragment.add(fragment)
        listTitleFragment.add(title)
    }

    override fun getItem(p0: Int): Fragment = listFragment[p0]

    override fun getCount(): Int = listFragment.size

    override fun getPageTitle(position: Int): CharSequence? = listTitleFragment[position]
}