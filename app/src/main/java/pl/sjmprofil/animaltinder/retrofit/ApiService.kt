package pl.sjmprofil.animaltinder.retrofit

import android.graphics.Picture
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import pl.sjmprofil.animaltinder.models.Advert
import pl.sjmprofil.animaltinder.models.Reaction
import pl.sjmprofil.animaltinder.models.User
import pl.sjmprofil.animaltinder.models.serverresponses.*
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
    @GET("userlogin")
    fun getMyInfo(@Header("Authorization") token: String): Deferred<Response<UserResponse>>


    @Headers("Content-Type: application/json")
    @GET("adverts")
    fun getAllAdverts(@Header("Authorization") token: String): Deferred<Response<AdvertResponse>>

    @Headers("Content-Type: application/json")
    @POST("reaction")
    fun addMyReactionToAdvert(@Header("Authorization") token: String, @Body reaction: Reaction): Deferred<Response<AdvertResponse>>

    //  Pictures Queries
    @Multipart
    @POST("userpictures")
    fun uploadUserPhoto(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part
    ): Deferred<Response<PictureResponse>>

    @Multipart
    @POST("advertpictures")
    fun advertCreate(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part,
        @Part bio: MultipartBody.Part,
        @Part header: MultipartBody.Part
    ): Deferred<Response<PictureResponse>>

    @Headers("Content-Type: application/json")
    @DELETE("advertcreate")
    fun deleteAdvert(@Header("Authorization") token: String, @Body advert: Advert): Deferred<Response<MessageOnlyResponse>>

    @Headers("Content-Type: application/json")
    @DELETE("usercreate")
    fun deleteUser(@Header("Authorization") token: String): Deferred<Response<MessageOnlyResponse>>

}