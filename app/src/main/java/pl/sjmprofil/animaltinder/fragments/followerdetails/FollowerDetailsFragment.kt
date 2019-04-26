package pl.sjmprofil.animaltinder.fragments.followerdetails

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.follower_details_fragment_layout.*
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.databinding.FollowerDetailsFragmentLayoutBinding
import pl.sjmprofil.animaltinder.models.User

class FollowerDetailsFragment : Fragment() {

    lateinit var followerDetailsFragmentLayoutBinding: FollowerDetailsFragmentLayoutBinding
    lateinit var user: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        followerDetailsFragmentLayoutBinding =
            DataBindingUtil.inflate(inflater, R.layout.follower_details_fragment_layout, container, false)
        setupUserBinding()
        return followerDetailsFragmentLayoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_send_email_follower_detail_fragment.setOnClickListener {
            setupEmailActivity()
        }
    }

    private fun setupUserBinding() {
        arguments?.let {
            val safeArgUser = FollowerDetailsFragmentArgs.fromBundle(it)
            user = safeArgUser.user
            followerDetailsFragmentLayoutBinding.user = user
        }
    }

    private fun setupEmailActivity() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf(user.email))
            putExtra(Intent.EXTRA_SUBJECT, "Pet adoption")
            putExtra(Intent.EXTRA_TEXT, "Hello, I'm interested in adopting your pet!")
            type = "message/rfc822"
        }
        startActivity(Intent.createChooser(intent, "Send email"))
    }
}