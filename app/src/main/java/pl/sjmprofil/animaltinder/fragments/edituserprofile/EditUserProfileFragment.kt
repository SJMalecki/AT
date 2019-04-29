package pl.sjmprofil.animaltinder.fragments.edituserprofile

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.edit_user_profile_fragment_layout.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.databinding.EditUserProfileFragmentLayoutBinding
import pl.sjmprofil.animaltinder.models.User
import pl.sjmprofil.animaltinder.utilities.DialogFragmentAddBio

class EditUserProfileFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val editUserProfileFragmentViewModelFactory: EditUserProfileFragmentViewModelFactory by instance()

    private lateinit var bindEditUserProfileFragment: EditUserProfileFragmentLayoutBinding
    private lateinit var editUserProfileFragmentViewModel: EditUserProfileFragmentViewModel

    private val TAKE_PICTURE_BUTTON_REQUEST_ID = 103
    private val OPEN_GALLERY_BUTTON_REQUEST_ID = 104

    private val dialogFragmentAddBio = DialogFragmentAddBio()

    private var profilePictureBitmap: Bitmap? = null

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

        setupOpenGalleryButton()
        setupTakePictureButton()
        setupImageToOpenGalleryButton()
        setupEditBioButton()
        setupProfilePicture()
        setupApproveChangesButton()
    }

    private fun setupProfilePicture() {
        editUserProfileFragmentViewModel.getMyData { myUser: User ->
            bindEditUserProfileFragment.user = myUser
            bindEditUserProfileFragment.executePendingBindings()
            if (profilePictureBitmap != null) {
                Glide.with(context!!)
                    .load(profilePictureBitmap)
                    .apply(
                        RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .error(R.drawable.loading_circle)
                    )
                    .into(image_view_edit_profile_fragment)
            }
        }
    }

    private fun setupApproveChangesButton() {
        button_add_advert_edit_profile_fragment.setOnClickListener {
            if (profilePictureBitmap != null) {
                editUserProfileFragmentViewModel.postMyNewData(profilePictureBitmap!!)
                Toast.makeText(context!!, getString(R.string.profile_picture_changed), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context!!, getString(R.string.no_changes), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupViewModel() {
        editUserProfileFragmentViewModel =
                ViewModelProviders
                    .of(this, editUserProfileFragmentViewModelFactory)
                    .get(EditUserProfileFragmentViewModel::class.java)
    }

    private fun setupTakePictureButton() {
        button_take_picture_edit_profile_fragment.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, TAKE_PICTURE_BUTTON_REQUEST_ID)
        }
    }

    private fun setupOpenGalleryButton() {
        button_open_gallery_edit_profile_fragment.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, OPEN_GALLERY_BUTTON_REQUEST_ID)
        }
    }

    private fun setupImageToOpenGalleryButton() {
        image_view_edit_profile_fragment.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, OPEN_GALLERY_BUTTON_REQUEST_ID)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("BioText", text_view_bio_edit_profile_fragment.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        text_view_bio_edit_profile_fragment.text = savedInstanceState?.getString("BioText")
    }

    private fun setupEditBioButton() {
        button_edit_bio_edit_profile_fragment.setOnClickListener {
            dialogFragmentAddBio.show(fragmentManager, "DialogFragmentAddBio")
            dialogFragmentAddBio.onAddButtonClick = { header, bio -> editBioTextView(header, bio) }
        }

        text_view_bio_edit_profile_fragment.setOnClickListener {
            dialogFragmentAddBio.show(fragmentManager, "DialogFragmentAddBio")
            dialogFragmentAddBio.onAddButtonClick = { header, bio -> editBioTextView(header, bio) }
        }
    }

    private fun editBioTextView(header: String, bio: String) {
        text_view_bio_edit_profile_fragment.text = bio
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TAKE_PICTURE_BUTTON_REQUEST_ID) {
            if (resultCode == Activity.RESULT_OK) {
                profilePictureBitmap = data?.extras?.get("data") as Bitmap
            }
        }

        if (requestCode == OPEN_GALLERY_BUTTON_REQUEST_ID) {
            if (resultCode == Activity.RESULT_OK) {
                val contentURI = data?.data
                profilePictureBitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, contentURI)
            }
        }
    }
}