package com.example.wheretoeat.data

import com.example.wheretoeat.data.database.RestaurantDao
import com.example.wheretoeat.data.database.entities.CitiesEntity
import com.example.wheretoeat.data.database.entities.FavoritesEntity
import com.example.wheretoeat.data.database.entities.RestaurantsEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val restaurantDao: RestaurantDao
) {

    fun readRestaurants() = restaurantDao.readRestaurants()

    fun readFavoriteRestaurants() = restaurantDao.readFavoriteRestaurants()

    fun readCities() = restaurantDao.readCities()

    suspend fun insertRestaurant(restaurantsEntity: RestaurantsEntity) =
        restaurantDao.insertRestaurant(restaurantsEntity)

    suspend fun insertFavoriteRestaurant(favoritesEntity: FavoritesEntity) =
        restaurantDao.insertFavoriteRestaurant(favoritesEntity)

    suspend fun insertCity(citiesEntity: CitiesEntity) =
        restaurantDao.insertCity(citiesEntity)

    suspend fun deleteFavoriteRestaurant(favoritesEntity: FavoritesEntity) =
        restaurantDao.deleteFavoriteRestaurant(favoritesEntity)

    suspend fun deleteAllFavoriteRestaurants() =
        restaurantDao.deleteAllFavoriteRestaurants()
}