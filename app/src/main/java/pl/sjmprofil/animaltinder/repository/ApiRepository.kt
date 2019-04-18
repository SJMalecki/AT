package pl.sjmprofil.animaltinder.repository

import android.content.Context
import android.preference.PreferenceManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.models.User
import pl.sjmprofil.animaltinder.retrofit.ApiService

class ApiRepository(val context: Context, val apiService: ApiService) {

    // Use shared pref to save data and get data from
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    lateinit var token : String
    lateinit var email : String
    lateinit var password : String

    init {
        updateValuesForSharedPrefs()
    }

    fun updateValuesForSharedPrefs(){
        token = sharedPreferences.getString(context.getString(R.string.shared_pref_token_key), "")!!
        email = sharedPreferences.getString(context.getString(R.string.shared_pref_email_key), "")!!
        password = sharedPreferences.getString(context.getString(R.string.shared_pref_password_key), "")!!
    }

    // create user
    // Required fields [ email , password, name , surname ]

    suspend fun createUser(user: User): Boolean 



    // login user
    // Required fields [ email, password ]

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