package pl.sjmprofil.animaltinder.fragments.search

import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.sjmprofil.animaltinder.models.Advert
import pl.sjmprofil.animaltinder.repository.ApiRepository
import pl.sjmprofil.animaltinder.utilities.AdvertCacher
import kotlin.coroutines.CoroutineContext

class SearchFragmentViewModel(private val apiRepository: ApiRepository, advertCacher: AdvertCacher) : ViewModel() {

    private val job = Job()
    private val coroutineContext: CoroutineContext get() = job + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    private val cacher = advertCacher

    fun getAdvertsForMe(callback: (List<Advert>) -> Unit) {

        scope.launch {
            val adverts = apiRepository.getAllAdverts()

            withContext(Dispatchers.Main) {
                callback.invoke(adverts)
            }
        }
    }

    fun cacheAdvert(advert: Advert){
        cacher.setNewCached(advert)
    }

    fun retrieveCachedAdvert(): Advert?{
        return cacher.retrieveCached()
    }

    fun removeReaction(advert: Advert){
        scope.launch {
            apiRepository.deleteReaction(advert)
        }
    }

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