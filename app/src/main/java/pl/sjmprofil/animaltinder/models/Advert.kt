package pl.sjmprofil.animaltinder.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Advert(
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("bio")
    var bio: String = "",

    @SerializedName("header")
    var header: String = "",

    @SerializedName("picture")
    var picture: String = "",

    @SerializedName("likedby")
    var likedby: List<User> = listOf()
) : Parcelable {

    fun getLikes() = likedby.size.toString()

    fun liked() = likedby.isNotEmpty()
}