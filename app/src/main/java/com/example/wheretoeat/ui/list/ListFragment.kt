package com.example.wheretoeat.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wheretoeat.R
import com.example.wheretoeat.network.RestaurantApiRepository
import com.example.wheretoeat.model.Restaurant

class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_list, container, false)

        val repository = RestaurantApiRepository()
        val factory = ListViewModelFactory(repository)
        listViewModel = ViewModelProvider(this, factory).get(ListViewModel::class.java)

//        listViewModel.loadRestaurants("London")

        lateinit var list: List<Restaurant>
        listViewModel.responseRestaurantsByCity.observe(requireActivity(), { response ->
            if (response.isSuccessful) {
                list = response.body()!!.restaurants
                Log.d("Restaurants count", response.body()!!.count.toString())
            } else {
                Log.d("Response", response.errorBody().toString())
//                textView.text = response.code().toString()
            }
        })

//            val list = listViewModel.getRestaurantsByCity("London")

        return root
    }
}