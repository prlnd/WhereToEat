package com.example.wheretoeat.bindingadapter

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.wheretoeat.R

class RestaurantsRowBinding {
    // to be able to access functions inside from outside
    companion object {

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            Glide.with(imageView.context)
                .load(imageUrl)
                .error(R.drawable.ic_error_placeholder)
                .transition(
                    DrawableTransitionOptions()
                        .crossFade(400)
                )
                .into(imageView)
        }

        @BindingAdapter("setPrice")
        @JvmStatic
        fun setPrice(view: View, price: Int) {
            val color = ContextCompat.getColor(
                view.context,
                when (price) {
                    1 -> R.color.green
                    2 -> R.color.yellow
                    3 -> R.color.orange
                    else -> R.color.red
                }
            )
            when (view) {
                is TextView -> {
                    view.apply {
                        text = price.toString()
                        setTextColor(color)
                    }
                }
                is ImageView -> view.setColorFilter(color)
            }
        }
    }
}