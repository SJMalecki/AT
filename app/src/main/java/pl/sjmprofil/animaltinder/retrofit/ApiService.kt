package pl.sjmprofil.animaltinder.retrofit

import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import pl.sjmprofil.animaltinder.models.Reaction
import pl.sjmprofil.animaltinder.models.User
import pl.sjmprofil.animaltinder.models.serverresponses.AdvertResponse
import pl.sjmprofil.animaltinder.models.serverresponses.MessageOnlyResponse
import pl.sjmprofil.animaltinder.models.serverresponses.PictureResponse
import pl.sjmprofil.animaltinder.models.serverresponses.UserResponse
import pl.sjmprofil.animaltinder.models.serverresponses.UsersList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.Part
import retrofit2.http.DELETE
import retrofit2.http.FormUrlEncoded
import retrofit2.http.HTTP
import retrofit2.http.Field

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
    fun getAdvertsWithoutMyReaction(@Header("Authorization") token: String): Deferred<Response<AdvertResponse>>

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

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "advertcreate", hasBody = true)
    fun deleteAdvert(
        @Header("Authorization") token: String,
        @Field("id") advert: Int
    ): Deferred<Response<MessageOnlyResponse>>

    @FormUrlEncoded
    @DELETE("usercreate")
    fun deleteUser(@Header("Authorization") token: String): Deferred<Response<MessageOnlyResponse>>
}