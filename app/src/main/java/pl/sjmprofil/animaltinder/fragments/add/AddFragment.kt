package pl.sjmprofil.animaltinder.fragments.add

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.add_fragment_layout.*
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.utilities.DialogFragmentAddBio

class AddFragment : Fragment() {

    private val TAKE_PICTURE_BUTTON_REQUEST_ID = 101
    private val OPEN_GALLERY_BUTTON_REQUEST_ID = 102

    private val dialogFragmentAddBio = DialogFragmentAddBio()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOpenGalleryButton()
        setupTakePictureButton()
        setupImageToOpenGalleryButton()
        setupEditBioButton()
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
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        text_view_bio_add_fragment.text = savedInstanceState?.getString("BioText")
    }

    private fun setupEditBioButton() {
        button_edit_bio_add_fragment.setOnClickListener {
            dialogFragmentAddBio.show(fragmentManager, "DialogFragmentAddBio")
            dialogFragmentAddBio.onAddButtonClick = { text -> editBioTextView(text) }
        }

        text_view_bio_add_fragment.setOnClickListener{
            dialogFragmentAddBio.show(fragmentManager, "DialogFragmentAddBio")
            dialogFragmentAddBio.onAddButtonClick = { text -> editBioTextView(text) }
        }
    }

    private fun editBioTextView(text: String) {
        text_view_bio_add_fragment.text = text
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