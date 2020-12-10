package com.example.wheretoeat.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.wheretoeat.data.DataStoreRepository
import com.example.wheretoeat.model.QueryParameters
import com.example.wheretoeat.util.Constants.Companion.DEFAULT_CITY
import com.example.wheretoeat.util.Constants.Companion.DEFAULT_PRICE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RestaurantsViewModel @ViewModelInject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var priceCategory = DEFAULT_PRICE

    var networkStatus = false
    var backOnline = false

    val readPriceCategory = dataStoreRepository.readPriceCategory
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    fun savePriceCategory(priceCategoryId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.savePriceCategory(priceCategoryId)
        }

    fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }

    fun applyQueries(): QueryParameters {
        viewModelScope.launch {
            readPriceCategory.collect {
                priceCategory = it + 1
            }
        }

        return QueryParameters(
            city = DEFAULT_CITY,
            price = priceCategory
        )
    }

    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection.", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if (networkStatus && backOnline) {
            Toast.makeText(getApplication(), "We are back online.", Toast.LENGTH_SHORT).show()
            saveBackOnline(false)

        }
    }

}