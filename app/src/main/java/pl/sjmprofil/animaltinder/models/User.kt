package pl.sjmprofil.animaltinder.models
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id")
    var id: Int = 0,

    @SerializedName("email")
    var email: String = "",

    @SerializedName("name")
    var name: String  = "",

    @SerializedName("surname")
    var surname: String = "",

    @SerializedName("password")
    var password: String = "",

    @SerializedName("picture")
    var picture: String = ""
)
