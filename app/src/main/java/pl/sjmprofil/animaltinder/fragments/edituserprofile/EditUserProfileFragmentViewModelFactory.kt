package pl.sjmprofil.animaltinder.fragments.edituserprofile

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import pl.sjmprofil.animaltinder.repository.ApiRepository

@Suppress("UNCHECKED_CAST")
class EditUserProfileFragmentViewModelFactory(private val apiRepository: ApiRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditUserProfileFragmentViewModel(apiRepository) as T
    }
}