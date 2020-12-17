package com.example.wheretoeat.data.database

import androidx.room.*
import com.example.wheretoeat.data.database.entities.FavoritesEntity
import com.example.wheretoeat.data.database.entities.RestaurantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurantEntity: RestaurantEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRestaurant(favoritesEntity: FavoritesEntity)

    // Flow is similar to LiveData
    @Query("SELECT * FROM restaurants_table")
    fun readRestaurants(): Flow<List<RestaurantEntity>>

    @Query("SELECT * FROM favorites_table")
    fun readFavoriteRestaurants(): Flow<List<FavoritesEntity>>

    @Delete
    suspend fun deleteFavoriteRestaurant(favoritesEntity: FavoritesEntity)

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAllFavoriteRestaurants()
}