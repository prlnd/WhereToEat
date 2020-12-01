package com.example.wheretoeat.network

import com.example.wheretoeat.model.RestaurantsByCity
import retrofit2.Response

class RestaurantApiRepository {
    suspend fun getRestaurantsByCity(city: String): Response<RestaurantsByCity> {
        return RestaurantApi.retrofitService.getRestaurantsByCity(city)
    }
}