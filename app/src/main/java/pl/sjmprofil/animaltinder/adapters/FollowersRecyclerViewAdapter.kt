package pl.sjmprofil.animaltinder.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.sjmprofil.animaltinder.R
import pl.sjmprofil.animaltinder.models.User

class FollowersRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TAG = "FollowersRecyclerView"
    }

    lateinit var fallowersList: MutableList<User>
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.cardview_followers, viewGroup, false)
        return ViewHolder(view)
    }

    fun updateList(list: MutableList<User>){
        fallowersList.clear()
        fallowersList.addAll(list)
        notifyDataSetChanged()
        Log.d(TAG, "list updated")
    }

    override fun getItemCount() = fallowersList.size

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}