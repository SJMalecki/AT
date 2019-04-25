package pl.sjmprofil.animaltinder.fragments.edituserprofile

import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.sjmprofil.animaltinder.models.User
import pl.sjmprofil.animaltinder.repository.ApiRepository

class EditUserProfileFragmentViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun getMyData(callback: (User) -> Unit) {
        GlobalScope.launch {

            val myData = apiRepository.getMyUserInfoFromServer()

            withContext(Dispatchers.Main) {
                callback.invoke(myData)
            }
        }
    }

    fun postMyNewData(bitmap: Bitmap){
        GlobalScope.launch(Dispatchers.Default) {

            apiRepository.changeUserPhoto(bitmap)

        }
    }
}