package pl.sjmprofil.animaltinder.models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
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
    var picture: String = "",

    @SerializedName("my_adverts")
    var myAdverts: @RawValue List<Advert> = listOf()
): Parcelable
