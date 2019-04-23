package pl.sjmprofil.animaltinder.adapters

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import pl.sjmprofil.animaltinder.R

object ImageBindingAdapter{
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.setImage(url: String?){
//            Picasso.get().load(url).placeholder().error(R.drawable.ic_error).into(this)

        Glide.with(context)
            .load(url)
            .apply(RequestOptions().placeholder(R.drawable.loading_circle))
            .into(this)
    }
}