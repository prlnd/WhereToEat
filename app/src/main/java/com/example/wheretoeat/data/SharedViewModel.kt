package com.example.wheretoeat.data

import androidx.lifecycle.ViewModel
import com.example.wheretoeat.model.Restaurant

class SharedViewModel : ViewModel() {
    val favoriteMap = hashMapOf<Int, MutableList<Restaurant>>()

    fun addFavorite(userId: Int, rest: Restaurant) {
        val tmp = favoriteMap.get(userId)
        if (tmp != null) {
            tmp.add(rest)
        } else {
            favoriteMap[userId] = mutableListOf(rest)
        }
    }

    fun getFavorites(userId: Int): List<Restaurant> {
        if (favoriteMap.containsKey(userId)) {
            return favoriteMap.getValue(userId)
        }
        return emptyList()
    }
}