package pl.sjmprofil.animaltinder.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.search_activity_layout.*
import kotlinx.android.synthetic.main.search_fragment_slide.*
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.adapters.SwipingViewPagerAdapter
import pl.sjmprofil.animaltinder.models.AdvertParcelable

class SearchActivity :AppCompatActivity(){

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity_layout)

        val advert1 = AdvertParcelable( header="Bobik", bio="Rezolutny Bobik SZNAUCEREK mix adopcja. BOBIK to okolo 1,5 letni psiak w typie sznaucera , ktory stracil dom poniewaz za bardzo kochal czlowieka", picture = "https://static.boredpanda.com/blog/wp-content/uploads/2018/07/5b557f4560e45_-2RQLO3QGIV9VGgpwIBSpnDKfh4V50mNGqzt2ghnlfo__700.jpg" )
        val advert2 = AdvertParcelable( header="Etna", bio="ETNA - wesola, ogromnie przyjacielska i cudowna sunia Trafila do schroniska chyba przez przypadek. Byc moze sie komus zgubila?", picture = "https://i.pinimg.com/originals/5a/24/c4/5a24c4c8dbd4485bba4155527f6a166e.jpg")
        val advert3 = AdvertParcelable(header="Carmen", bio="Carmen... Z sercem do ludzi. Sunia urodzona w 2009 roku, od maja 2015 pod opieke fundacji. Bardzo spragniona czlowieka", picture="https://i.pinimg.com/474x/e3/2a/0f/e32a0f799d10788a089fea7a40489da1--funny-dogs-funny-animals.jpg")
         val advert4 = AdvertParcelable( header="Czako", bio="Kochany Czako, kotolubny i psolubny psiak ADOPCJA. Wesoly, kochany Czako szuka domku! Czako to okolo 5/6 letni pies ktory wazy 40 kg.", picture = "https://i.ytimg.com/vi/PpnUX9emtIU/maxresdefault.jpg")
        val advert5 = AdvertParcelable( header="Lajka", bio="Lajka nigdy nie miala prawdziwej rodziny.Odebrana interwencyjnie przez inspektorow OTOZ Animals z fatalnych warunkow, dzis odzyskuje wiare w ludzi.", picture = "https://static.boredpanda.com/blog/wp-content/uploads/2018/07/Funny-Dog-Balloons-Ceiling-4-5b55c9caf20f0__700.jpg")
        val adverts = arrayListOf(advert1, advert2, advert3, advert4, advert5)
        val pagerAdapter = SwipingViewPagerAdapter(supportFragmentManager,adverts)

        search_view_pager.adapter = pagerAdapter
        search_view_pager.currentItem = pagerAdapter.count/2
    }
}