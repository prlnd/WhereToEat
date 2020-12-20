package com.example.wheretoeat.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wheretoeat.model.CityList
import com.example.wheretoeat.util.Constants.Companion.CITIES_TABLE

@Entity(tableName = CITIES_TABLE)
class CitiesEntity(var cityList: CityList) {
    @PrimaryKey(autoGenerate = false)
    var id = 0
}