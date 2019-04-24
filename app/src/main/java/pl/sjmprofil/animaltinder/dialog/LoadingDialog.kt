package pl.sjmprofil.animaltinder.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import pl.sjmprofil.animaltinder.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.loading_dialog.view.*

class LoadingDialog : DialogFragment() {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val view = activity?.layoutInflater?.inflate(R.layout.loading_dialog, null)!!

            Glide.with(context!!).load("file:///android_asset/loadingGif.gif").into(view.custom_dialog_loading_image)

            return AlertDialog.Builder(context!!)
                .setView(view)
                .setCancelable(false)
                .create()

        }
    }