package pl.sjmprofil.animaltinder

import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import pl.sjmprofil.animaltinder.models.serverresponses.UserResponse

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

data class UserResponse(

    @SerializedName("message")
    val message: String,

    @SerializedName("token")
    val token: String,

    @SerializedName("user")
    val user: User
)

data class User(
    @SerializedName("user_id")
    var id: Int = 0,

    @SerializedName("email")
    var email: String,

    @SerializedName("name")
    var name: String,

    @SerializedName("surname")
    var surname: String
)

data class UserDTO(
    @SerializedName("user_id")
    var id: Int = 0,

    @SerializedName("email")
    var email: String,

    @SerializedName("name")
    var name: String,

    @SerializedName("surname")
    var surname: String,

    @SerializedName("password")
    var password: String
)

data class UsersListDTO(
    var users: List<User>
)

data class ResponseDTO(
    val message: String,
    val token: String
)

interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("users")
    fun getAllUsers(): Deferred<Response<UsersListDTO>>

    @Headers("Content-Type: application/json")
    @POST("usercreate")
    fun createNewUser(@Body user: UserDTO): Deferred<Response<ResponseDTO>>

    @Headers("Content-Type: application/json")
    @POST("userlogin")
    fun logInUser(@Body user: UserDTO): Deferred<Response<ResponseDTO>>

    @Headers("Content-Type: application/json")
    @GET("userlogin")
    fun getMyInfo(@Header("Authorization") token: String): Deferred<Response<UserResponse>>
}

fun setup_sercive(): ApiService {
    val retrofit = Retrofit.Builder()
        .client(OkHttpClient.Builder().build())
        .baseUrl("http://0.0.0.0:5000/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    return retrofit.create(ApiService::class.java)
}

class ApiTest {
    fun getting_all_users_works_correctly() {
        val service = setup_sercive()

        var users : Response<UsersListDTO>

        runBlocking {
            users = service.getAllUsers().await()
            if (users.isSuccessful) {
                val listOfUsers = users.body()?.users
                println(listOfUsers.toString())
            }
        }
    }

    fun sending_user_to_api() {
        val service = setup_sercive()

        val newUser = UserDTO(name = "Antoni", surname = "Macierewicz", email = "antoni.macier@wp.pl", password = "password")

        runBlocking {
            val response = service.createNewUser(newUser).await()
            println(response.body())
        }
    }

    fun loginUser() {
        val service = setup_sercive()

        val newUser = UserDTO(name = "Antoni", surname = "Macierewicz", email = "antoni.macier@wp.pl", password = "password")

        runBlocking {
            val response = service.logInUser(newUser).await()
            println(response.body())

        }
    }

}

fun main() {
    println("Getting all users")
    ApiTest().getting_all_users_works_correctly()
    println("Sending user to api")
    ApiTest().sending_user_to_api()
    println("Logging in user")
    ApiTest().loginUser()

}

fun getHash(password: String): ByteArray {
    var digest: MessageDigest? = null
    try {
        digest = MessageDigest.getInstance("SHA-256")
    } catch (e1: NoSuchAlgorithmException) {
        // TODO Auto-generated catch block
        e1.printStackTrace()
    }

    digest!!.reset()
    return digest!!.digest(password.toByteArray())
}

fun bin2hex(data: ByteArray): String {
    return String.format("%0" + data.size * 2 + "X", BigInteger(1, data))
}