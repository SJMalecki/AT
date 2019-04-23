package pl.sjmprofil.animaltinder.repository

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.models.Advert
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

    private fun updateSharedPrefs(newEmail: String, newPassword: String, newToken: String) {
        val editor = sharedPreferences.edit()
        editor.putString(context.getString(R.string.shared_pref_token_key), newToken)
        editor.putString(context.getString(R.string.shared_pref_email_key), newEmail)
        editor.putString(context.getString(R.string.shared_pref_password_key), newPassword)
        Log.d("APIREPO", "UPDATING SHARED PREF USER: $newEmail, $newPassword, $newToken")
        editor.apply()
    }

    fun wrapToken(token: String) = "Bearer $token"

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
            updateSharedPrefs(newEmail = user.email, newPassword = user.password, newToken = newToken)
            updateClassSharedPrefValues()
            return true
        }
        Log.d("APIREPO", "Creating user failed")
        return false
    }

    // login user
    // Required fields [ email, password ]
    suspend fun loginUser(user: User): Boolean {

        val response = apiService.loginUser(user).await()
        val responseBody = response.body()

        if (response.isSuccessful && responseBody?.message == "success") {
            val newToken = responseBody.token

            Log.d("APIREPO", "LOGIN USER: ${user.email}, ${user.password}, $newToken")
            updateSharedPrefs(newEmail = user.email, newPassword = user.password, newToken = newToken)
            updateClassSharedPrefValues()
            return true
        }

        Log.d("APIREPO", "Login user failed")
        return false
    }

    suspend fun getAllUsers(): List<User> {
        val response = apiService.getAllUsers(token).await()

        val responseBody = response.body()

        if (response.isSuccessful && responseBody?.messasge == "success") {
            Log.d("APIREPO", "Getting all users ${responseBody.users}")
            return responseBody.users
        }
        Log.d("APIREPO", "Getting all users, no users")
        return listOf()
    }

    // Get my user info by token identity
    suspend fun getMyUserInfoFromServer(): User {
        val response = apiService.getMyInfo(wrapToken(token)).await()

        if (response.isSuccessful) {
            // Response body is User

            val myUserInstance = response.body()!!
            Log.d("APIREPO", "Getting user info details: ${myUserInstance.user}")
            return myUserInstance.user
        }
        Log.d("APIREPO", "Getting user info, empty user")
        return User()
    }

    fun getUserFromSharedPrefs(): User {
        Log.d("APIREPO", "Getting user from shared prefs: $email, $password")
        return User(email = email, password = password)
    }

    fun saveUserToSharedPrefs(user: User) {
        Log.d("APIREPO", "Saving user in shared prefs: ${user.email}, ${user.password}")
        updateSharedPrefs(newEmail = user.email, newPassword = user.password, newToken = "")
    }

    suspend fun getMyAdverts(): List<Advert> {
        val response = apiService.getMyInfo(wrapToken(token)).await()
        val responseBody = response.body()

        if (response.isSuccessful && responseBody?.message == "success") {
            Log.d("APIREPO", "Getting my adverts ${responseBody.user.myAdverts}")
            return responseBody.user.myAdverts
        }
        Log.d("APIREPO", "Getting my adverts, empty list ${response.isSuccessful} && ${responseBody?.message}")
        return listOf()
    }

    suspend fun createNewAdvert(advert: Advert): Boolean {
        val response = apiService.advertCreate(advert, token).await()
        val responseBody = response.body()

        if (response.isSuccessful && responseBody?.message == "success") {
            Log.d("APIREPO", "Creating new advert success")
            return true
        }
        Log.d("APIREPO", "Creating new Advert failed")
        return false
    }

    suspend fun getAllAdverts(): List<Advert> {
        val response = apiService.getAllAdverts(token).await()

        val responseBody = response.body()

        if (response.isSuccessful && responseBody?.message == "success") {
            Log.d("APIREPO", "Getting all adverts ${responseBody.adverts}")
            return responseBody.adverts
        }
        Log.d("APIREPO", "Getting all adverts empty list")
        return listOf()
    }
}
// change user details (photo and/or bio)
// Required fields [ photo, email ]
// @token required

// change advert details (photo and/or bio)
// Required fields [ photo, advert_id ]
// @token required