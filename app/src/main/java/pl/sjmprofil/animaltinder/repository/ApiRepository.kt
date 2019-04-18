package pl.sjmprofil.animaltinder.repository

import android.content.Context
import android.preference.PreferenceManager
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
        getSharedPrefValues()
    }

    private fun getSharedPrefValues() {
        token = sharedPreferences.getString(context.getString(R.string.shared_pref_token_key), "")!!
        email = sharedPreferences.getString(context.getString(R.string.shared_pref_email_key), "")!!
        password = sharedPreferences.getString(context.getString(R.string.shared_pref_password_key), "")!!
    }

    private fun updateSharedPrefPassword(newEmail: String, newPassword: String, newToken: String) {
        val editor = sharedPreferences.edit()
        editor.putString(context.getString(R.string.shared_pref_token_key), newToken)
        editor.putString(context.getString(R.string.shared_pref_email_key), newEmail)
        editor.putString(context.getString(R.string.shared_pref_password_key), newPassword)
        editor.apply()
    }

    // create user
    // Required fields [ email , password, name , surname ]
    suspend fun createUser(user: User): Boolean {

        if (user.email == "" || user.name == "" || user.surname == "" || user.password == "") {
            return false
        }

        val response = apiService.createNewUser(user).await()

        if (response.isSuccessful) {
            val responseBody = response.body()
            val newToken = responseBody?.token

            updateSharedPrefPassword(newEmail = user.email, newPassword = user.password, newToken = newToken!!)
            return true
        }
        return false
    }

    // login user
    // Required fields [ email, password ]
    suspend fun loginUser(user: User): Boolean {
        val response = apiService.loginUser(user, token).await()

        if (response.isSuccessful) {
            val responseBody = response.body()
            val newToken = responseBody?.token

            updateSharedPrefPassword(newEmail = user.email, newPassword = user.password, newToken = newToken!!)
            return true
        }
        return false
    }

    // Get my user info by token identity
    suspend fun getMyUserInfo(): User? {
        val response = apiService.getMyInfo(token).await()

        if (response.isSuccessful) {
            // Response body is User
            return response.body()
        }
        return User()
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
}