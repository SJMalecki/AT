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
    }

//    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private fun setupRecycler() {
//        recyclerViewAdapter = RecyclerViewAdapter()
        followers_fragment_recycler_view.adapter = recyclerViewAdapter
        followers_fragment_recycler_view.layoutManager = LinearLayoutManager(context)
        val tmp = getList()
        recyclerViewAdapter.updateList(tmp as MutableList<Any>)
        recyclerViewAdapter.itemClickListener = {
            val action = FollowersFragmentDirections.actionFollowersToFollowerDetails(it as User)
            navController.navigate(action)
        }
    }

    private fun getList(): List<Any> {
        return listOf(
            User(
                0,
                "email@op.pl",
                "Taylor",
                "Swift",
                "1234",
                "https://static.wizaz.pl/resize/var/ezdemo_site/storage/images/fryzury/lob-najmodniejsza-fryzura-sezonu/lob-taylor-swift/120930-1-pol-PL/Lob-Taylor-Swift.jpg?width=256&height=256"
            ),
            User(
                0,
                "email@op.pl",
                "Taylor",
                "Swift",
                "1234",
                "http://www.songnotes.cc/images/artists/TaylorSwift.jpg"
            )

        )
    }

}