package pl.sjmprofil.animaltinder.fragments.edituserprofile

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
import pl.sjmprofil.animaltinder.databinding.EditUserProfileFragmentLayoutBinding
import pl.sjmprofil.animaltinder.models.User

class EditUserProfileFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val editUserProfileFragmentViewModelFactory: EditUserProfileFragmentViewModelFactory by instance()

    private lateinit var bindEditUserProfileFragment: EditUserProfileFragmentLayoutBinding
    private lateinit var editUserProfileFragmentViewModel: EditUserProfileFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindEditUserProfileFragment = DataBindingUtil.inflate(
            inflater, R.layout.edit_user_profile_fragment_layout,
            container, false
        )
        return bindEditUserProfileFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editUserProfileFragmentViewModel.getMyData { myUser: User -> bindEditUserProfileFragment.user = myUser }
        bindEditUserProfileFragment.executePendingBindings()
    }

    private fun setupViewModel() {
        editUserProfileFragmentViewModel =
                ViewModelProviders
                    .of(this, editUserProfileFragmentViewModelFactory)
                    .get(EditUserProfileFragmentViewModel::class.java)
    }
}