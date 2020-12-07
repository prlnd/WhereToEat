package com.example.wheretoeat.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wheretoeat.data.Repository
import com.example.wheretoeat.model.RestaurantList
import com.example.wheretoeat.util.NetworkResult
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

// AndroidViewModel provides application reference
class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    // injecting our repository here
    var restaurantsResponse: MutableLiveData<NetworkResult<RestaurantList>> = MutableLiveData()

    fun getRestaurants(queries: Map<String, String>) = viewModelScope.launch {
        getRestaurantsSafeCall(queries)
    }

    private suspend fun getRestaurantsSafeCall(queries: Map<String, String>) {
        restaurantsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRestaurants(queries)
                restaurantsResponse.value = handleRestaurantListResponse(response)
            } catch (e: Exception) {
                restaurantsResponse.value = NetworkResult.Error("Restaurants not found.")
            }
        } else {
            restaurantsResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun handleRestaurantListResponse(response: Response<RestaurantList>): NetworkResult<RestaurantList>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.body()!!.restaurants.isNullOrEmpty() -> {
                return NetworkResult.Error("Restaurants not found.")
            }
            response.isSuccessful -> {
                val restaurantList = response.body()
                Log.d("RestaurantList", restaurantList.toString())
                return NetworkResult.Success(restaurantList!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}