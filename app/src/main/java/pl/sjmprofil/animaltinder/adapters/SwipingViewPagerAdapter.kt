package pl.sjmprofil.animaltinder.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import pl.sjmprofil.animaltinder.fragments.search.SearchFragment
import pl.sjmprofil.animaltinder.models.Advert
import pl.sjmprofil.animaltinder.models.AdvertParcelable

private const val MAX_VALUE = 200

class SwipingViewPagerAdapter(manager: FragmentManager,private val adverts: MutableList<Advert> ) :
    FragmentStatePagerAdapter(manager) {

    override fun getItem(position: Int): Fragment {
        return SearchFragment().getInstance(adverts[position % adverts.size])
    }

    override fun getCount(): Int {
        return adverts.size * MAX_VALUE
    }
}