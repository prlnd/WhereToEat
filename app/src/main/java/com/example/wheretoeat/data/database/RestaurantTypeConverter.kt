package com.example.wheretoeat.data.database

import androidx.room.TypeConverter
import com.example.wheretoeat.model.Restaurant
import com.example.wheretoeat.model.RestaurantList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RestaurantTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun restaurantListToString(restaurantList: RestaurantList): String = gson.toJson(restaurantList)

    @TypeConverter
    fun stringToRestaurantList(data: String): RestaurantList {
        val listType = object : TypeToken<RestaurantList>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun restaurantToString(restaurant: Restaurant): String = gson.toJson(restaurant)

    @TypeConverter
    fun stringToRestaurant(data: String): Restaurant {
        val listType = object : TypeToken<Restaurant>() {}.type
        return gson.fromJson(data, listType)
    }
}