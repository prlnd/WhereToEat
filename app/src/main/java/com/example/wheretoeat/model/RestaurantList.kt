package com.example.wheretoeat.model

import com.google.gson.annotations.SerializedName

data class RestaurantList(
    @SerializedName("total_entries")
    val totalEntries:Int,
    @SerializedName("per_page")
    val perPage:Int,
    @SerializedName("current_page")
    val currentPage:Int,
    @SerializedName("restaurants")
    val restaurants: List<Restaurant>
)