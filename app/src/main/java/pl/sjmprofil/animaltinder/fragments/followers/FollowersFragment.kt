package pl.sjmprofil.animaltinder.fragments.followers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_followers_fragment.*
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.adapters.FollowersRecyclerViewAdapter
import pl.sjmprofil.animaltinder.models.Advert

class FollowersFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_followers_fragment, container, false)
    }

    lateinit var recyclerViewAdapter: FollowersRecyclerViewAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    private fun setupRecycler() {
        recyclerViewAdapter = FollowersRecyclerViewAdapter()
        followers_fragment_recycler_view.adapter = recyclerViewAdapter
        followers_fragment_recycler_view.layoutManager = LinearLayoutManager(context)
        val tmp = listOf(
            Advert("Jan", "Kowalski", ""),
            Advert("Jan", "Kowals ki", ""),
            Advert("Jan1", "Kowalski1", ""),
            Advert("Jan2", "Kowalski5", ""),
            Advert("Jan", "Kowalski", ""),
            Advert("Jan", "Kowals ki", ""),
            Advert("Jan1", "Kowalski1", ""),
            Advert("Jan2", "Kowalski5", ""),
            Advert("Jan", "Kowalski", ""),
            Advert("Jan", "Kowals ki", ""),
            Advert("Jan1", "Kowalski1", ""),
            Advert("Jan2", "Kowalski5", "")
        )
        recyclerViewAdapter.updateList(tmp as MutableList<Advert>)
    }

}