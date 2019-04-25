package pl.sjmprofil.animaltinder.fragments.userprofile

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.user_profile_fragment_layout.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.activities.MainActivity
import pl.sjmprofil.animaltinder.activities.SearchActivity
import pl.sjmprofil.animaltinder.databinding.UserProfileFragmentLayoutBinding
import pl.sjmprofil.animaltinder.fragments.followers.FollowersFragmentDirections
import pl.sjmprofil.animaltinder.models.User

class UserProfileFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private lateinit var bindUserProfileFragment: UserProfileFragmentLayoutBinding

    private val userProfileFragmentViewModelFactory: UserProfileFragmentViewModelFactory by instance()
    private lateinit var userProfileFragmentViewModel: UserProfileFragmentViewModel

    private lateinit var navController: NavController

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

        navController = Navigation.findNavController(view)
        bindUserProfileFragment.executePendingBindings()

        super.onViewCreated(view, savedInstanceState)

        button_user_profile_fragment_search.setOnClickListener {
            navController.navigate(R.id.search_fragment)
        }

        setupAddAdvertButton()
        setupMyAdvertsButton()
        setupImageEditProfile()
    }

    private fun setupImageEditProfile(){
        image_view_user_profile_fragment.setOnClickListener {
            val action = UserProfileFragmentDirections.actionUserProfileFragmentLayoutToEditUserProfileFragment()
            navController.navigate(action)
        }
    }

    private fun setupAddAdvertButton(){
        button_add_user_profile_fragment.setOnClickListener {
            val action = UserProfileFragmentDirections.actionUserProfileFragmentLayoutToAddFragmentLayout()
            navController.navigate(action)
        }
    }

    private fun setupMyAdvertsButton(){
        button_my_adverts_user_profile_fragment.setOnClickListener {
            val action = UserProfileFragmentDirections.actionUserProfileFragmentLayoutToLayoutAdvertsFragment()
            navController.navigate(action)
        }
    }

    private fun setupViewModel() {
        userProfileFragmentViewModel =
            ViewModelProviders
                .of(this, userProfileFragmentViewModelFactory)
                .get(UserProfileFragmentViewModel::class.java)
    }
}