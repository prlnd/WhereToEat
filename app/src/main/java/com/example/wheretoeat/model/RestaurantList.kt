package com.example.wheretoeat.model

import com.google.gson.annotations.SerializedName

data class RestaurantList(
    @SerializedName("total_entries")
    val totalEntries:Int,
    @SerializedName("page")
    val page:Int,
    @SerializedName("per_page")
    val perPage:Int,
    @SerializedName("restaurants")
    val restaurants: MutableList<Restaurant>
)