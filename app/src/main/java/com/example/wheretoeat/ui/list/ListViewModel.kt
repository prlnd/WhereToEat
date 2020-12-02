package com.example.wheretoeat.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheretoeat.network.RestaurantApiRepository
import com.example.wheretoeat.model.Restaurant
import com.example.wheretoeat.model.RestaurantsByCity
import kotlinx.coroutines.launch
import retrofit2.Response

class ListViewModel(private val repository: RestaurantApiRepository) : ViewModel() {
    private val _responseRestaurantsByCity = MutableLiveData<Response<RestaurantsByCity>>()

    val responseRestaurantsByCity: MutableLiveData<Response<RestaurantsByCity>>
        get() = _responseRestaurantsByCity

    fun getRestaurantsByCity(city: String) {
        viewModelScope.launch {
            val response = repository.getRestaurantsByCity(city)
            _responseRestaurantsByCity.value = response
        }
    }
}