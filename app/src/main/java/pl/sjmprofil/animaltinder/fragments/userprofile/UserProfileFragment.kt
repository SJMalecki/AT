package pl.sjmprofil.animaltinder.fragments.userprofile

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.databinding.UserProfileFragmentLayoutBinding
import pl.sjmprofil.animaltinder.models.User

class UserProfileFragment : Fragment() {

    private lateinit var bindUserProfileFragment: UserProfileFragmentLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindUserProfileFragment =
                DataBindingUtil.inflate(inflater, R.layout.user_profile_fragment_layout, container, false)
        val user = User(0, "email@wp.pl", "Imie", "nazwisko", "password", "https://pbs.twimg.com/profile_images/651092879725740032/5Fau7HaM_400x400.jpg")
        bindUserProfileFragment.user = user
        return bindUserProfileFragment.root
    }
}