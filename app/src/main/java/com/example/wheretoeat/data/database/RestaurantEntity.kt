package com.example.wheretoeat.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wheretoeat.model.RestaurantList
import com.example.wheretoeat.util.Constants.Companion.RESTAURANTS_TABLE

@Entity(tableName = RESTAURANTS_TABLE)
class RestaurantEntity(var restaurantList: RestaurantList) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}