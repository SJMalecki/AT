package pl.sjmprofil.animaltinder.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.utilities.Validator

class LoginActivity : AppCompatActivity() {

    private val validator = Validator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        register_button.setOnClickListener { startRegisterActivity() }

        with(validator) {
            showEmptyError(login_input_text)
            showEmptyError(password_input_text)
        }
    }

    private fun startRegisterActivity() {
        val intent = RegisterActivity.getIntent(this)
        startActivity(intent)
    }
}