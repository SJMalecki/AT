package pl.sjmprofil.animaltinder.repository

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.models.User
import pl.sjmprofil.animaltinder.retrofit.ApiService

class ApiRepository(private val context: Context, private val apiService: ApiService) {

    // Use shared pref to save data and get data from
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    lateinit var token: String
    lateinit var email: String
    lateinit var password: String

    init {
        updateClassSharedPrefValues()
    }

    private fun updateClassSharedPrefValues() {
        token = sharedPreferences.getString(context.getString(R.string.shared_pref_token_key), "")!!
        email = sharedPreferences.getString(context.getString(R.string.shared_pref_email_key), "")!!
        password = sharedPreferences.getString(context.getString(R.string.shared_pref_password_key), "")!!
        Log.d("APIREPO", "UPDATING CLASS SHARED PREF VALUES: $token, $email, $password")
    }

    private fun updateSharedPrefPassword(newEmail: String, newPassword: String, newToken: String) {
        val editor = sharedPreferences.edit()
        editor.putString(context.getString(R.string.shared_pref_token_key), newToken)
        editor.putString(context.getString(R.string.shared_pref_email_key), newEmail)
        editor.putString(context.getString(R.string.shared_pref_password_key), newPassword)
        Log.d("APIREPO", "UPDATING SHARED PREF USER: $newEmail, $newPassword, $newToken")
        editor.apply()
    }

    // create user
    // Required fields [ email , password, name , surname ]
    suspend fun createUser(user: User): Boolean {

        if (user.email == "" || user.name == "" || user.surname == "" || user.password == "") {
            return false
        }

        val response = apiService.createNewUser(user).await()
        val responseBody = response.body()

        if (response.isSuccessful && responseBody?.message == "success") {
            val newToken = responseBody.token

            Log.d("APIREPO", "CREATING NEW USER: ${user.email}, ${user.password}, $newToken")
            updateSharedPrefPassword(newEmail = user.email, newPassword = user.password, newToken = newToken!!)
            updateClassSharedPrefValues()
            return true
        }
        Log.d("APIREPO", "Creating user failed")
        return false
    }

    // login user
    // Required fields [ email, password ]
    suspend fun loginUser(user: User): Boolean {
        val response = apiService.loginUser(user, token).await()
        val responseBody = response.body()

        if (response.isSuccessful && responseBody?.message == "success") {
            val newToken = responseBody.token

            Log.d("APIREPO", "LOGIN USER: ${user.email}, ${user.password}, $newToken")
            updateSharedPrefPassword(newEmail = user.email, newPassword = user.password, newToken = newToken)
            updateClassSharedPrefValues()
            return true
        }
        Log.d("APIREPO", "Login user failed")
        return false
    }

    // Get my user info by token identity
    suspend fun getMyUserInfoFromServer(): User {
        val response = apiService.getMyInfo(token).await()

        if (response.isSuccessful) {
            // Response body is User

            val myUserInstance = response.body()!!
            Log.d("APIREPO", "Getting user info details: ${myUserInstance.email}, ${myUserInstance.password}")
            return myUserInstance
        }
        Log.d("APIREPO", "Getting user info, empty user")
        return User()
    }

    fun getUserFromSharedPrefs(): User {
        Log.d("APIREPO", "Getting user from shared prefs: $email, $password")
        return User(email = email, password = password)
    }
}

// get all users
// Required fields [ ]
// @token required

// create advert
// Required fields [ email, bio]
// @token required

// get advert by email
// Required fields [ email ]
// @token required

// get all adverts
// @token required

// upload user picture
// Required fields [ photo, email ]
// @token required

// upload advert picture
// Required fields [ photo, advert_id ]
// @token required