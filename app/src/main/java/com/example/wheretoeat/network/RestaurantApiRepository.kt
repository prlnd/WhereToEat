package com.example.wheretoeat.network

import com.example.wheretoeat.model.RestaurantList
import retrofit2.Response

class RestaurantApiRepository {
    suspend fun getRestaurantsByCity(city: String, page: Int): Response<RestaurantList> {
        return RestaurantApi.retrofitService.getRestaurants(city, page)
    }
}