package com.example.wheretoeat.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Restaurant(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("state")
    val state: String,
//    @SerializedName("area")
//    val area: String,
    @SerializedName("postal_code")
    val postalCode: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("price")
    val price: Int,
    @SerializedName("reserve_url")
    val reserveUrl: String,
//    @SerializedName("mobile_reserve_url")
//    val mobileReserveUrl: String,
    @SerializedName("image_url")
    val imageUrl: String
): Parcelable