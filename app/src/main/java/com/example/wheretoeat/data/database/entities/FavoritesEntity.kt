package com.example.wheretoeat.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wheretoeat.model.Restaurant
import com.example.wheretoeat.util.Constants.Companion.FAVORITES_TABLE

@Entity(tableName = FAVORITES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var restaurant: Restaurant
)