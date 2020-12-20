package com.example.wheretoeat.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wheretoeat.model.RestaurantList
import com.example.wheretoeat.util.Constants.Companion.RESTAURANTS_TABLE

@Entity(tableName = RESTAURANTS_TABLE)
class RestaurantsEntity(var restaurantList: RestaurantList) {
    @PrimaryKey(autoGenerate = false)
    var id = 0
}