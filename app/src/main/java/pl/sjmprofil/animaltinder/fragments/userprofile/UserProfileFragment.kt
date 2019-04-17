package pl.sjmprofil.animaltinder.fragments.userprofile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_profile_fragment_layout.*
import pl.sjmprofil.animaltinder.R

class UserProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.user_profile_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Picasso
            .get()
            .load("http://sjmprofil.pl/imgjson/18.jpg")
            .centerCrop()
            .placeholder(R.drawable.ic_hourglass_empty_black_24dp)
            .error(R.drawable.ic_add_a_photo_black_24dp)
            .resize(500,500)
            .into(image_view_user_profile_fragment)
    }
}