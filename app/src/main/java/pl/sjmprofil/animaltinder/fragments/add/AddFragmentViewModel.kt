package pl.sjmprofil.animaltinder.fragments.add

import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import pl.sjmprofil.animaltinder.models.Advert
import pl.sjmprofil.animaltinder.repository.ApiRepository
import kotlin.coroutines.CoroutineContext

class AddFragmentViewModel(private val apiRepository: ApiRepository) : ViewModel() {
    private val job = Job()
    private val coroutineContext: CoroutineContext get() = job + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    fun addNewAdvert(header: String, bio:String, picture: Bitmap) {
        scope.launch {
            val newAdvert = Advert(bio=bio, header=header)
            apiRepository.createAdvert(bitmap = picture, advert = newAdvert)
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelAllRequests()
    }

    private fun cancelAllRequests() = job.cancel()
}