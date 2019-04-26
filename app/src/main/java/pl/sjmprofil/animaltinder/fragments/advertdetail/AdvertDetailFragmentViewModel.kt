package pl.sjmprofil.animaltinder.fragments.advertdetail

import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import pl.sjmprofil.animaltinder.models.Advert
import pl.sjmprofil.animaltinder.repository.ApiRepository
import kotlin.coroutines.CoroutineContext

class AdvertDetailFragmentViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    private val job = Job()
    private val coroutineContext: CoroutineContext get() = job + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    fun addReactionToAdvert(advert: Advert, reaction: Int) {
        scope.launch {
            apiRepository.addReactionToAdvert(advertId = advert.id, reaction = reaction)
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelAllRequests()
    }

    private fun cancelAllRequests() = job.cancel()
}
