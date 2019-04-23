package pl.sjmprofil.animaltinder.fragments.adverts

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.layout_adverts_fragment.*
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.adapters.RecyclerViewAdapter
import pl.sjmprofil.animaltinder.models.Advert
import pl.sjmprofil.animaltinder.repository.ApiRepository

class AdvertsFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val recyclerViewAdapter: RecyclerViewAdapter by instance()
    private val apiRepository: ApiRepository by instance()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_adverts_fragment, container, false)
    }

    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setupRecycler()

        swipe_refresh_layout_adverts_fragment.setOnRefreshListener {
            swipe_refresh_layout_adverts_fragment.isRefreshing = true
        }
    }

    private fun setupRecycler() {
        adverts_fragment_recycler_view.adapter = recyclerViewAdapter
        adverts_fragment_recycler_view.layoutManager = LinearLayoutManager(context)
        updateRecyclerViewAdapter()
        recyclerViewAdapter.itemClickListener = {
            //add list of followers for advert
            val action = AdvertsFragmentDirections.actionAdvertsToFollowers()
            navController.navigate(action)
        }
    }

    private fun updateRecyclerViewAdapter(): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            val tmp = apiRepository.getMyAdverts()
            withContext(Dispatchers.Main) {
                recyclerViewAdapter.updateList(tmp.toMutableList())
            }
            delay(2000)
            swipe_refresh_layout_adverts_fragment.isRefreshing = false
        }
    }
}