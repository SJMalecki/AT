package pl.sjmprofil.animaltinder.fragments.followers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.layout_followers_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.kodein.di.android.support.kodein
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.adapters.RecyclerViewAdapter
import pl.sjmprofil.animaltinder.models.User

class FollowersFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val recyclerViewAdapter: RecyclerViewAdapter by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_followers_fragment, container, false)
    }

    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setupRecycler()
        setupSwipeRefresh()
    }

    private fun setupSwipeRefresh() {
        swipe_refresh_layout_followers_fragment.setOnRefreshListener {
            swipe_refresh_layout_followers_fragment.isRefreshing = true
            updateList()
            updateCallback = {
                swipe_refresh_layout_followers_fragment?.isRefreshing = false
            }
        }
    }

    private fun getList(): List<User>? {
        arguments?.let {
            val safeArgsAdvert = FollowersFragmentArgs.fromBundle(it)
            return safeArgsAdvert.advert.likedby
        }
        return null
    }

    private fun setupRecycler() {
        followers_fragment_recycler_view.adapter = recyclerViewAdapter
        followers_fragment_recycler_view.layoutManager = LinearLayoutManager(context)
        updateList()
        recyclerViewAdapter.itemClickListener = {
            val action = FollowersFragmentDirections.actionFollowersToFollowerDetails(it as User)
            navController.navigate(action)
        }
    }

    private var updateCallback: ((Unit) -> Unit)? = null
    private fun updateList() {
        recyclerViewAdapter.updateList(getList()!!.toMutableList())
        updateCallback?.invoke(Unit)
    }
}