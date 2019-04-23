package pl.sjmprofil.animaltinder.fragments.userprofile

import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.sjmprofil.animaltinder.models.User
import pl.sjmprofil.animaltinder.repository.ApiRepository

class UserProfileFragmentViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun getMyData(callback: (User) -> Unit) {
        GlobalScope.launch {

            val myData = apiRepository.getMyUserInfoFromServer()

            withContext(Dispatchers.Main) {
                callback.invoke(myData)
            }
        }
    }
}