package com.example.wheretoeat.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.wheretoeat.R
import com.example.wheretoeat.adapter.PagerAdapter
import com.example.wheretoeat.data.database.entities.FavoritesEntity
import com.example.wheretoeat.ui.fragments.overview.OverviewFragment
import com.example.wheretoeat.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*
import java.lang.Exception

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()

    private var restaurantSaved = false
    private var savedRestaurantID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = mutableListOf<Fragment>(OverviewFragment())

        val titles = mutableListOf("Overview")

        val restaurantBundle = Bundle()
        restaurantBundle.putParcelable("restaurantBundle", args.restaurant)

        val adapter = PagerAdapter(
            restaurantBundle,
            fragments,
            titles,
            supportFragmentManager
        )

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_favorites_menu)
        checkSavedRestaurants(menuItem!!)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_to_favorites_menu) {
            if (restaurantSaved) removeFromFavorites(item)
            else saveToFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkSavedRestaurants(menuItem: MenuItem) {
        mainViewModel.readFavoriteRestaurants.observe(this, { favoritesEntity ->
            try {
                for (savedRestaurant in favoritesEntity) {
                    if (savedRestaurant.restaurant.id == args.restaurant.id) {
                        changeMenuItemColor(menuItem, R.color.yellow)
                        savedRestaurantID = savedRestaurant.id
                        restaurantSaved = true
                    } else {
                        changeMenuItemColor(menuItem, R.color.white)
                    }
                }
            } catch (e: Exception) {
                Log.d("DetailsActivity", e.message.toString())
            }
        })
    }

    private fun saveToFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(0, args.restaurant)
        mainViewModel.insertFavoriteRestaurant(favoritesEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("Restaurant saved.")
        restaurantSaved = true
    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(
            savedRestaurantID,
            args.restaurant
        )
        mainViewModel.deleteFavoriteRestaurant(favoritesEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackBar("Removed from Favorites.")
        restaurantSaved = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            detailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }
}