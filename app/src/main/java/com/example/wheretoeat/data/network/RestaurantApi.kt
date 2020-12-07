package com.example.wheretoeat.data.network

import com.example.wheretoeat.model.CityList
import com.example.wheretoeat.model.CountryList
import com.example.wheretoeat.model.RestaurantList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RestaurantApi {
    @GET("api/restaurants")
    suspend fun getRestaurants(
        @QueryMap queries: Map<String, String>
    ): Response<RestaurantList>

    @GET("api/cities")
    suspend fun getCities(): Response<CityList>

    @GET("api/countries")
    suspend fun getCountries(): Response<CountryList>
}

//object Api {
//    val api: RestaurantApi by lazy {
//        retrofit.create(RestaurantApi::class.java)
//    }
//}
