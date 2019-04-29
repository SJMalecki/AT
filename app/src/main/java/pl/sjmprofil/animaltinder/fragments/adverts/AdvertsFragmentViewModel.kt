package pl.sjmprofil.animaltinder.fragments.adverts

import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.*
import pl.sjmprofil.animaltinder.models.Advert
import pl.sjmprofil.animaltinder.repository.ApiRepository
import kotlin.coroutines.CoroutineContext

class AdvertsFragmentViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    private val job = Job()
    private val coroutineContext: CoroutineContext get() = job + Dispatchers.IO
    private val scope = CoroutineScope(coroutineContext)

    override fun onCleared() {
        super.onCleared()
        cancelAllRequests()
    }

    fun getAdverts(callback: (List<Advert>) -> Unit) {
        scope.launch {
            val adverts = apiRepository.getMyAdverts()
            withContext(Dispatchers.Main){
                callback.invoke(adverts)
            }
        }
    }

    private fun cancelAllRequests() = job.cancel()
}