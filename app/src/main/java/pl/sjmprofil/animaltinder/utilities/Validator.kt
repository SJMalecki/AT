package pl.sjmprofil.animaltinder.utilities

import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TextInputEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns

class Validator {

    private fun validateAllFieldsIsNullOrEmpty(
        text1: TextInputEditText,
        text2: TextInputEditText,
        text3: TextInputEditText,
        text4: TextInputEditText,
        text5: TextInputEditText
    ): Boolean =
        text1.text.isNullOrEmpty() || text2.text.isNullOrEmpty() || text3.text.isNullOrEmpty() || text4.text.isNullOrEmpty() || text5.text.isNullOrEmpty()


    fun validateAllFields(
        text1: TextInputEditText,
        text2: TextInputEditText,
        text3: TextInputEditText,
        text4: TextInputEditText,
        text5: TextInputEditText
    ): Boolean = validateName(text1) || validateSurname(text2) || validateEmail(text3) || validateSecondPassword(
        text4,
        text5
    ) || validateAllFieldsIsNullOrEmpty(text1, text2, text3, text4, text5)


    private fun validateName(text: TextInputEditText): Boolean =
        text.editableText.isNullOrEmpty() || text.editableText.first().isUpperCase()

    private fun validateSurname(text: TextInputEditText): Boolean =
        text.editableText.isNullOrEmpty() || text.editableText.first().isUpperCase()

    private fun validateEmail(text: TextInputEditText): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(text.text.toString()).matches()

//    private fun validateFirstPassword(text: TextInputEditText): Boolean {
//        return text.editableText.isNullOrEmpty()
//    }

    private fun validateSecondPassword(password1: TextInputEditText, password2: TextInputEditText): Boolean =
        password2.text.toString() == password1.text.toString()

    fun showNameError(text: TextInputEditText) {
        text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!validateName(text))
                    text.error = "Name must start with a capital letter!"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    fun showSurnameError(text: TextInputEditText) {
        text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!validateSurname(text))
                    text.error = "Surname must start with a capital letter!"
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

//    fun showFirstPasswordError(text: TextInputEditText) {
//        text.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                if (validateFirstPassword(text))
//                    text.error = "The password can not be empty!"
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//        })
//    }

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