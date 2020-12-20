package com.example.wheretoeat.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.wheretoeat.data.database.entities.CitiesEntity
import com.example.wheretoeat.data.database.entities.FavoritesEntity
import com.example.wheretoeat.data.database.entities.RestaurantsEntity

@Database(
    entities = [RestaurantsEntity::class, FavoritesEntity::class, CitiesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RestaurantTypeConverter::class)
abstract class RestaurantDatabase: RoomDatabase() {

    abstract fun restaurantDao(): RestaurantDao

}