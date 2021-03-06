package pl.sjmprofil.animaltinder.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.databinding.CardViewAdvertsBinding
import pl.sjmprofil.animaltinder.databinding.CardViewUsersBinding
import pl.sjmprofil.animaltinder.models.Advert
import pl.sjmprofil.animaltinder.models.User
import java.lang.Error

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TAG = "FollowersRecyclerView"
        const val ADVERTS_VIEW = 0
        const val USERS_VIEW = 1
    }

    private var itemsList = mutableListOf<Any>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return when (viewType) {
            ADVERTS_VIEW -> {
                val view = inflater.inflate(R.layout.card_view_adverts, viewGroup, false)
                AdvertViewHolder(view)
            }
            USERS_VIEW -> {
                val view = inflater.inflate(R.layout.card_view_users, viewGroup, false)
                UserViewHolder(view)
            }
            else -> {
                throw Error("$TAG: wrong viewType")
            }
        }
    }

    fun removeAt(position: Int) {
        itemsList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItemAt(position: Int): Any {
        return itemsList[position]
    }

    fun updateList(list: MutableList<Any>) {
        itemsList.clear()
        itemsList.addAll(list)
        notifyDataSetChanged()
        Log.d(TAG, "list updated")
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemsList[position]) {
            is Advert -> ADVERTS_VIEW
            is User -> USERS_VIEW
            else -> throw Error("$TAG: wrong value in itemsList")
        }
    }

    override fun getItemCount() = itemsList.size

    var itemClickListener: ((Any) -> Unit)? = null
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val itemType = viewHolder.itemViewType
        when (itemType) {
            ADVERTS_VIEW -> (viewHolder as AdvertViewHolder).bindItems(itemsList[position] as Advert, itemClickListener)
            USERS_VIEW -> (viewHolder as UserViewHolder).bindItems(itemsList[position] as User, itemClickListener)
        }
    }

    class AdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding: CardViewAdvertsBinding
        fun bindItems(advert: Advert, itemClickListener: ((Advert) -> Unit)?) {
            binding = DataBindingUtil.bind(itemView.rootView)!!
            binding.item = advert
            itemView.setOnClickListener {
                itemClickListener?.invoke(advert)
            }
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding: CardViewUsersBinding
        fun bindItems(user: User, itemClickListener: ((User) -> Unit)?) {
            binding = DataBindingUtil.bind(itemView.rootView)!!
            binding.item = user
            itemView.setOnClickListener {
                itemClickListener?.invoke(user)
            }
        }
    }
}