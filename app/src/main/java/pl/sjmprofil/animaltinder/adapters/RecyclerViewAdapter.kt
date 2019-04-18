package pl.sjmprofil.animaltinder.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.databinding.CardViewFollowersBinding
import pl.sjmprofil.animaltinder.models.Advert



class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TAG = "FollowersRecyclerView"
        const val FOLLOWERS_VIEW = 0
    }

    private var itemsList = mutableListOf<Any>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = when(viewType){
            FOLLOWERS_VIEW -> {
                inflater.inflate(R.layout.card_view_followers, viewGroup, false)
            }
            else -> {
                //for test purpose
                inflater.inflate(R.layout.layout_followers_fragment, viewGroup, false)
            }
        }

        return AdvertViewHolder(view)
    }

    fun updateList(list: MutableList<Advert>) {
        itemsList.clear()
        itemsList.addAll(list)
        notifyDataSetChanged()
        Log.d(TAG, "list updated")
    }

    override fun getItemViewType(position: Int): Int {
        return when(itemsList[position]){
            is Advert -> FOLLOWERS_VIEW
            else -> -1
        }
    }

    override fun getItemCount() = itemsList.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int){
        val itemType = getItemViewType(position)
        when(itemType){
            FOLLOWERS_VIEW -> (viewHolder as AdvertViewHolder).bindItems(itemsList[position] as Advert)
        }

    }

    class AdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding: CardViewFollowersBinding
        fun bindItems(advert: Advert) {
            binding = DataBindingUtil.bind(itemView.rootView)!!
            binding.item = advert
        }
    }
}