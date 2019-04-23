package pl.sjmprofil.animaltinder

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import pl.sjmprofil.animaltinder.models.User
import pl.sjmprofil.animaltinder.retrofit.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertEquals

fun setup_sercive(): ApiService {
    val retrofit = Retrofit.Builder()
        .client(OkHttpClient.Builder().build())
        .baseUrl("https://animtind.herokuapp.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    return retrofit.create(ApiService::class.java)
}

class ApiTest {

    lateinit var token: String

    fun getting_all_users() {
        val service = setup_sercive()

        runBlocking {

            val response = service.getAllUsers(token).await()
            if (response.isSuccessful) {
                val responseBody = response.body()
                val listOfUsers = responseBody?.users
                println(listOfUsers.toString())
            }
        }

    }

    fun sending_user_to_api() {
        val service = setup_sercive()

        val newUser = User(name = "Antoni", surname = "Macierewicz", email = "antoni.macier@wp.pl", password = "antoni")

        runBlocking {
            val response = service.createNewUser(newUser).await()
            val responseBody = response.body()

            println(responseBody)

//            assertEquals(responseBody?.message, "failure")
        }

    }

    fun loginUser() {
        val service = setup_sercive()

        val newUser = User(name = "Antoni", surname = "Macierewicz", email = "antoni.macier@wp.pl", password = "antoni")

        runBlocking {
            val response = service.loginUser(newUser).await()
            val responseBody = response.body()

            token = responseBody!!.token
            println(responseBody)

//            assertEquals(responseBody?.message, "success")
        }
    }

}

fun main() {

    val api = ApiTest()

    println("Sending user to api")
    api.sending_user_to_api()
    println("Logging in user")
    api.loginUser()
    println("Getting all users")
    api.getting_all_users()

}
