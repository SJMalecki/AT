package pl.sjmprofil.animaltinder.fragments.adverts

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.layout_adverts_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
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
    private val advertsFragmentViewModelFactory: AdvertsFragmentViewModelFactory by instance()
    private lateinit var advertsFragmentViewModel: AdvertsFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_adverts_fragment, container, false)
    }

    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        navController = Navigation.findNavController(view)
        setupRecycler()
        setupSwipeRefresh()
    }

    private fun setupViewModel() {
        advertsFragmentViewModel =
            ViewModelProviders.of(this, advertsFragmentViewModelFactory).get(AdvertsFragmentViewModel::class.java)
    }

    private fun setupSwipeRefresh() {
        swipe_refresh_layout_adverts_fragment.setOnRefreshListener {
            swipe_refresh_layout_adverts_fragment.isRefreshing = true
            updateRecyclerViewAdapter()
            swipeRefreshUpdateCallback = {
                swipe_refresh_layout_adverts_fragment?.isRefreshing = false
            }
        }
    }

    private fun setupRecycler() {
        adverts_fragment_recycler_view.adapter = recyclerViewAdapter
        adverts_fragment_recycler_view.layoutManager = LinearLayoutManager(context)
        updateRecyclerViewAdapter()
        recyclerViewAdapter.itemClickListener = {
            val action = AdvertsFragmentDirections.actionAdvertsToFollowers(it as Advert)
            navController.navigate(action)
        }
        swipeHandlerSetup()
    }

    private fun swipeHandlerSetup() {
        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder1: RecyclerView.ViewHolder,
                viewHolder2: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                GlobalScope.launch(Dispatchers.IO) {
                    apiRepository.deleteAdvert((recyclerViewAdapter.getItemAt(viewHolder.adapterPosition) as Advert))
                    withContext(Dispatchers.Main) {
                        recyclerViewAdapter.removeAt(viewHolder.adapterPosition)
                    }
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(adverts_fragment_recycler_view)
    }

    private var swipeRefreshUpdateCallback: ((Unit) -> Unit)? = null
    private fun updateRecyclerViewAdapter() {
        advertsFragmentViewModel.getAdverts {
            recyclerViewAdapter.updateList(it.toMutableList())
            swipeRefreshUpdateCallback?.invoke(Unit)
        }
    }
}