package com.example.wheretoeat.data

import com.example.wheretoeat.model.QueryParameters
import com.example.wheretoeat.data.network.RestaurantApi
import com.example.wheretoeat.model.CityList
import com.example.wheretoeat.model.RestaurantList
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val restaurantApi: RestaurantApi
) {
    suspend fun getRestaurants(queries: QueryParameters): Response<RestaurantList> =
        restaurantApi.getRestaurants(
            queries.city,
            queries.price,
            queries.page,
            queries.per_page
        )

    suspend fun getCities(): Response<CityList> = restaurantApi.getCities()
}