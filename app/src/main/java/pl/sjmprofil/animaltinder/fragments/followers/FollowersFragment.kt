package pl.sjmprofil.animaltinder.fragments.followers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_followers_fragment.*
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.adapters.RecyclerViewAdapter
import pl.sjmprofil.animaltinder.models.Advert
import pl.sjmprofil.animaltinder.models.User

class FollowersFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_followers_fragment, container, false)
    }

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    private fun setupRecycler() {
        recyclerViewAdapter = RecyclerViewAdapter()
        followers_fragment_recycler_view.adapter = recyclerViewAdapter
        followers_fragment_recycler_view.layoutManager = LinearLayoutManager(context)
        val tmp = listOf(
            Advert(
                0,
                "bla bla bla ",
                "bla bla @op.pl",
                "header",
                "http://d3g9pb5nvr3u7.cloudfront.net/authors/539a28913f3c0fd71ed4e43d/2131300937/256.jpg"
            ),
            User(
                0,
                "email@op.pl",
                "Taylor",
                "Swift",
                "1234",
                "https://static.wizaz.pl/resize/var/ezdemo_site/storage/images/fryzury/lob-najmodniejsza-fryzura-sezonu/lob-taylor-swift/120930-1-pol-PL/Lob-Taylor-Swift.jpg?width=256&height=256"
            ),
            Advert(
                0,
                "bla bla bla ",
                "bla bla @op.pl",
                "header",
                "http://d3g9pb5nvr3u7.cloudfront.net/authors/539a28913f3c0fd71ed4e43d/2131300937/256.jpg"
            ),
            User(
                0,
                "email@op.pl",
                "Taylor",
                "Swift",
                "1234",
                "https://static.wizaz.pl/resize/var/ezdemo_site/storage/images/fryzury/lob-najmodniejsza-fryzura-sezonu/lob-taylor-swift/120930-1-pol-PL/Lob-Taylor-Swift.jpg?width=256&height=256"
            )

        )



        recyclerViewAdapter.updateList(tmp as MutableList<Any>)
    }

}