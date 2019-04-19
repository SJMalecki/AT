package pl.sjmprofil.animaltinder.fragments.userprofile

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.databinding.UserProfileFragmentLayoutBinding
import pl.sjmprofil.animaltinder.models.User
import pl.sjmprofil.animaltinder.repository.ApiRepository

class UserProfileFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private val apiRepository: ApiRepository by instance()

    private lateinit var bindUserProfileFragment: UserProfileFragmentLayoutBinding

    private lateinit var myUser : User


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindUserProfileFragment =
                DataBindingUtil.inflate(inflater, R.layout.user_profile_fragment_layout, container, false)
        getMyUser()
        bindUserProfileFragment.user = myUser
        bindUserProfileFragment.executePendingBindings()
        return bindUserProfileFragment.root
    }

    private fun getMyUser() {
        runBlocking {
            myUser = apiRepository.getMyUserInfoFromServer()
        }
    }
}