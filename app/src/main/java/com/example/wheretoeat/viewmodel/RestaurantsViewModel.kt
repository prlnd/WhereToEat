package com.example.wheretoeat.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.wheretoeat.data.DataStoreRepository
import com.example.wheretoeat.model.QueryParameters
import com.example.wheretoeat.util.Constants.Companion.DEFAULT_PRICE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RestaurantsViewModel @ViewModelInject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    val cities = MutableStateFlow(listOf<String>())

    private var priceCategory = DEFAULT_PRICE
    private var cityName: String? = null

    var networkStatus = false
    var backOnline = false

    val readPriceCategory = dataStoreRepository.readPriceCategory
    val readCityName = dataStoreRepository.readCityName
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    fun savePriceCategory(priceCategory: Int, priceCategoryId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.savePriceCategory(priceCategory, priceCategoryId)
        }

    fun saveCityName(cityName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveCityName(cityName)
        }

    fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }

    fun applyQueries(): QueryParameters {
        viewModelScope.launch {
            readPriceCategory.collect { value ->
                priceCategory = value.selectedPriceCategory
            }
        }

        viewModelScope.launch {
            readCityName.collect { value ->
                if (value.isNotEmpty()) {
                    cityName = value
                }
            }
        }

            if (cityName.isNullOrEmpty() && cities.value.isNotEmpty()) {
                cityName = cities.value[0]
            }


        return QueryParameters(
            city = cityName,
            price = priceCategory
        )
    }

    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection.", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if (backOnline) {
            Toast.makeText(getApplication(), "We are back online.", Toast.LENGTH_SHORT).show()
            saveBackOnline(false)
        }
    }
}