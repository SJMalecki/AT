package pl.sjmprofil.animaltinder.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import pl.sjmprofil.animaltinder.fragments.search.SearchFragment
import pl.sjmprofil.animaltinder.models.Advert

private const val MAX_VALUE = 200

class SwipingViewPagerAdapter(manager: FragmentManager) :
    FragmentStatePagerAdapter(manager) {

    private val adverts: MutableList<Advert> = mutableListOf()

    override fun getItem(position: Int): Fragment {
        return SearchFragment()
// .getInstance(adverts[position % adverts.size])
    }

    private fun makeFragmentName(advert: Advert): String {
        return "android:switcher:${advert.id}"
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "${adverts[position % adverts.size]}"
    }

    override fun getCount(): Int {
        return adverts.size * MAX_VALUE
    }

    fun swapData(listOfAdverts: List<Advert>) {
        adverts.clear()
        adverts.addAll(listOfAdverts)
        notifyDataSetChanged()
    }
}