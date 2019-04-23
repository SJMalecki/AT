package pl.sjmprofil.animaltinder.models

import com.google.gson.annotations.SerializedName

data class Advert(
    @SerializedName("advert_id")
    var id: Int = 0,

    @SerializedName("bio")
    var bio: String = "",

    @SerializedName("header")
    var header: String = "",

    @SerializedName("picture")
    var picture: String = "",

    @SerializedName("likedby")
    var likedby: List<User> = listOf()
)