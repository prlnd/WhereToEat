package com.example.wheretoeat.bindingadapter

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.wheretoeat.R
import com.example.wheretoeat.model.Restaurant
import com.example.wheretoeat.ui.fragments.restaurants.RestaurantsFragmentDirections
import java.lang.Exception

class RestaurantsRowBinding {
    // to be able to access functions inside from outside
    companion object {

        @BindingAdapter("onRestaurantClickListener")
        @JvmStatic
        fun onRestaurantClickListener(
            restaurantRowLayout: ConstraintLayout,
            restaurant: Restaurant
        ) {
            Log.d("onRestaurantClickListener", "CALLED")
            restaurantRowLayout.setOnClickListener {
                try {
                    val action =
                        RestaurantsFragmentDirections.actionRestaurantsFragmentToDetailsActivity(
                            restaurant
                        )
                } catch (e: Exception) {
                    Log.d("onRestaurantClickListener", e.message.toString())
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            Glide.with(imageView.context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
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
                        text = "$".repeat(price)
//                        setTextColor(color)
                    }
                }
                is ImageView -> view.setColorFilter(color)
            }
        }
    }
}