package pl.sjmprofil.animaltinder.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.dialog.LoadingDialog
import pl.sjmprofil.animaltinder.repository.ApiRepository
import pl.sjmprofil.animaltinder.utilities.Validator

class LoginActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val validator: Validator by instance()

    private val apiRepository: ApiRepository by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        activity_login_register_button.setOnClickListener { startRegisterActivity() }
        activity_login_login_button.setOnClickListener { manageOnClick() }

//        with(validator) {
//            showEmptyError(activity_login_login_input_text)
//            showEmptyError(activity_login_password_input_text)
//        }

        checkForExistingUser()
    }

    private fun checkForExistingUser() {

        GlobalScope.launch {
            val mySavedUser = apiRepository.getUserFromSharedPrefs()

            withContext(Dispatchers.Main) {
                mySavedUser.let {
                    activity_login_login_input_text.setText(it.email)
                    activity_login_password_input_text.setText(it.password)
                }
            }
        }
    }

    private fun startMainActivity() {
        val intent = MainActivity.getIntent(this)
        startActivity(intent)
    }


    private fun startRegisterActivity() {
        val intent = RegisterActivity.getIntent(this)
        startActivity(intent)
    }

    private fun prepareCustomDialog(): LoadingDialog {
        return LoadingDialog()
    }

    private fun manageOnClick() {
        authenticateUserAndMoveToMainActivity()
    }

    private fun authenticateUserAndMoveToMainActivity() {

        GlobalScope.launch {

            val dialog = prepareCustomDialog()
            dialog.show(supportFragmentManager, "TAG")
            val user = apiRepository.getUserFromSharedPrefs()
            val loginStatus = apiRepository.loginUser(user)

            if (loginStatus) {
                dialog.dismiss()
                startMainActivity()
            } else {
                dialog.dismiss()
            }
        }
    }
}