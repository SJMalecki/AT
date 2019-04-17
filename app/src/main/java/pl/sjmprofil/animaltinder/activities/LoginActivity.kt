package pl.sjmprofil.animaltinder.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import pl.sjmprofil.animaltinder.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        register_button.setOnClickListener { startRegisterActivity() }

    }

    private fun startRegisterActivity() {
        val intent = RegisterActivity.getIntent(this)
        startActivity(intent)
    }

}