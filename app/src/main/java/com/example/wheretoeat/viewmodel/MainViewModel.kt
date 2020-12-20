package com.example.wheretoeat.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.wheretoeat.data.Repository
import com.example.wheretoeat.data.database.entities.CitiesEntity
import com.example.wheretoeat.data.database.entities.FavoritesEntity
import com.example.wheretoeat.model.QueryParameters
import com.example.wheretoeat.data.database.entities.RestaurantsEntity
import com.example.wheretoeat.model.CityList
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

    val readRestaurants = repository.local.readRestaurants().asLiveData()
    val readFavoriteRestaurants = repository.local.readFavoriteRestaurants().asLiveData()
    val readCities = repository.local.readCities().asLiveData()

    private fun insertRestaurants(restaurantsEntity: RestaurantsEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRestaurant(restaurantsEntity)
        }

    fun insertFavoriteRestaurant(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavoriteRestaurant(favoritesEntity)
        }

    private fun insertCities(citiesEntity: CitiesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertCity(citiesEntity)
        }

    fun deleteFavoriteRestaurant(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavoriteRestaurant(favoritesEntity)
        }

    fun deleteAllFavoriteRestaurants() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllFavoriteRestaurants()
        }

    /** RETROFIT */

    // injecting our repository here
    var restaurantsResponse = MutableLiveData<NetworkResult<RestaurantList>>()
    var citiesResponse = MutableLiveData<NetworkResult<CityList>>()

    fun getRestaurants(queries: QueryParameters) = viewModelScope.launch {
        getRestaurantsSafeCall(queries)
    }

    fun getCities() = viewModelScope.launch {
        getCitiesSafeCall()
    }

    private suspend fun getRestaurantsSafeCall(queries: QueryParameters) {
        restaurantsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRestaurants(queries)
                restaurantsResponse.value = handleRestaurantListResponse(response)

                val restaurantList = restaurantsResponse.value!!.data
                restaurantList?.let { offlineCacheRestaurants(it) }
            } catch (e: Exception) {
                restaurantsResponse.value = NetworkResult.Error("Restaurants not found.")
            }
        } else {
            restaurantsResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private suspend fun getCitiesSafeCall() {
        citiesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getCities()
                citiesResponse.value = handleCityListResponse(response)

                val cityList = citiesResponse.value!!.data
                cityList?.let { offlineCacheCities(it) }
            } catch (e: Exception) {
                citiesResponse.value = NetworkResult.Error("Cities not found.")
            }
        } else {
            citiesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun offlineCacheCities(cityList: CityList) {
        val cityEntity = CitiesEntity(cityList)
        insertCities(cityEntity)
    }

    private fun offlineCacheRestaurants(restaurantList: RestaurantList) {
        val restaurantEntity = RestaurantsEntity(restaurantList)
        insertRestaurants(restaurantEntity)
    }

    /**
     *
     * @param response Response<RestaurantList>
     * @return NetworkResult<RestaurantList>
     */
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

    private fun handleCityListResponse(response: Response<CityList>): NetworkResult<CityList> {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            response.body()!!.cities.isNullOrEmpty() -> {
                NetworkResult.Error("Cities not found.")
            }
            response.isSuccessful -> {
                val cityList = response.body()
                NetworkResult.Success(cityList!!)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }

    /**
     *
     * @return Boolean
     */
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