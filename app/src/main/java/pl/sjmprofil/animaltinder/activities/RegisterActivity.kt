package pl.sjmprofil.animaltinder.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import pl.sjmprofil.animaltinder.R
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

        with(validator) {
            showNameError(name_input_text)
            showSurnameError(surname_input_text)
            showEmailError(email_input_text)
            showSecondPasswordError(password1_input_text, password2_input_text)

            register_button2.setOnClickListener {
            if(validateAllFields(name_input_text, surname_input_text, email_input_text, password1_input_text, password2_input_text))
                Toast.makeText(this@RegisterActivity, "Fields can not be empty!", Toast.LENGTH_SHORT).show()}
        }




    }
}