package pl.sjmprofil.animaltinder.utilities

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.add_bio_dialog_fragment.*
import pl.sjmprofil.animaltinder.R

class DialogFragmentAddBio : DialogFragment() {

    var onAddButtonClick: ((String, String) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isCancelable = false
        return inflater.inflate(R.layout.add_bio_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_add_bio_dialog_fragment_cancel.setOnClickListener {
            dismiss()
        }

        button_add_bio_dialog_fragment_add.setOnClickListener {
            onAddButtonClick?.invoke(
                    text_view_add_header_dialog_fragment.text.toString(),
                    text_view_add_bio_dialog_fragment.text.toString())
            dismiss()
        }
    }
}