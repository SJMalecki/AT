package pl.sjmprofil.animaltinder.fragments.advertdetail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import pl.sjmprofil.animaltinder.repository.ApiRepository

@Suppress("UNCHECKED_CAST")
class AdvertDetailFragmentViewModelFactory(private val apiRepository: ApiRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AdvertDetailFragmentViewModel(apiRepository) as T
    }
}