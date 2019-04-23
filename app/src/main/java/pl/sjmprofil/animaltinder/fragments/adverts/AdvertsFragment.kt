package pl.sjmprofil.animaltinder.fragments.adverts

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_adverts_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.adapters.RecyclerViewAdapter
import pl.sjmprofil.animaltinder.models.Advert

class AdvertsFragment: Fragment(), KodeinAware {

    override val kodein by kodein()
    private val recyclerViewAdapter: RecyclerViewAdapter by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_adverts_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adverts_fragment_recycler_view.adapter = recyclerViewAdapter
        adverts_fragment_recycler_view.layoutManager = LinearLayoutManager(context)
        val tmp = getList()
        recyclerViewAdapter.updateList(tmp as MutableList<Any>)
    }

    private fun getList(): List<Any> {
        return listOf(
            Advert(
                0,
                "bla bla bla ",
                "bla bla @op.pl",
                "header",
                "http://d3g9pb5nvr3u7.cloudfront.net/authors/539a28913f3c0fd71ed4e43d/2131300937/256.jpg"
            ),
            Advert(
                0,
                "bla bla bla ",
                "bla bla @op.pl",
                "header",
                "http://d3g9pb5nvr3u7.cloudfront.net/authors/539a28913f3c0fd71ed4e43d/2131300937/256.jpg"
            )
        )
    }
}