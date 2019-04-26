package pl.sjmprofil.animaltinder.fragments.search

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import com.yuyakaido.android.cardstackview.Duration
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeableMethod
import kotlinx.android.synthetic.main.swipe_layout.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.adapters.SwipeDeckAdapter
import pl.sjmprofil.animaltinder.models.Advert

class SearchFragment : Fragment(), KodeinAware, CardStackListener {

    override val kodein by kodein()

    private val searchFragmentViewModelFactory: SearchFragmentViewModelFactory by instance()
    private lateinit var searchFragmentViewModel: SearchFragmentViewModel

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

        val likeButton = like_button

        likeButton.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            card_stack_view.swipe()

            val advert = swipeDeckAdapter.removeFirst()
            advert?.let {
                searchFragmentViewModel.cacheAdvert(it)
                addReaction(it, 1)
            }
        }

        val dislikeButton = skip_button

        dislikeButton.setOnClickListener {

            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            card_stack_view.swipe()

            val advert = swipeDeckAdapter.removeFirst()
            advert?.let {
                searchFragmentViewModel.cacheAdvert(it)
                addReaction(it, 0)
            }
        }

        val rewindButton = rewind_button

        rewindButton.setOnClickListener {
            onCardRewound()
        }
    }

    private fun addReaction(advert: Advert, reaction: Int) {
        searchFragmentViewModel.addReactionToAdvert(advert, reaction)
    }

    private fun initializeAdapterAndManager() {
        manager = CardStackLayoutManager(context, this)
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

    override fun onCardDisappeared(view: View?, position: Int) {
        Log.d("SWIPE", "onCardDisappeared ")
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        Log.d("SWIPE", "onCardDragging")
    }

    override fun onCardSwiped(direction: Direction?) {
        Log.d("SWIPE", "onCardSwiped $direction ")

        val advertToReactTo = swipeDeckAdapter.removeFirst()!!

        searchFragmentViewModel.cacheAdvert(advertToReactTo)

        when (direction) {
            Direction.Right -> searchFragmentViewModel.addReactionToAdvert(advertToReactTo, 1)
            Direction.Left -> searchFragmentViewModel.addReactionToAdvert(advertToReactTo, 0)
            else -> Log.d("SWIPE", "onCardSwiped $direction ")
        }
    }

    override fun onCardCanceled() {
        Log.d("SWIPE", "onCardCanceled ")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        Log.d("SWIPE", "onCardAppeared ")
    }

    override fun onCardRewound() {
        Log.d("SWIPE", "onCardRewound ")
        val cachedAdvert = searchFragmentViewModel.retrieveCachedAdvert()
        cachedAdvert?.let {
            searchFragmentViewModel.removeReaction(it)
            swipeDeckAdapter.insertFirst(it)
        }
    }

    private fun setupViewModel() {
        searchFragmentViewModel =
            ViewModelProviders
                .of(this, searchFragmentViewModelFactory)
                .get(SearchFragmentViewModel::class.java)
    }
}