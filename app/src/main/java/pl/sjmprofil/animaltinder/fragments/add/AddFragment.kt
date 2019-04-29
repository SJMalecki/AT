package pl.sjmprofil.animaltinder.fragments.add

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.add_fragment_layout.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance
import pl.sjmprofil.animaltinder.utilities.DialogFragmentAddBio
import android.graphics.drawable.BitmapDrawable
import androidx.navigation.NavController
import androidx.navigation.Navigation
import pl.sjmprofil.animaltinder.R

class AddFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private val addFragmentViewModelFactory: AddFragmentViewModelFactory by instance()

    private lateinit var addFragmentViewModel: AddFragmentViewModel

    private val TAKE_PICTURE_BUTTON_REQUEST_ID = 101
    private val OPEN_GALLERY_BUTTON_REQUEST_ID = 102

    private val dialogFragmentAddBio = DialogFragmentAddBio()

    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        setupViewModel()
        setupOpenGalleryButton()
        setupTakePictureButton()
        setupImageToOpenGalleryButton()
        setupEditBioButton()
        addNewAdvert()
    }

    private fun setupViewModel() {
        addFragmentViewModel =
            ViewModelProviders
                .of(this, addFragmentViewModelFactory)
                .get(AddFragmentViewModel::class.java)
    }

    private fun setupTakePictureButton() {
        button_take_picture_add_fragment.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, TAKE_PICTURE_BUTTON_REQUEST_ID)
        }
    }

    private fun setupOpenGalleryButton() {
        button_open_gallery_add_fragment.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, OPEN_GALLERY_BUTTON_REQUEST_ID)
        }
    }

    private fun setupImageToOpenGalleryButton() {
        image_view_add_fragment.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, OPEN_GALLERY_BUTTON_REQUEST_ID)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("BioText", text_view_bio_add_fragment.text.toString())
        outState.putString("HeaderText", text_view_add_bio_header.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            text_view_bio_add_fragment.text = savedInstanceState.getString("BioText")
            text_view_add_bio_header.text = savedInstanceState.getString("HeaderText")
        }
    }

    private fun addNewAdvert() {
        button_add_advert_add_fragment.setOnClickListener {
            addFragmentViewModel.addNewAdvert(
                header = text_view_add_bio_header.text.toString(),
                bio = text_view_bio_add_fragment.text.toString(),
                picture = (image_view_add_fragment.drawable as BitmapDrawable).bitmap
            )
            val action = AddFragmentDirections.actionAddFragmentLayoutToUserProfileFragmentLayout()
            navController.navigate(action)
        }
    }

    private fun setupEditBioButton() {
        button_edit_bio_add_fragment.setOnClickListener {
            dialogFragmentAddBio.show(fragmentManager, "DialogFragmentAddBio")
            dialogFragmentAddBio.onAddButtonClick = { header, bio -> editBioTextView(header, bio) }
        }

        text_view_bio_add_fragment.setOnClickListener {
            dialogFragmentAddBio.show(fragmentManager, "DialogFragmentAddBio")
            dialogFragmentAddBio.onAddButtonClick = { header, bio -> editBioTextView(header, bio) }
        }
    }

    private fun editBioTextView(header: String, bio: String) {
        text_view_add_bio_header.text = header
        text_view_bio_add_fragment.text = bio
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TAKE_PICTURE_BUTTON_REQUEST_ID) {
            if (resultCode == Activity.RESULT_OK) {
                val imageHolder = data?.extras?.get("data") as Bitmap
                image_view_add_fragment.setImageBitmap(imageHolder)
            }
        }

        if (requestCode == OPEN_GALLERY_BUTTON_REQUEST_ID) {
            if (resultCode == Activity.RESULT_OK) {

                val contentURI = data?.data
                val bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, contentURI)
                image_view_add_fragment.setImageBitmap(bitmap)
            }
        }
    }
}