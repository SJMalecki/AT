package pl.sjmprofil.animaltinder.utilities

import android.support.design.widget.TextInputEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns

class Validator {

    private fun validateFirstLetterCapital(text: TextInputEditText): Boolean =
        text.editableText.isNullOrEmpty() || text.editableText.first().isUpperCase()

    private fun validateEmail(text: TextInputEditText): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(text.text.toString()).matches()

    private fun validateIsEmpty(text: TextInputEditText): Boolean {
        return text.editableText.isNullOrEmpty()
    }

    private fun validateSecondPassword(password1: TextInputEditText, password2: TextInputEditText): Boolean =
        password2.text.toString() == password1.text.toString()


    fun showEmptyError(text: TextInputEditText) {
        text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (validateIsEmpty(text))
                    text.error = "The field can not be empty!"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    fun showTextError(text: TextInputEditText) {
        text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!validateFirstLetterCapital(text))
                    text.error = "Name must start with a capital letter!"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    fun showEmailError(text: TextInputEditText) {
        text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!validateEmail(text))
                    text.error = "Wrong email!"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    fun showSecondPasswordError(password1: TextInputEditText, password2: TextInputEditText) {
        password2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!validateSecondPassword(password1, password2))
                    password2.error = "The passwords given must be the same!"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}