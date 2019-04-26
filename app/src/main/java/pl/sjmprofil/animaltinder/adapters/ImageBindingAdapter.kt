package pl.sjmprofil.animaltinder.adapters

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import pl.sjmprofil.animaltinder.R

object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.setImage(url: String?) {
//        Picasso
//            .get()
//            .load(url)
//            .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
//            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//            .placeholder(R.drawable.loading_circle)
//            .error(R.drawable.ic_error)
//            .into(this)


        Glide.with(context)
            .load(url)
            .apply(
                RequestOptions()
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.loading_circle)
                    .error(R.drawable.ic_error))
            .into(this)
    }
}