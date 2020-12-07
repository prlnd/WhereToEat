package com.example.wheretoeat.network

import com.example.wheretoeat.Constants.Companion.BASE_URL
import com.example.wheretoeat.model.CityList
import com.example.wheretoeat.model.CountryList
import com.example.wheretoeat.model.RestaurantList
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface RestaurantApiService {
    @GET("api/restaurants")
    suspend fun getRestaurants(
        @QueryMap queries: Map<String, String>
    ): Response<RestaurantList>

    @GET("api/cities")
    suspend fun getCities(): Response<CityList>

    @GET("api/countries")
    suspend fun getCountries(): Response<CountryList>
}

object RestaurantApi {
    val retrofitService: RestaurantApiService by lazy {
        retrofit.create(RestaurantApiService::class.java)
    }
}
