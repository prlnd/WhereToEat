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
    suspend fun getRestaurants(queries: QueryParameters): Response<RestaurantList> {
        val data = restaurantApi.getRestaurants(
            queries.city,
            queries.price,
            queries.page,
            queries.per_page
        )
        if (data.isSuccessful) {
            data.body()?.let { body ->
                var page = 1
                while (body.restaurants.size < body.totalEntries) {
                    val tmp = restaurantApi.getRestaurants(
                        queries.city,
                        queries.price,
                        ++page,
                        queries.per_page
                    )
                    if (tmp.isSuccessful && tmp.body() != null) {
                        body.restaurants.addAll(tmp.body()!!.restaurants)
                    } else {
                        break
                    }
                }
            }
        }
        return data
    }

    suspend fun getCities(): Response<CityList> = restaurantApi.getCities()
}