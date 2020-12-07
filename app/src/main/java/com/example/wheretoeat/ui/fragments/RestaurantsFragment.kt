package com.example.wheretoeat.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wheretoeat.viewmodel.MainViewModel
import com.example.wheretoeat.R
import com.example.wheretoeat.adapter.RestaurantsAdapter
import com.example.wheretoeat.util.Constants
import com.example.wheretoeat.util.NetworkResult
import com.example.wheretoeat.viewmodel.RestaurantsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_restaurants.view.*

@AndroidEntryPoint
class RestaurantsFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var restaurantsViewModel: RestaurantsViewModel
    private val mAdapter by lazy { RestaurantsAdapter() }
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelProvider = ViewModelProvider(requireActivity())
        mainViewModel = viewModelProvider.get(MainViewModel::class.java)
        restaurantsViewModel = viewModelProvider.get(RestaurantsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_restaurants, container, false)

        setupRecyclerView()
        requestApiData()

        return mView
    }

    fun applyQueries(): HashMap<String, String> {
        val queries = HashMap<String, String>()

        queries[Constants.QUERY_CITY] = "Chicago"

        return queries
    }

    private fun requestApiData() {
        mainViewModel.getRestaurants(applyQueries())
        mainViewModel.restaurantsResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        mView.recyclerview.adapter = mAdapter
        mView.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        mView.recyclerview.showShimmer()
    }

    private fun hideShimmerEffect() {
        mView.recyclerview.hideShimmer()
    }
}