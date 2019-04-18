package pl.sjmprofil.animaltinder.retrofit

import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import pl.sjmprofil.animaltinder.models.Advert
import pl.sjmprofil.animaltinder.models.User
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // User Queries
    @Headers("Content-Type: application/json")
    @GET("users")
    fun getAllUsers(@Header("Authorization") token: String): Deferred<Response<UsersListDTO>>

    @Headers("Content-Type: application/json")
    @POST("usercreate")
    fun createNewUser(@Body user: User): Deferred<Response<UserResponseDTO>>

    @Headers("Content-Type: application/json")
    @POST("userlogin")
    fun loginUser(@Body user: User): Deferred<Response<UserResponseDTO>>

    // Advert Queries
    @Headers("Content-Type: application/json")
    @POST("advertcreate")
    fun advertCreate(@Body advert: Advert, @Header("Authorization") token: String): Deferred<Response<UserResponseDTO>>

//    @Headers("Content-Type: application/json")
//    @POST("advertfind")
//    fun advertFind(@Body user: User, @Header("Authorization") token: String): Deferred<Response<AdvertResponseDTO>>
//
//    @Headers("Content-Type: application/json")
//    @GET("adverts")
//    fun getAllAdverts(@Header("Authorization") token: String): Deferred<Response<AdvertResponseDTO>>
//
//    // Pictures Queries
//    @Multipart
//    @POST("photoupload")
//    fun uploadPhoto(
//        @Part("email") email: String,
//        @Part("photo") photo: MultipartBody.Part
//    ): Deferred<Response<PictureResponseDTO>>
}