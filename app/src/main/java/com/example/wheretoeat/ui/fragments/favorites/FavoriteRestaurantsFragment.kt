package com.example.wheretoeat.ui.fragments.favorites

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.R
import com.example.wheretoeat.adapter.FavoriteRestaurantsAdapter
import com.example.wheretoeat.databinding.FragmentFavoriteRestaurantsBinding
import com.example.wheretoeat.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRestaurantsFragment : Fragment(), SearchView.OnQueryTextListener {

    private val mainViewModel: MainViewModel by viewModels()
    private val mAdapter: FavoriteRestaurantsAdapter by lazy {
        FavoriteRestaurantsAdapter(requireActivity(), mainViewModel)
    }

    private var _binding: FragmentFavoriteRestaurantsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteRestaurantsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.mAdapter = mAdapter

        setHasOptionsMenu(true)

        setupRecyclerView(binding.favoritesRecyclerView)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_restaurants_menu, menu)

        val search = menu.findItem(R.id.favorite_menu_search)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAll_favorite_restaurant_menu) {
            mainViewModel.deleteAllFavoriteRestaurants()
            showSnackBar()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun showSnackBar() {
        Snackbar.make(binding.root, "All restaurants removed.", Snackbar.LENGTH_SHORT)
            .setAction("Okay") {}
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mAdapter.clearContextualActionMode()
    }
}