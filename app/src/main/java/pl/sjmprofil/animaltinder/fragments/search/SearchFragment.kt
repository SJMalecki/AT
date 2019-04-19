package pl.sjmprofil.animaltinder.fragments.search

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.search_fragment_layout.*
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.databinding.SearchFragmentSlideBinding
import pl.sjmprofil.animaltinder.adapters.SwipingViewPagerAdapter
import pl.sjmprofil.animaltinder.models.Advert


class SearchFragment : Fragment() {

    private lateinit var viewPagerAdapter: SwipingViewPagerAdapter
    private lateinit var binding: SearchFragmentSlideBinding


    fun getInstance(advert: Advert): SearchFragment {
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
        val advert = Advert(
            0,
            "email@wp.pl",
            "Imie",
            "nazwisko",
            "https://pbs.twimg.com/profile_images/651092879725740032/5Fau7HaM_400x400.jpg"
        )
        binding.advert = advert
        return binding.root
    }

    private fun setupViewPagerAdapter() {
        val adverts = arrayListOf("aaa", "bbb", "ccc")
        viewPagerAdapter = SwipingViewPagerAdapter(fragmentManager!!, adverts)
        search_fragment_layout_view_pager.adapter = viewPagerAdapter
    }


}