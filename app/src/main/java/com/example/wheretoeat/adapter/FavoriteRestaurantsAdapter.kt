package com.example.wheretoeat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.data.database.entities.FavoritesEntity
import com.example.wheretoeat.databinding.FavoriteRestaurantsRowLayoutBinding
import com.example.wheretoeat.util.RestaurantsDiffUtil

class FavoriteRestaurantsAdapter : RecyclerView.Adapter<FavoriteRestaurantsAdapter.MyViewHolder>() {
    private var favoriteRestaurants = emptyList<FavoritesEntity>()

    class MyViewHolder(private val binding: FavoriteRestaurantsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    FavoriteRestaurantsRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val selectedRestaurant = favoriteRestaurants[position]
        holder.bind(selectedRestaurant)
    }

    override fun getItemCount() = favoriteRestaurants.size

    fun setData(newFavoriteRestaurants: List<FavoritesEntity>) {
        val favoriteRestaurantsDiffUtil = RestaurantsDiffUtil(favoriteRestaurants, newFavoriteRestaurants)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRestaurantsDiffUtil)
        favoriteRestaurants = newFavoriteRestaurants
        diffUtilResult.dispatchUpdatesTo(this)
    }
}