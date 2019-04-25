package pl.sjmprofil.animaltinder.fragments.search

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeableMethod
import kotlinx.android.synthetic.main.advert_detail_layout.*
import kotlinx.android.synthetic.main.swipe_layout.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.adapters.SwipeDeckAdapter
import pl.sjmprofil.animaltinder.models.Advert

class SearchFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private val searchFragmentViewModelFactory: SearchFragmentViewModelFactory by instance()
    private lateinit var searchFragmentViewModel: SearchFragmentViewModel

//    private val manager: CardStackLayoutManager by instance()
    private val swipeDeckAdapter: SwipeDeckAdapter by instance()

    private lateinit var manager: CardStackLayoutManager

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setupViewModel()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.swipe_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        initializeAdapterAndManager()
        initializeCardStack()
        setupCallbackOnRecycler()
        loadAdverts()
        setupButtonListeners()
    }

    private fun setupButtonListeners() {

        like_button.setOnClickListener {
            val advert = swipeDeckAdapter.removeFirst()
            advert?.let {
                addReaction(advert, 1)
                like_button.isPressed = true
                skip_button.isPressed = false
            }
        }

        skip_button.setOnClickListener {
            val advert = swipeDeckAdapter.removeFirst()
            advert?.let {
                addReaction(advert, 0)
                like_button.isPressed = false
                skip_button.isPressed = true
            }
        }
    }


    private fun addReaction(advert: Advert, reaction: Int){
        searchFragmentViewModel.addReactionToAdvert(advert, reaction)
    }

    private fun initializeAdapterAndManager() {
        manager = CardStackLayoutManager(context)
        card_stack_view.layoutManager = manager
        card_stack_view.adapter = swipeDeckAdapter
    }

    private fun initializeCardStack() {
        with(manager) {
            setStackFrom(StackFrom.None)
            setVisibleCount(2)
            setTranslationInterval(8.0f)
            setScaleInterval(0.95f)
            setSwipeThreshold(0.3f)
            setMaxDegree(20.0f)
            setDirections(Direction.HORIZONTAL)
            setCanScrollHorizontal(true)
            setCanScrollVertical(true)
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
            card_stack_view.itemAnimator.apply {
                if (this is DefaultItemAnimator) {
                    supportsChangeAnimations = false
                }
            }
        }
    }

    private fun loadAdverts() {
        searchFragmentViewModel.getAdvertsForMe { adverts -> swipeDeckAdapter.swapData(adverts) }
    }

    private fun setupCallbackOnRecycler() {
        swipeDeckAdapter.openDetailsCallback = { advert -> navigateToDetailFragment(advert) }
    }

    private fun navigateToDetailFragment(advert: Advert) {
        val action = SearchFragmentDirections.actionSearchFragmentToAdvertDetails(advert)
        navController.navigate(action)
    }

    private fun setupViewModel() {
        searchFragmentViewModel =
            ViewModelProviders
                .of(this, searchFragmentViewModelFactory)
                .get(SearchFragmentViewModel::class.java)
    }
}