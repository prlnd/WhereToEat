package com.example.wheretoeat.data.network

import com.example.wheretoeat.model.CityList
import com.example.wheretoeat.model.CountryList
import com.example.wheretoeat.model.RestaurantList
import com.example.wheretoeat.util.Constants.Companion.QUERY_CITY
import com.example.wheretoeat.util.Constants.Companion.QUERY_PAGE
import com.example.wheretoeat.util.Constants.Companion.QUERY_PER_PAGE
import com.example.wheretoeat.util.Constants.Companion.QUERY_PRICE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantApi {

    @GET("/restaurants")
    suspend fun getRestaurants(
        @Query(QUERY_CITY) city: String?,
        @Query(QUERY_PRICE) price: Int? = null,
        @Query(QUERY_PAGE) page: Int? = null,
        @Query(QUERY_PER_PAGE) per_page: Int? = null
        ): Response<RestaurantList>

    @GET("/cities")
    suspend fun getCities(): Response<CityList>

    @GET("/countries")
    suspend fun getCountries(): Response<CountryList>
}
