package pl.sjmprofil.animaltinder.fragments.userprofile

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.databinding.UserProfileFragmentLayoutBinding
import pl.sjmprofil.animaltinder.models.User

class UserProfileFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private lateinit var bindUserProfileFragment: UserProfileFragmentLayoutBinding

    private val userProfileFragmentViewModelFactory: UserProfileFragmentViewModelFactory by instance()
    private lateinit var userProfileFragmentViewModel: UserProfileFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindUserProfileFragment =
            DataBindingUtil.inflate(inflater, R.layout.user_profile_fragment_layout, container, false)

        return bindUserProfileFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userProfileFragmentViewModel.getMyData { myUser: User -> bindUserProfileFragment.user = myUser }

        bindUserProfileFragment.executePendingBindings()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupViewModel() {
        userProfileFragmentViewModel =
            ViewModelProviders
                .of(this, userProfileFragmentViewModelFactory)
                .get(UserProfileFragmentViewModel::class.java)
    }
}