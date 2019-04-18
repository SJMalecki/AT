package pl.sjmprofil.animaltinder.models.serverresponses

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @SerializedName("message")
    val message: String,

    @SerializedName("token")
    val token: String
)