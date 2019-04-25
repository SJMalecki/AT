package pl.sjmprofil.animaltinder.models

import com.google.gson.annotations.SerializedName

data class Reaction(
    @SerializedName("advert_id")
    var advert_id: Int = 0,

    @SerializedName("reaction")
    var reaction: Int = 0
)
