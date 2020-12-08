package com.example.wheretoeat.data

import com.example.wheretoeat.data.database.RestaurantDao
import com.example.wheretoeat.data.database.RestaurantEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val restaurantDao: RestaurantDao
) {

    fun readDatabase(): Flow<List<RestaurantEntity>> {
        return restaurantDao.readRestaurant()
    }

    suspend fun insertRestaurants(restaurantEntity: RestaurantEntity) {
        restaurantDao.insertRestaurant(restaurantEntity)
    }
}