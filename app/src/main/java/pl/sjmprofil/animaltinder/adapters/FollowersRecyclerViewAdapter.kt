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

class FollowersRecyclerViewAdapter : RecyclerView.Adapter<FollowersRecyclerViewAdapter.ViewHolder>() {

    companion object {
        const val TAG = "FollowersRecyclerView"
    }

    private var followersList = mutableListOf<Advert>()
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): FollowersRecyclerViewAdapter.ViewHolder {

        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.card_view_followers, viewGroup, false)
        return ViewHolder(view)
    }

    fun updateList(list: MutableList<Advert>) {
        followersList.clear()
        followersList.addAll(list)
        notifyDataSetChanged()
        Log.d(TAG, "list updated")
    }

    override fun getItemCount() = followersList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int){
        viewHolder.bindItems(followersList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: CardViewFollowersBinding
        fun bindItems(advert: Advert) {
            binding = DataBindingUtil.bind(itemView.rootView)!!
            binding.advert = advert
        }
    }
}