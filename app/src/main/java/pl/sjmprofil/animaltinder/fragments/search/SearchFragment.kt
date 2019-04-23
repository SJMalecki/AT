package pl.sjmprofil.animaltinder.fragments.search

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.search_fragment_layout.*
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.adapters.SwipingViewPagerAdapter
import pl.sjmprofil.animaltinder.databinding.SearchFragmentSlideBinding
import pl.sjmprofil.animaltinder.models.AdvertParcelable


class SearchFragment : Fragment() {

    private lateinit var viewPagerAdapter: SwipingViewPagerAdapter
    private lateinit var binding: SearchFragmentSlideBinding


    fun getInstance(advert: AdvertParcelable): SearchFragment {
        val fragment = SearchFragment()
        val bundle = Bundle()
        bundle.putParcelable("advert", advert)
        fragment.arguments = bundle
        return fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPagerAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment_slide, container, false)
        binding.advert = arguments?.getParcelable("advert")
        return binding.root
    }

    private fun setupViewPagerAdapter() {

        val advert1 = AdvertParcelable(1,"aaa", "djhf@wp.pl", "aaaaa", "http://d3g9pb5nvr3u7.cloudfront.net/authors/539a28913f3c0fd71ed4e43d/2131300937/256.jpg" )
        val advert2 = AdvertParcelable(2, "bbb", "bb@onet.pl", "bbbb","https://static.wizaz.pl/resize/var/ezdemo_site/storage/images/fryzury/lob-najmodniejsza-fryzura-sezonu/lob-taylor-swift/120930-1-pol-PL/Lob-Taylor-Swift.jpg?width=256&height=256" )
        val adverts = arrayListOf(advert1, advert2)

        viewPagerAdapter = SwipingViewPagerAdapter(fragmentManager!!, adverts)
        search_fragment_slide.adapter = viewPagerAdapter
        search_fragment_slide.currentItem = viewPagerAdapter.count/2

    }


}