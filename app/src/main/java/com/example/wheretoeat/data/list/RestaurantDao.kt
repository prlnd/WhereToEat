package com.example.wheretoeat.data.list

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wheretoeat.model.Restaurant

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRestaurant(restaurant: Restaurant)

    @Query("SELECT * FROM restaurant_table ORDER BY name ASC")
    fun readAllData(): LiveData<List<Restaurant>>

    @Delete
    suspend fun deleteRestaurant(restaurant: Restaurant)

    @Query("DELETE FROM restaurant_table")
    suspend fun deleteAll()
}