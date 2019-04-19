package pl.sjmprofil.animaltinder.utilities

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.sjmprofil.animaltinder.R

class DialogFragmentAddBio : DialogFragment() {

    var onAddButtonClick: ((String) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isCancelable = false
        return inflater.inflate(R.layout.fragment_my_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogCancelBtn.setOnClickListener {
            dismiss()
        }

        dialogAddBtn.setOnClickListener {
            onAddButtonClick?.invoke(dialogEditText.text.toString())
            dismiss()
        }
    }
}