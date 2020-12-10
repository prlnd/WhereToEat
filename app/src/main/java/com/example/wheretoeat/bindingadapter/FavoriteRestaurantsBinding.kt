package com.example.wheretoeat.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.adapter.FavoriteRestaurantsAdapter
import com.example.wheretoeat.data.database.entities.FavoritesEntity

class FavoriteRestaurantsBinding {
    companion object {
        fun setDataAndViewVisibility(
            view: View,
            favoritesEntity: List<FavoritesEntity>?,
            mAdapter: FavoriteRestaurantsAdapter?
        ) {
            if (favoritesEntity.isNullOrEmpty()) {
                view.visibility = when (view) {
                    is ImageView -> View.VISIBLE
                    is TextView -> View.VISIBLE
                    else -> View.INVISIBLE
                }
            }
        }
    }
}