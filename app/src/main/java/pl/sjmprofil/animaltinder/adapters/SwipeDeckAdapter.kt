package pl.sjmprofil.animaltinder.adapters

import android.view.ViewGroup
import pl.sjmprofil.animaltinder.models.Advert
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.swipe_card_layout.view.*
import pl.sjmprofil.animaltinder.databinding.SwipeCardLayoutBinding

class SwipeDeckAdapter: RecyclerView.Adapter<SwipeDeckAdapter.ViewHolder>() {

    var openDetailsCallback: ((Advert) -> Unit)? = null
    private val listOfAdverts: MutableList<Advert> = mutableListOf()

    fun swapData(list: List<Advert>) {
        listOfAdverts.clear()
        listOfAdverts.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwipeDeckAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val card = SwipeCardLayoutBinding.inflate(inflater, parent, false)

        return ViewHolder(card)
    }

    override fun getItemCount(): Int {
        return listOfAdverts.size
    }

    fun removeFirst(): Advert? {
        val index = 0
        if (listOfAdverts.size > 0) {
            val advert = listOfAdverts.removeAt(0)
            Log.d("SwipeAdapter", "Advert ${advert}")
            notifyItemRemoved(0)
            return advert
        }
        return null
    }

    override fun onBindViewHolder(holder: SwipeDeckAdapter.ViewHolder, position: Int) {
        holder.bind(listOfAdverts[position], openDetailsCallback)
    }

    class ViewHolder(private val binding: SwipeCardLayoutBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(advert: Advert,
                 openDetailsCallback: ((Advert) -> Unit)?){
            binding.advert = advert
            binding.executePendingBindings()

            binding.root.swipe_card_card_view_layout.setOnClickListener {
                openDetailsCallback?.invoke(advert)
            }
        }
    }
}