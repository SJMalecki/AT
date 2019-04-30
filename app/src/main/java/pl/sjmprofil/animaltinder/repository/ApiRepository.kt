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
import pl.sjmprofil.animaltinder.network.safeApiCall
import pl.sjmprofil.animaltinder.retrofit.ApiService
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import pl.sjmprofil.animaltinder.network.Result

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

        val result = safeApiCall { apiService.createNewUser(user).await() }

        when (result) {
            is Result.Success -> {
                if (result.data.message == "success") {
                    Log.d("APIREPO", "Creating user success $user")
                    val newToken = result.data.token
                    updateSharedPrefs(newEmail = user.email, newPassword = user.password, newToken = newToken)
                    updateClassSharedPrefValues()
                    return true
                }
                return false
            }
            else -> {
                Log.d("APIREPO", "Creating user failed")
                return false
            }
        }
    }

    // login user
    // Required fields [ email, password ]
    suspend fun loginUser(user: User): Boolean {

        val result = safeApiCall { apiService.loginUser(user).await() }

        when (result) {
            is Result.Success -> {
                if (result.data.message == "success") {

                    val newToken = result.data.token
                    Log.d("APIREPO", "LOGIN USER: ${user.email}, ${user.password}, $newToken")
                    updateSharedPrefs(newEmail = user.email, newPassword = user.password, newToken = newToken)
                    updateClassSharedPrefValues()
                    return true
                }
                Log.d("APIREPO", "Login user failed")
                return false
            }
            else -> {
                Log.d("APIREPO", "Login user failed")
                return false
            }
        }
    }

    suspend fun getAllUsers(): List<User> {

        val result = safeApiCall { apiService.getAllUsers(token).await() }

        when (result) {
            is Result.Success -> {
                if (result.data.messasge == "success") {

                    Log.d("APIREPO", "Getting all users ${result.data.users}")
                    return result.data.users
                }

                Log.d("APIREPO", "Getting all users, no users")
                return listOf()
            }
            else -> {
                Log.d("APIREPO", "Getting all users failed")
                return listOf()
            }
        }
    }

    // Get my user info by token identity
    suspend fun getMyUserInfoFromServer(): User {

        val result = safeApiCall { apiService.getMyInfo(wrapToken(token)).await() }

        when (result) {
            is Result.Success -> {
                if (result.data.message == "success") {

                    val myUserInstance = result.data.user
                    Log.d("APIREPO", "Getting user info details: $myUserInstance")
                    return myUserInstance
                }

                Log.d("APIREPO", "Getting user info, empty user")
                return User()
            }
            else -> {
                Log.d("APIREPO", "Getting user info, empty user")
                return User()
            }
        }
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

        val result = safeApiCall { apiService.getMyInfo(wrapToken(token)).await() }

        when (result) {
            is Result.Success -> {
                if (result.data.message == "success") {

                    Log.d("APIREPO", "Getting my adverts: ${result.data.user.myAdverts}")
                    return result.data.user.myAdverts
                }

                Log.d("APIREPO", "Getting my adverts, empty list")
                return listOf()
            }
            else -> {
                Log.d("APIREPO", "Getting my adverts, empty list or error")
                return listOf()
            }
        }
    }

    suspend fun getAllAdverts(): List<Advert> {

        val result = safeApiCall { apiService.getAdvertsWithoutMyReaction(wrapToken(token)).await() }

        when (result) {
            is Result.Success -> {
                if (result.data.message == "success") {

                    return result.data.adverts
                }

                Log.d("APIREPO", "Getting adverts for me, empty list")
                return listOf()
            }
            else -> {
                Log.d("APIREPO", "Gettingadverts for me, empty list or error")
                return listOf()
            }
        }
    }

    suspend fun changeUserPhoto(bitmap: Bitmap) {

        val file = File(context.filesDir.path.toString() + "temp")
        val outputStream = BufferedOutputStream(FileOutputStream(file))
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        outputStream.close()

        val requestBody = RequestBody.create(MediaType.parse("image/jpng"), file)
        val multipartBody = MultipartBody.Part.createFormData("photo", "photo", requestBody)

        val result = safeApiCall { apiService.uploadUserPhoto(wrapToken(token), photo = multipartBody).await() }

        when (result) {
            is Result.Success -> {
                if (result.data.message == "success") {

                    Log.d("APIREPO", "Upload success")
                }
            }
            else ->
                Log.d("APIREPO", "Upload failed")
        }
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

        val result = safeApiCall {
            apiService.advertCreate(
                wrapToken(token),
                photo = multipartBody,
                bio = multipartBodyAdvertBio,
                header = multipartBodyAdvertHeader
            ).await()
        }

        when (result) {
            is Result.Success -> {
                if (result.data.message == "success") {

                    Log.d("APIREPO", "Upload advert success")
                }
            }
            else ->
                Log.d("APIREPO", "Upload failed")
        }
    }

    suspend fun addReactionToAdvert(advertId: Int, reaction: Int) {

        val reactionObj = Reaction(advert_id = advertId, reaction = reaction)
        val result = safeApiCall { apiService.addMyReactionToAdvert(wrapToken(token), reactionObj).await() }

        when (result) {
            is Result.Success -> {
                if (result.data.message == "success") {

                    Log.d("APIREPO", "Reaction Added")
                }
            }
            else -> Log.d("APIREPO", "Reaction Add failed")
        }
    }

    suspend fun deleteAdvert(advert: Advert) {

        val result = safeApiCall { apiService.deleteAdvert(wrapToken(token), advert.id).await() }

        when (result) {
            is Result.Success -> {
                if (result.data.message == "success") {

                    Log.d("APIREPO", "Advert Deleted")
                }
            }
            else -> Log.d("APIREPO", "Advert delete failed")
        }
    }

    suspend fun deleteReaction(advert: Advert) {
        val result = safeApiCall { apiService.deleteReaction(wrapToken(token), advertId = advert.id).await() }

        when (result) {
            is Result.Success -> {
                if (result.data.message == "success") {

                    Log.d("APIREPO", "Reaction Deleted")
                }
            }
            else -> Log.d("APIREPO", "Reaction delete failed")
        }
    }

    suspend fun deleteUser() {

        val result = safeApiCall { apiService.deleteUser(wrapToken(token)).await() }

        when (result) {
            is Result.Success -> {
                if (result.data.message == "success") {

                    Log.d("APIREPO", "Reaction Deleted")
                }
            }
            else -> Log.d("APIREPO", "Reaction delete failed")
        }
    }
}
// change user details (photo and/or bio)
// Required fields [ photo, email ]
// @token required

// change advert details (photo and/or bio)
// Required fields [ photo, advert_id ]
// @token required