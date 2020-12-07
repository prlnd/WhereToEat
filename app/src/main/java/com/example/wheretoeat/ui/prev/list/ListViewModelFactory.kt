package com.example.wheretoeat.ui.prev.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wheretoeat.network.RestaurantApiRepository

class ListViewModelFactory(private val repository: RestaurantApiRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(repository) as T
    }
}