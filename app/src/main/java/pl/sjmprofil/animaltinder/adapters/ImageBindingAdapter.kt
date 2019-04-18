package pl.sjmprofil.animaltinder.adapters

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso

object ImageBindingAdapter{
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.setImage(url: String){
        Picasso.get().load(url).into(this)
    }
}