package com.example.wheretoeat.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.wheretoeat.model.Restaurant
import com.example.wheretoeat.util.Constants.Companion.DATABASE_NAME

@Database(
    entities = [RestaurantEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RestaurantTypeConverter::class)
abstract class RestaurantDatabase: RoomDatabase() {

    abstract fun restaurantDao(): RestaurantDao

}