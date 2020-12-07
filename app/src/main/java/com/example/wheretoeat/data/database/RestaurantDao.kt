package com.example.wheretoeat.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurantEntity: RestaurantEntity)

    // Flow is similar to LiveData
    @Query("SELECT * FROM restaurants_table ORDER BY id ASC")
    fun readRestaurant(): Flow<List<RestaurantEntity>>
}