package com.example.wheretoeat.data

import com.example.wheretoeat.data.network.RestaurantApi
import com.example.wheretoeat.model.RestaurantList
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val restaurantApi: RestaurantApi
) {
    suspend fun getRestaurants(queries: Map<String, String>) = restaurantApi.getRestaurants(queries)
}