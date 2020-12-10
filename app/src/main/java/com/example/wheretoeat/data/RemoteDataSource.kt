package com.example.wheretoeat.data

import com.example.wheretoeat.model.QueryParameters
import com.example.wheretoeat.data.network.RestaurantApi
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val restaurantApi: RestaurantApi
) {
    suspend fun getRestaurants(queries: QueryParameters) =
        restaurantApi.getRestaurants(
            city = queries.city,
            price = queries.price,
            page = queries.page,
            per_page = queries.per_page
        )

    suspend fun getCities() = restaurantApi.getCities()
}