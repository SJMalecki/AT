package pl.sjmprofil.animaltinder.fragments.advertdetail

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance
import pl.sjmprofil.animaltinder.R

class AdvertDetailFragment: Fragment(), KodeinAware {

    override val kodein by kodein()

    private val advertDetailFragmentViewModelFactory: AdvertDetailFragmentViewModelFactory by instance()

    private lateinit var advertDetailFragmentViewModel: AdvertDetailFragmentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViewModel()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupViewModel() {
        advertDetailFragmentViewModel =
            ViewModelProviders
                .of(this, advertDetailFragmentViewModelFactory)
                .get(AdvertDetailFragmentViewModel::class.java)
    }

    lateinit var advertDetailsFragmentLayoutBinding : pl.sjmprofil.animaltinder.databinding.AdvertDetailLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        advertDetailsFragmentLayoutBinding =
            DataBindingUtil.inflate(inflater, R.layout.advert_detail_layout, container, false)

        arguments?.let {
            val safeArgAdvert = AdvertDetailFragmentArgs.fromBundle(it)
            advertDetailsFragmentLayoutBinding.advert = safeArgAdvert.advert
        }
        return advertDetailsFragmentLayoutBinding.root
    }
}