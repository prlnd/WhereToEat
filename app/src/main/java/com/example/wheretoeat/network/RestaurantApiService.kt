package com.example.wheretoeat.network

import com.example.wheretoeat.model.CountryList
import com.example.wheretoeat.model.Restaurant
import com.example.wheretoeat.model.RestaurantList
import com.example.wheretoeat.model.RestaurantsByCity
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface RestaurantApiService {
//    @GET("/api/countries")
//    suspend fun getCountry(): Response<CountryList>

    @GET("api/restaurants")
    suspend fun getRestaurantsByCity(
        @Query("city") city: String
    ): Response<RestaurantsByCity>
}

object RestaurantApi {
    val retrofitService: RestaurantApiService by lazy {
        retrofit.create(RestaurantApiService::class.java)
    }
}
