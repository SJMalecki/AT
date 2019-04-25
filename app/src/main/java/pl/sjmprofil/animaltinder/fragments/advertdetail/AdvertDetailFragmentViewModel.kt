package pl.sjmprofil.animaltinder.fragments.advertdetail

import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.sjmprofil.animaltinder.models.Advert
import pl.sjmprofil.animaltinder.repository.ApiRepository

class AdvertDetailFragmentViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun addReactionToAdvert(advert: Advert, reaction: Int) {
        GlobalScope.launch {
            apiRepository.addReactionToAdvert(advertId = advert.id, reaction = reaction)
        }
    }
}
