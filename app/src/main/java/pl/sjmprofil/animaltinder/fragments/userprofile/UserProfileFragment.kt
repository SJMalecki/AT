package pl.sjmprofil.animaltinder.fragments.userprofile

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.databinding.UserProfileFragmentLayoutBinding
import pl.sjmprofil.animaltinder.models.Advert

class UserProfileFragment : Fragment() {

    private lateinit var bindUserProfileFragment: UserProfileFragmentLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindUserProfileFragment =
                DataBindingUtil.inflate(inflater, R.layout.user_profile_fragment_layout, container, false)
        val advert = Advert("nameTest", "surnameTest", "http://sjmprofil.pl/imgjson/18.jpg")
        bindUserProfileFragment.advert = advert
        return bindUserProfileFragment.root
    }
}