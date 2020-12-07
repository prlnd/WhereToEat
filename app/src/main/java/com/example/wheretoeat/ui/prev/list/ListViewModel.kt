package com.example.wheretoeat.ui.prev.list

//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.wheretoeat.network.RestaurantApiRepository
//import com.example.wheretoeat.model.RestaurantList
//import kotlinx.coroutines.launch
//import retrofit2.Response
//
//class ListViewModel(private val repository: RestaurantApiRepository) : ViewModel() {
//    private val _responseRestaurantsByCity = MutableLiveData<Response<RestaurantList>>()
//
//    val responseRestaurantList: MutableLiveData<Response<RestaurantList>>
//        get() = _responseRestaurantsByCity
//
//    fun getRestaurantsByCity(city: String, page: Int) {
//        viewModelScope.launch {
//            val response = repository.getRestaurantsByCity(city, page)
//            _responseRestaurantsByCity.value = response
//        }
//    }
//}