package pl.sjmprofil.animaltinder.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.dialog.LoadingDialog
import pl.sjmprofil.animaltinder.utilities.Validator

class RegisterActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }

    private val validator = Validator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        activity_register_register_button2.setOnClickListener { manageOnClick() }

        with(validator) {
            showTextError(activity_register_name_input_text)
            showTextError(activity_register_surname_input_text)
            showEmailError(activity_register_email_input_text)
            showSecondPasswordError(activity_register_password1_input_text, activity_register_password2_input_text)

            showEmptyError(activity_register_password1_input_text)
            showEmptyError(activity_register_password2_input_text)
            showEmptyError(activity_register_name_input_text)
            showEmptyError(activity_register_surname_input_text)
            showEmptyError(activity_register_email_input_text)
        }
    }

    private fun prepareCustomDialog(): LoadingDialog {
        return LoadingDialog()
    }

    private fun manageOnClick() {
        val dialog = prepareCustomDialog()
        dialog.show(supportFragmentManager, "TAG")
    }
}
