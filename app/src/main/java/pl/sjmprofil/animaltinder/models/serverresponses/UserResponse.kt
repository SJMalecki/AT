package pl.sjmprofil.animaltinder.models.serverresponses

import com.google.gson.annotations.SerializedName
import pl.sjmprofil.animaltinder.models.User

data class UserResponse(

    @SerializedName("message")
    val message: String,

    @SerializedName("token")
    val token: String,

    @SerializedName("user")
    val user: User
)