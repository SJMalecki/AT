package pl.sjmprofil.animaltinder.retrofit

import android.graphics.Picture
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import pl.sjmprofil.animaltinder.models.Advert
import pl.sjmprofil.animaltinder.models.User
import pl.sjmprofil.animaltinder.models.serverresponses.AdvertResponse
import pl.sjmprofil.animaltinder.models.serverresponses.PictureResponse
import pl.sjmprofil.animaltinder.models.serverresponses.UserResponse
import pl.sjmprofil.animaltinder.models.serverresponses.UsersList
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    //  User Queries
    @Headers("Content-Type: application/json")
    @GET("users")
    fun getAllUsers(@Header("Authorization") token: String): Deferred<Response<UsersList>>

    @Headers("Content-Type: application/json")
    @POST("usercreate")
    fun createNewUser(@Body user: User): Deferred<Response<UserResponse>>

    @Headers("Content-Type: application/json")
    @POST("userlogin")
    fun loginUser(@Body user: User): Deferred<Response<UserResponse>>

    @Headers("Content-Type: application/json")
    @POST("myinfo")
    fun getMyInfo(@Header("Authorization") token: String): Deferred<Response<User>>

    //  Advert Queries
    @Headers("Content-Type: application/json")
    @POST("advertcreate")
    fun advertCreate(@Body advert: Advert, @Header("Authorization") token: String): Deferred<Response<UserResponse>>

    @Headers("Content-Type: application/json")
    @POST("advertfind")
    fun advertFind(@Body user: User, @Header("Authorization") token: String): Deferred<Response<AdvertResponse>>

    @Headers("Content-Type: application/json")
    @GET("adverts")
    fun getAllAdverts(@Header("Authorization") token: String): Deferred<Response<AdvertResponse>>

    //  Pictures Queries
    @Multipart
    @POST("photoupload")
    fun uploadUserPhoto(
        @Part("email") email: String,
        @Part("photo") photo: MultipartBody.Part
    ): Deferred<Response<PictureResponse>>
}