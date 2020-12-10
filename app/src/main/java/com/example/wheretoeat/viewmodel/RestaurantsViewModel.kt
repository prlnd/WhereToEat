package com.example.wheretoeat.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
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

    val readPriceCategory = dataStoreRepository.readPriceCategory

    fun savePriceCategory(priceCategory: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.savePriceCategory(priceCategory)
        }

    fun applyQueries(): QueryParameters {
        viewModelScope.launch {
            readPriceCategory.collect {
                priceCategory = it
            }
        }

        return QueryParameters(
            city = DEFAULT_CITY,
            price = priceCategory
        )
    }

}