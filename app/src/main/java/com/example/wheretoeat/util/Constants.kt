package com.example.wheretoeat.util

class Constants {
    companion object {
        const val BASE_URL = "https://opentable.herokuapp.com/"
        const val USER_ID = 1234

        // Default values
        const val DEFAULT_CITY = "Chicago"
        const val DEFAULT_PRICE = 2
        const val DEFAULT_PAGE = 1
        const val DEFAULT_PER_PAGE = 100

        // API Queries
        const val QUERY_PRICE = "price"
        const val QUERY_NAME = "name"
        const val QUERY_ADDRESS = "address"
        const val QUERY_STATE = "state"
        const val QUERY_CITY = "city"
        const val QUERY_ZIP = "zip"
        const val QUERY_COUNTRY = "country"
        const val QUERY_PAGE = "page"
        const val QUERY_PER_PAGE = "per_page"

        // ROOM Database
        const val DATABASE_NAME = "restaurants_database"
        const val RESTAURANTS_TABLE = "restaurants_table"
        const val CITIES_TABLE = "cities_table"
        const val FAVORITES_TABLE = "favorites_table"

        // Bottom Sheet Preferences
        const val PREFERENCE_NAME = "restaurantPreferences"
        const val PREFERENCE_PRICE_CATEGORY_ID = "priceCategoryId"
        const val PREFERENCE_BACK_ONLINE = "backOnline"
    }
}