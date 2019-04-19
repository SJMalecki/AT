package pl.sjmprofil.animaltinder.fragments.followerdetails

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.databinding.FollowerDetailsFragmentLayoutBinding
import pl.sjmprofil.animaltinder.models.User

class FollowerDetailsFragment : Fragment() {

    lateinit var followerDetailsFragmentLayoutBinding: FollowerDetailsFragmentLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        followerDetailsFragmentLayoutBinding =
            DataBindingUtil.inflate(inflater, R.layout.follower_details_fragment_layout, container, false)
        val user = User(
            0,
            "email@op.pl",
            "Taylor",
            "Swift",
            "1234",
            "https://static.wizaz.pl/resize/var/ezdemo_site/storage/images/fryzury/lob-najmodniejsza-fryzura-sezonu/lob-taylor-swift/120930-1-pol-PL/Lob-Taylor-Swift.jpg?width=256&height=256"
        )
        followerDetailsFragmentLayoutBinding.user = user
        return followerDetailsFragmentLayoutBinding.root
    }
}