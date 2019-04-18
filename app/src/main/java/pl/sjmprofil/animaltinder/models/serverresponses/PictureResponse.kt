package pl.sjmprofil.animaltinder.models.serverresponses

import com.google.gson.annotations.SerializedName

data class PictureResponse(

    @SerializedName("message")
    val message: String
)