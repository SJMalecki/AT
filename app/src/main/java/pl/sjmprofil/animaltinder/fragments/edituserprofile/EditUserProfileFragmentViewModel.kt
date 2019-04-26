package pl.sjmprofil.animaltinder.fragments.edituserprofile

import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.sjmprofil.animaltinder.models.User
import pl.sjmprofil.animaltinder.repository.ApiRepository
import kotlin.coroutines.CoroutineContext

class EditUserProfileFragmentViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    private val job = Job()
    private val coroutineContext: CoroutineContext get() = job + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    fun getMyData(callback: (User) -> Unit) {
        scope.launch {

            val myData = apiRepository.getMyUserInfoFromServer()

            withContext(Dispatchers.Main) {
                callback.invoke(myData)
            }
        }
    }

    fun postMyNewData(bitmap: Bitmap) {
        scope.launch(Dispatchers.Default) {

            apiRepository.changeUserPhoto(bitmap)
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelAllRequests()
    }

    private fun cancelAllRequests() = job.cancel()
}