package com.example.wheretoeat.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.wheretoeat.data.Repository
import com.example.wheretoeat.data.database.RestaurantEntity
import com.example.wheretoeat.model.RestaurantList
import com.example.wheretoeat.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

// AndroidViewModel provides application reference
class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /** ROOM DATABASE */

    val readRestaurants: LiveData<List<RestaurantEntity>> =
        repository.local.readDatabase().asLiveData()

    private fun insertRestaurants(restaurantEntity: RestaurantEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRestaurants(restaurantEntity)
        }

    /** RETROFIT */
    // injecting our repository here
    var restaurantsResponse = MutableLiveData<NetworkResult<RestaurantList>>()

    fun getRestaurants(queries: Map<String, String>) = viewModelScope.launch {
        getRestaurantsSafeCall(queries)
    }

    private suspend fun getRestaurantsSafeCall(queries: Map<String, String>) {
        restaurantsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRestaurants(queries)
                restaurantsResponse.value = handleRestaurantListResponse(response)

                val restaurantList = restaurantsResponse.value!!.data
                if (restaurantList != null) {
                    offlineCacheRestaurants(restaurantList)
                }
            } catch (e: Exception) {
                restaurantsResponse.value = NetworkResult.Error("Restaurants not found.")
            }
        } else {
            restaurantsResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun offlineCacheRestaurants(restaurantList: RestaurantList) {
        val restaurantEntity = RestaurantEntity(restaurantList)
        insertRestaurants(restaurantEntity)
    }

    private fun handleRestaurantListResponse(response: Response<RestaurantList>): NetworkResult<RestaurantList> {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            response.body()!!.restaurants.isNullOrEmpty() -> {
                NetworkResult.Error("Restaurants not found.")
            }
            response.isSuccessful -> {
                val restaurantList = response.body()
                NetworkResult.Success(restaurantList!!)
            }
            else -> {
                NetworkResult.Error(response.message())
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