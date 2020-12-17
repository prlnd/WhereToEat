package com.example.wheretoeat.ui.fragments.restaurants

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wheretoeat.R
import com.example.wheretoeat.viewmodel.MainViewModel
import com.example.wheretoeat.adapter.RestaurantsAdapter
import com.example.wheretoeat.databinding.FragmentRestaurantsBinding
import com.example.wheretoeat.util.NetworkListener
import com.example.wheretoeat.util.NetworkResult
import com.example.wheretoeat.util.observeOnce
import com.example.wheretoeat.viewmodel.RestaurantsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RestaurantsFragment : Fragment(), SearchView.OnQueryTextListener {
    private val args by navArgs<RestaurantsFragmentArgs>()

    private var _binding: FragmentRestaurantsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var restaurantsViewModel: RestaurantsViewModel
    private val mAdapter by lazy { RestaurantsAdapter() }

    private lateinit var networkListener: NetworkListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelProvider = ViewModelProvider(requireActivity())
        mainViewModel = viewModelProvider.get(MainViewModel::class.java)
        restaurantsViewModel = viewModelProvider.get(RestaurantsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRestaurantsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setHasOptionsMenu(true)

        setupRecyclerView()

        restaurantsViewModel.readBackOnline.observe(viewLifecycleOwner, {
            restaurantsViewModel.backOnline = it
        })

        lifecycleScope.launchWhenStarted {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status ->
                    Log.d("NetworkListener", status.toString())
                    restaurantsViewModel.networkStatus = status
                    restaurantsViewModel.showNetworkStatus()
                    readDatabase()
                }
        }

        binding.restaurantsFab.setOnClickListener {
            if (restaurantsViewModel.networkStatus) {
                findNavController().navigate(R.id.action_restaurantsFragment_to_restaurantsBottomSheet)
            } else {
                restaurantsViewModel.showNetworkStatus()
            }
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.restaurants_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            mAdapter.filter(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            mAdapter.filter(newText)
        }
        return true
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRestaurants.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    Log.d("RestaurantFragment", "requestApiData called!")
                    mAdapter.setData(database[0].restaurantList)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            })
        }
    }

    private fun requestApiData() {
        Log.d("RestaurantFragment", "requestApiData called!")
        mainViewModel.getRestaurants(restaurantsViewModel.applyQueries())
        mainViewModel.restaurantsResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
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

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRestaurants.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].restaurantList)
                }
            })
        }
    }

    private fun showShimmerEffect() {
        binding.recyclerview.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.recyclerview.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}