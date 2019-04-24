package pl.sjmprofil.animaltinder.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdvertParcelable(

    var id: Int = 0,
    var bio: String = "",
    var email: String = "",
    var header: String = "",
    var picture: String = ""
):Parcelable