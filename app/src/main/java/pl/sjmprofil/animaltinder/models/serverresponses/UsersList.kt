package pl.sjmprofil.animaltinder.models.serverresponses

import com.google.gson.annotations.SerializedName
import pl.sjmprofil.animaltinder.models.User

data class UsersList(
    @SerializedName("message")
    var messasge: String = "",

    @SerializedName("users")
    var users: List<User>
)