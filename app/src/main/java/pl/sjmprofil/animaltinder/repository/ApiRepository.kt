package pl.sjmprofil.animaltinder.repository

import android.content.Context
import android.graphics.Bitmap
import android.preference.PreferenceManager
import android.util.Log
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.models.Advert
import pl.sjmprofil.animaltinder.models.Reaction
import pl.sjmprofil.animaltinder.models.User
import pl.sjmprofil.animaltinder.retrofit.ApiService
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

fun wrapToken(token: String) = "Bearer $token"

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

    // create user
    // Required fields [ email , password, name , surname ]
    suspend fun createUser(user: User): Boolean {

        if (user.email == "" || user.name == "" || user.surname == "" || user.password == "") {
            return false
        }

        val response = apiService.createNewUser(user).await()

        val responseBody = response.body()

        Log.d("RESPONSEBODY", "Create new user $responseBody")

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

        Log.d("RESPONSEBODY", "Login User $responseBody")

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

        Log.d("RESPONSEBODY", "Get All Users $responseBody")

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

        val responseBody = response.body()

        Log.d("RESPONSEBODY", "Get My User Info From Server $responseBody")

        if (response.isSuccessful &&  responseBody?.message == "success") {
            // Response body is User

            val myUserInstance = responseBody
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

        Log.d("RESPONSEBODY", "Get my adverts $responseBody")

        if (response.isSuccessful && responseBody?.message == "success") {
            Log.d("APIREPO", "Getting my adverts ${responseBody.user.myAdverts}")
            return responseBody.user.myAdverts
        }
        Log.d("APIREPO", "Getting my adverts, empty list ${response.isSuccessful} && ${responseBody?.message}")
        return listOf()
    }

    suspend fun getAllAdverts(): List<Advert> {
        val response = apiService.getAdvertsWithoutMyReaction(wrapToken(token)).await()

        val responseBody = response.body()

        Log.d("RESPONSEBODY", "Get All Adverts $responseBody")

        if (response.isSuccessful && responseBody?.message == "success") {
            Log.d("APIREPO", "Getting all adverts ${responseBody.adverts}")
            return responseBody.adverts
        }
        Log.d("APIREPO", "Getting all adverts empty list")
        return listOf()
    }

    suspend fun changeUserPhoto(bitmap: Bitmap) {

        val file = File(context.filesDir.path.toString() + "temp")
        val outputStream = BufferedOutputStream(FileOutputStream(file))
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        outputStream.close()

        val requestBody = RequestBody.create(MediaType.parse("image/jpng"), file)
        val multipartBody = MultipartBody.Part.createFormData("photo", "photo", requestBody)
        val response = apiService.uploadUserPhoto(wrapToken(token), photo = multipartBody).await()

        val responseBody = response.body()

        Log.d("RESPONSEBODY", "Change User Photo $responseBody")
    }

    suspend fun createAdvert(bitmap: Bitmap, advert: Advert) {

        val file = File(context.filesDir.path.toString() + "temp")
        val outputStream = BufferedOutputStream(FileOutputStream(file))
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        outputStream.close()

        val requestBody = RequestBody.create(MediaType.parse("image/jpng"), file)
        val multipartBody = MultipartBody.Part.createFormData("photo", "photo", requestBody)
        val multipartBodyAdvertBio = MultipartBody.Part.createFormData("bio", advert.bio)
        val multipartBodyAdvertHeader = MultipartBody.Part.createFormData("header", advert.header)
        val response = apiService.advertCreate(
            wrapToken(token),
            photo = multipartBody,
            bio = multipartBodyAdvertBio,
            header = multipartBodyAdvertHeader
        ).await()

        val responseBody = response.body()

        Log.d("RESPONSEBODY", "Create Advert $responseBody")
    }

    suspend fun addReactionToAdvert(advertId: Int, reaction: Int) {

        val reactionObj = Reaction(advert_id = advertId, reaction = reaction)
        val response = apiService.addMyReactionToAdvert(wrapToken(token), reactionObj).await()
        val responseBody = response.body()

        Log.d("RESPONSEBODY", "Add Reaction To Advert $responseBody")
    }

    suspend fun deleteAdvert(advert: Advert) {
        val response = apiService.deleteAdvert(wrapToken(token), advert.id).await()

        val responseBody = response.body()

        Log.d("RESPONSEBODY", "ADVERT DELETE $responseBody")
    }

    suspend fun deleteUser() {
        apiService.deleteUser(wrapToken(token)).await()
    }
}
// change user details (photo and/or bio)
// Required fields [ photo, email ]
// @token required

// change advert details (photo and/or bio)
// Required fields [ photo, advert_id ]
// @token required