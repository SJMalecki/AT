package pl.sjmprofil.animaltinder.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.search_activity_layout.*
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.adapters.SwipingViewPagerAdapter
import pl.sjmprofil.animaltinder.models.Advert
import pl.sjmprofil.animaltinder.repository.ApiRepository

class SearchActivity :AppCompatActivity(), KodeinAware{

    override val kodein by org.kodein.di.android.kodein()

    private val apiRepository: ApiRepository by instance()

    lateinit var pagerAdapter: SwipingViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity_layout)

        pagerAdapter = SwipingViewPagerAdapter(supportFragmentManager)
        getAdverts { listOfAdverts -> pagerAdapter.swapData(listOfAdverts) }
        search_view_pager.adapter = pagerAdapter
        search_view_pager.currentItem = pagerAdapter.count/2
    }

    private fun getAdverts(callback: (List<Advert>) -> Unit) {

        GlobalScope.launch {
            val listOfAdverts = apiRepository.getAllAdverts()

            withContext(Dispatchers.Main){
                callback.invoke(listOfAdverts)
                println(listOfAdverts)
            }
        }

    }
}