package com.example.wheretoeat.data.database

import androidx.room.*
import com.example.wheretoeat.data.database.entities.CitiesEntity
import com.example.wheretoeat.data.database.entities.FavoritesEntity
import com.example.wheretoeat.data.database.entities.RestaurantsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurantsEntity: RestaurantsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRestaurant(favoritesEntity: FavoritesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(citiesEntity: CitiesEntity)


    @Query("SELECT * FROM restaurants_table")
    fun readRestaurants(): Flow<List<RestaurantsEntity>> // Flow is similar to LiveData

    @Query("SELECT * FROM favorites_table")
    fun readFavoriteRestaurants(): Flow<List<FavoritesEntity>>

    @Query("SELECT * FROM cities_table")
    fun readCities(): Flow<List<CitiesEntity>>


    @Delete
    suspend fun deleteFavoriteRestaurant(favoritesEntity: FavoritesEntity)

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAllFavoriteRestaurants()
}