package pl.sjmprofil.animaltinder.adapters

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso
import pl.sjmprofil.animaltinder.R

object ImageBindingAdapter{
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.setImage(url: String?){
            Picasso.get().load(url).placeholder(R.drawable.ic_error).error(R.drawable.ic_error).into(this)
    }
}