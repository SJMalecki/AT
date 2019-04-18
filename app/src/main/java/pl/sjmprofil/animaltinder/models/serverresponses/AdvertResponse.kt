package pl.sjmprofil.animaltinder.models.serverresponses

import com.google.gson.annotations.SerializedName
import pl.sjmprofil.animaltinder.models.Advert

data class AdvertResponse(

    @SerializedName("message")
    val message: String,

    @SerializedName("adverts")
    val adverts: List<Advert>
)