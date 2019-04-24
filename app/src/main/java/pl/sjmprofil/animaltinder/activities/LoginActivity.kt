package pl.sjmprofil.animaltinder.activities

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.dialog.LoadingDialog
import pl.sjmprofil.animaltinder.models.User
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
        activity_login_login_button.setOnClickListener { manageOnClick().also { hideKeyboard(
            currentFocus
        ) } }

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

    private fun makeSnackbar(string: String) {
        val snackbar = Snackbar.make(activity_login_root_layout, string, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }

    private fun authenticateUserAndMoveToMainActivity() {
        GlobalScope.launch {

            val dialog = prepareCustomDialog()
            dialog.show(supportFragmentManager, "TAG")

            val user = User(
                email = activity_login_login_input_text.text.toString(),
                password = activity_login_password_input_text.text.toString()
            )

            apiRepository.saveUserToSharedPrefs(user)
            val loginStatus = apiRepository.loginUser(user)

            if (loginStatus) {
                withContext(Dispatchers.Main) {
                    dialog.dismiss()
                    startMainActivity()
                }
            } else {
                withContext(Dispatchers.Main) {
                    dialog.dismiss()
                    makeSnackbar("Authentication Failed")
                }
            }
        }
    }


    private fun hideKeyboard(view: View?) {
        view?.let{
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}