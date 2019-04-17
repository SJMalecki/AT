package pl.sjmprofil.animaltinder

import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

data class User(
    @SerializedName("user_id")
    var id: Int,
    var email: String,
    var hash: String,
    var name: String,
    var surname: String
)

class UsersListDTO(
    var users: List<User>
)

interface ApiService {

    @GET("users")
    fun getAllUsers(): Deferred<Response<UsersListDTO>>

}

class ApiTest {
    @Test
    fun getting_all_users_works_correctly() {
        val retrofit = Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl("https://animtind.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        val service = retrofit.create(ApiService::class.java)

        var users : Response<UsersListDTO>

        runBlocking {
            users = service.getAllUsers().await()
            if (users.isSuccessful) {
                println("List Of users")
                val listOfUsers = users.body()?.users
                println(listOfUsers.toString())
                println("End List Of users")

                assertNotEquals(listOfUsers, arrayOf<User>())
            }
        }

    }
}
