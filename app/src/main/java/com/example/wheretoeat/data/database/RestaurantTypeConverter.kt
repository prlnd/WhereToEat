package com.example.wheretoeat.data.database

import androidx.room.TypeConverter
import com.example.wheretoeat.model.RestaurantList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RestaurantTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun restaurantListToString(restaurantList: RestaurantList): String {
        return gson.toJson(restaurantList)
    }

    @TypeConverter
    fun stringToRestaurant(data: String): RestaurantList {
        val listType = object : TypeToken<RestaurantList>() {}.type
        return gson.fromJson(data, listType)
    }
}