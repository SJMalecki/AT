package pl.sjmprofil.animaltinder.fragments.search

import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.sjmprofil.animaltinder.models.Advert
import pl.sjmprofil.animaltinder.repository.ApiRepository

class SearchFragmentViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun addReactionToAdvert(advertId: Int, reaction: Int){

        GlobalScope.launch {
            apiRepository.addReactionToAdvert(advertId, reaction)
        }
    }

    fun getAdvertsForMe(callback: (List<Advert>) -> Unit){

        GlobalScope.launch {
            val adverts = apiRepository.getAllAdverts()

            withContext(Dispatchers.Main){
                callback.invoke(adverts)
            }
        }
    }
}