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
    }

    private var itemsList = mutableListOf<Any>()
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.card_view_followers, viewGroup, false)
        return AdvertViewHolder(view)
    }

    fun updateList(list: MutableList<Advert>) {
        itemsList.clear()
        itemsList.addAll(list)
        notifyDataSetChanged()
        Log.d(TAG, "list updated")
    }

    override fun getItemCount() = itemsList.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int){
        val item = itemsList[position]
        when(item){
            is Advert -> (viewHolder as AdvertViewHolder).bindItems(itemsList[position] as Advert)
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