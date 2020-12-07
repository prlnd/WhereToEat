package com.example.wheretoeat.ui.prev.list

//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.activityViewModels
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.wheretoeat.R
//import com.example.wheretoeat.adapter.RestaurantAdapter
//import com.example.wheretoeat.data.SharedViewModel
//import com.example.wheretoeat.network.RestaurantApiRepository
//import com.example.wheretoeat.model.Restaurant
//
//class ListFragment : Fragment() {
//    private lateinit var listViewModel: ListViewModel
//    private lateinit var restaurantList: RecyclerView
//    private lateinit var adapter: RestaurantAdapter
//    private val sharedViewModel: SharedViewModel by activityViewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val root = inflater.inflate(R.layout.fragment_list, container, false)
//
//        adapter = RestaurantAdapter(sharedViewModel)
//        restaurantList = root.findViewById(R.id.recyclerView)
//        restaurantList.adapter = adapter
//        restaurantList.layoutManager = LinearLayoutManager(activity)
//        restaurantList.setHasFixedSize(true)
//
//        val repository = RestaurantApiRepository()
//        val factory = ListViewModelFactory(repository)
//        listViewModel =
//            ViewModelProvider(requireActivity(), factory).get(ListViewModel::class.java)
//        listViewModel.getRestaurantsByCity("Atlanta", 1)
//
//        lateinit var list: List<Restaurant>
//        listViewModel.responseRestaurantsByCity.observe(viewLifecycleOwner, { response ->
//            if (response.isSuccessful) {
//                list = response.body()!!.restaurants
//                adapter.setData(list)
//                Log.d("Restaurants", response.body()!!.restaurants.toString())
//            } else {
//                Log.d("Response", response.errorBody().toString())
////                textView.text = response.code().toString()
//            }
//        })
//
//        return root
//
//    }
//}