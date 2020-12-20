package com.example.wheretoeat.model

import com.google.gson.annotations.SerializedName

data class CityList(
    @SerializedName("cities")
    val cities: List<String>
)