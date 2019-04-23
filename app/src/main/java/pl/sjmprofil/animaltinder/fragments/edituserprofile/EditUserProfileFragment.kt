package pl.sjmprofil.animaltinder.fragments.edituserprofile

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.edit_user_profile_fragment_layout.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.databinding.EditUserProfileFragmentLayoutBinding
import pl.sjmprofil.animaltinder.models.User
import pl.sjmprofil.animaltinder.utilities.DialogFragmentAddBio
import java.io.File

class EditUserProfileFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val editUserProfileFragmentViewModelFactory: EditUserProfileFragmentViewModelFactory by instance()

    private lateinit var bindEditUserProfileFragment: EditUserProfileFragmentLayoutBinding
    private lateinit var editUserProfileFragmentViewModel: EditUserProfileFragmentViewModel

    private val TAKE_PICTURE_BUTTON_REQUEST_ID = 103
    private val OPEN_GALLERY_BUTTON_REQUEST_ID = 104

    private val dialogFragmentAddBio = DialogFragmentAddBio()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()

        Log.i("OnView", "onCreate EditUserProfileFragment called")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindEditUserProfileFragment = DataBindingUtil.inflate(
            inflater, R.layout.edit_user_profile_fragment_layout,
            container, false
        )
        Log.i("OnView", "onCreateView EditUserProfileFragment called")
        return bindEditUserProfileFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editUserProfileFragmentViewModel.getMyData { myUser: User ->
            bindEditUserProfileFragment.user = myUser
            bindEditUserProfileFragment.executePendingBindings()
        }

        Log.i("OnView", "onViewCreated EditUserProfileFragment called")

        setupOpenGalleryButton()
        setupTakePictureButton()
        setupImageToOpenGalleryButton()
        setupEditBioButton()
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
            dialogFragmentAddBio.onAddButtonClick = { text -> editBioTextView(text) }
        }

        text_view_bio_edit_profile_fragment.setOnClickListener {
            dialogFragmentAddBio.show(fragmentManager, "DialogFragmentAddBio")
            dialogFragmentAddBio.onAddButtonClick = { text -> editBioTextView(text) }
        }
    }

    private fun editBioTextView(text: String) {
        text_view_bio_edit_profile_fragment.text = text
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TAKE_PICTURE_BUTTON_REQUEST_ID) {
            if (resultCode == Activity.RESULT_OK) {
                val imageHolder = data?.extras?.get("data") as Bitmap
                Log.i("OnView", "$imageHolder")
                                image_view_edit_profile_fragment.setImageBitmap(imageHolder)
            }
        }

        if (requestCode == OPEN_GALLERY_BUTTON_REQUEST_ID) {
            if (resultCode == Activity.RESULT_OK) {

                val contentURI = data?.data

                Log.i("OnView", contentURI.toString())
                val file = File(contentURI.toString())
                Log.i("OnView", contentURI.toString())
                val selectedUri  = Uri.fromFile(file)
                val bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, contentURI)
                image_view_edit_profile_fragment.setImageBitmap(bitmap)
                editUserProfileFragmentViewModel.postMyNewData(bitmap)
            }
        }
    }
}