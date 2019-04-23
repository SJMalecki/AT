package pl.sjmprofil.animaltinder.fragments.search

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.databinding.SearchFragmentSlideBinding
import pl.sjmprofil.animaltinder.models.AdvertParcelable


class SearchFragment : Fragment() {

    private lateinit var aAdvert: AdvertParcelable
    private lateinit var binding: SearchFragmentSlideBinding


    fun getInstance(advert: AdvertParcelable): SearchFragment {
        val fragment = SearchFragment()
        val bundle = Bundle()
        bundle.putParcelable("advert", advert)
        fragment.arguments = bundle
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) aAdvert = arguments!!.getParcelable("advert")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment_slide, container, false)
        binding.advert = arguments?.getParcelable("advert")
        return binding.root
    }
}