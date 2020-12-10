package com.example.wheretoeat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.databinding.RestaurantsRowLayoutBinding
import com.example.wheretoeat.model.Restaurant
import com.example.wheretoeat.model.RestaurantList
import com.example.wheretoeat.util.RestaurantsDiffUtil

class RestaurantsAdapter : RecyclerView.Adapter<RestaurantsAdapter.MyViewHolder>() {
    private var restaurants = emptyList<Restaurant>()

    class MyViewHolder(private val binding: RestaurantsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant: Restaurant) {
            binding.restaurant = restaurant
            binding.executePendingBindings() // update layout whenever there's a change inside our data
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RestaurantsRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentResult = restaurants[position]
        holder.bind(currentResult)
    }

    override fun getItemCount() = restaurants.size

    fun setData(newData: RestaurantList) {
        val restaurantsDiffUtil = RestaurantsDiffUtil(restaurants, newData.restaurants)
        val diffUtilResult = DiffUtil.calculateDiff(restaurantsDiffUtil)
        restaurants = newData.restaurants.sortedBy { it.name }
        diffUtilResult.dispatchUpdatesTo(this)
    }
}