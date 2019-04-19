package pl.sjmprofil.animaltinder.fragments.followerdetails

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.databinding.FollowerDetailsFragmentLayoutBinding

class FollowerDetailsFragment : Fragment() {

    lateinit var followerDetailsFragmentLayoutBinding: FollowerDetailsFragmentLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        followerDetailsFragmentLayoutBinding =
            DataBindingUtil.inflate(inflater, R.layout.follower_details_fragment_layout, container, false)

        arguments?.let {
            val safeArgUser = FollowerDetailsFragmentArgs.fromBundle(it)
            followerDetailsFragmentLayoutBinding.user = safeArgUser.user
        }
        return followerDetailsFragmentLayoutBinding.root
    }
}