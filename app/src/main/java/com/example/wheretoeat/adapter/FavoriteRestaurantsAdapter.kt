package com.example.wheretoeat.adapter

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.R
import com.example.wheretoeat.data.database.entities.FavoritesEntity
import com.example.wheretoeat.databinding.FavoriteRestaurantsRowLayoutBinding
import com.example.wheretoeat.ui.fragments.favorites.FavoriteRestaurantsFragmentDirections
import com.example.wheretoeat.util.RestaurantsDiffUtil
import com.example.wheretoeat.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.favorite_restaurants_row_layout.view.*

class FavoriteRestaurantsAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
) : RecyclerView.Adapter<FavoriteRestaurantsAdapter.MyViewHolder>(), ActionMode.Callback {

    private var multiSelection = false

    private lateinit var mActionMode: ActionMode
    private lateinit var rootView: View

    private val selectedRestaurants = mutableListOf<FavoritesEntity>()
    private val myViewHolders = mutableListOf<MyViewHolder>()
    private var favoriteRestaurants = mutableListOf<FavoritesEntity>()
    private var favoriteRestaurantsCopy = emptyList<FavoritesEntity>()

    class MyViewHolder(private val binding: FavoriteRestaurantsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    FavoriteRestaurantsRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myViewHolders.add(holder)
        rootView = holder.itemView.rootView

        val currentRestaurant = favoriteRestaurants[position]
        holder.bind(currentRestaurant)

        /**
         * Single Click Listener
         */
        holder.itemView.favoriteRestaurantsRowLayout.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentRestaurant)
            } else {
                val action =
                    FavoriteRestaurantsFragmentDirections.actionFavoriteRestaurantsFragmentToDetailsActivity(
                        currentRestaurant.restaurant
                    )
                holder.itemView.findNavController().navigate(action)
            }
        }

        /**
         * Long Click Listener
         */
        holder.itemView.favoriteRestaurantsRowLayout.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentRestaurant)
                true
            } else {
                mActionMode.finish()
                false
            }
        }

    }

    private fun applySelection(holder: MyViewHolder, currentRestaurant: FavoritesEntity) {
        if (selectedRestaurants.contains(currentRestaurant)) {
            selectedRestaurants.remove(currentRestaurant)
            changeRestaurantStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
            applyActionModeTitle()
        } else {
            selectedRestaurants.add(currentRestaurant)
            changeRestaurantStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeRestaurantStyle(
        holder: MyViewHolder,
        backgroundColor: Int,
        strokeColor: Int
    ) {
        holder.itemView.favoriteRestaurantsRowLayout.setBackgroundColor(
            ContextCompat.getColor(requireActivity, backgroundColor)
        )
        holder.itemView.favorite_row_cardView.strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)
    }

    private fun applyActionModeTitle() {
        when (selectedRestaurants.size) {
            0 -> mActionMode.finish()
            1 -> mActionMode.title = "${selectedRestaurants.size} item selected"
            else -> mActionMode.title = "${selectedRestaurants.size} items selected"
        }
    }

    override fun getItemCount() = favoriteRestaurants.size

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
        mActionMode = mode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, menu: MenuItem?): Boolean {
        if (menu?.itemId == R.id.delete_favorite_restaurant_menu) {
            selectedRestaurants.forEach {
                mainViewModel.deleteFavoriteRestaurant(it)
            }
            when (selectedRestaurants.size) {
                1 -> showSnackBar("Restaurant removed.")
                else -> showSnackBar("${selectedRestaurants.size} Restaurants removed.")
            }

            multiSelection = false
            selectedRestaurants.clear()
            mode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        myViewHolders.forEach { holder ->
            changeRestaurantStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }
        multiSelection = false
        selectedRestaurants.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    fun setData(newFavoriteRestaurants: List<FavoritesEntity>) {
        val favoriteRestaurantsDiffUtil =
            RestaurantsDiffUtil(favoriteRestaurants, newFavoriteRestaurants)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRestaurantsDiffUtil)
        favoriteRestaurantsCopy = newFavoriteRestaurants
        favoriteRestaurants = favoriteRestaurantsCopy.toMutableList()
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
            .setAction("Okay") {}
            .show()
    }

    fun clearContextualActionMode() {
        if (this::mActionMode.isInitialized) {
            mActionMode.finish()
        }
    }

    fun filter(text: String) {
        favoriteRestaurants.clear()
        if (text.isEmpty()) {
            favoriteRestaurants.addAll(favoriteRestaurantsCopy)
        } else {
            favoriteRestaurantsCopy.forEach {
                if (it.restaurant.name.contains(text, true)) {
                    favoriteRestaurants.add(it)
                }
            }
        }
        notifyDataSetChanged()
    }
}