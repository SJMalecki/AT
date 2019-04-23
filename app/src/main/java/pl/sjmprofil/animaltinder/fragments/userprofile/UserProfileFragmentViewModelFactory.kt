package pl.sjmprofil.animaltinder.fragments.userprofile

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import pl.sjmprofil.animaltinder.repository.ApiRepository

@Suppress("UNCHECKED_CAST")
class UserProfileFragmentViewModelFactory(private val apiRepository: ApiRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserProfileFragmentViewModel(apiRepository) as T
    }
}