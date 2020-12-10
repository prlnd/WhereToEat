package com.example.wheretoeat.data

import com.example.wheretoeat.data.database.RestaurantDao
import com.example.wheretoeat.data.database.entities.FavoritesEntity
import com.example.wheretoeat.data.database.entities.RestaurantEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val restaurantDao: RestaurantDao
) {

    fun readRestaurants() = restaurantDao.readRestaurants()

    fun readFavoriteRestaurants() = restaurantDao.readFavoriteRestaurants()

    suspend fun insertRestaurants(restaurantEntity: RestaurantEntity) =
        restaurantDao.insertRestaurant(restaurantEntity)

    suspend fun insertFavoriteRestaurant(favoritesEntity: FavoritesEntity) =
        restaurantDao.insertFavoriteRestaurant(favoritesEntity)

    suspend fun deleteFavoriteRestaurant(favoritesEntity: FavoritesEntity) =
        restaurantDao.deleteFavoriteRestaurant(favoritesEntity)

    suspend fun deleteAllFavoriteRestaurants() =
        restaurantDao.deleteAllFavoriteRestaurants()
}