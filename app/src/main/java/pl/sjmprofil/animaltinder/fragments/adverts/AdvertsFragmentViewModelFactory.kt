package pl.sjmprofil.animaltinder.fragments.adverts

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import pl.sjmprofil.animaltinder.repository.ApiRepository

@Suppress("UNCHECKED_CAST")
class AdvertsFragmentViewModelFactory(private val apiRepository: ApiRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AdvertsFragmentViewModel(apiRepository) as T
    }
}
