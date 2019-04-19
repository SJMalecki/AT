package pl.sjmprofil.animaltinder.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import pl.sjmprofil.animaltinder.fragments.search.SearchFragment
import pl.sjmprofil.animaltinder.models.Advert

private const val MAX_VALUE = 200

class SwipingViewPagerAdapter(manager: FragmentManager, private val list: List<Advert>) :
    FragmentStatePagerAdapter(manager) {


    override fun getItem(position: Int): Fragment {
        return SearchFragment()
    }

    override fun getCount(): Int {
        return list.size * MAX_VALUE
    }
}