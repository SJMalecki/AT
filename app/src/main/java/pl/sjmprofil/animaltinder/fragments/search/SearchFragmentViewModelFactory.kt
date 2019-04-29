package pl.sjmprofil.animaltinder.fragments.search

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import pl.sjmprofil.animaltinder.repository.ApiRepository
import pl.sjmprofil.animaltinder.utilities.AdvertCacher

@Suppress("UNCHECKED_CAST")
class SearchFragmentViewModelFactory(
    private val apiRepository: ApiRepository,
    private val advertCacher: AdvertCacher
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchFragmentViewModel(apiRepository, advertCacher) as T
    }
}