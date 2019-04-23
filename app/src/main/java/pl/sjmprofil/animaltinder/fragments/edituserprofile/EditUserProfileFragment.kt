package pl.sjmprofil.animaltinder.fragments.edituserprofile

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.edit_user_profile_fragment_layout.*
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.databinding.EditUserProfileFragmentLayoutBinding

class EditUserProfileFragment: Fragment() {

    private lateinit var bindEditUserProfileFragment: EditUserProfileFragmentLayoutBinding
    


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindEditUserProfileFragment = DataBindingUtil.inflate(inflater, R.layout.edit_user_profile_fragment_layout,
            container, false)
        return bindEditUserProfileFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text_view_edit_user_fragment.text = "Obama"

    }
}