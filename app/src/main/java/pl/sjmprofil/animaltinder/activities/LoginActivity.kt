package pl.sjmprofil.animaltinder.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.dialog.LoadingDialog
import pl.sjmprofil.animaltinder.utilities.Validator

class LoginActivity : AppCompatActivity(), KodeinAware{

    override val kodein by kodein()

    private val validator: Validator by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        activity_login_register_button.setOnClickListener { startRegisterActivity() }
        activity_login_login_button.setOnClickListener { manageOnClick() }

//        with(validator) {
//            showEmptyError(activity_login_login_input_text)
//            showEmptyError(activity_login_password_input_text)
//        }
    }

    private fun startRegisterActivity() {
        val intent = RegisterActivity.getIntent(this)
        startActivity(intent)
    }

    private fun prepareCustomDialog(): LoadingDialog {
        return LoadingDialog()
    }

    private fun manageOnClick(){
        val dialog = prepareCustomDialog()
        dialog.show(supportFragmentManager, "TAG")
    }
}