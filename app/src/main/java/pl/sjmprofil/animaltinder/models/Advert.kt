package pl.sjmprofil.animaltinder.models

import com.google.gson.annotations.SerializedName

data class Advert(
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("bio")
    var bio: String = "",

    @SerializedName("email")
    var email: String = "",

    @SerializedName("header")
    var header: String = "",

    @SerializedName("picture")
    var picture: String = ""
)