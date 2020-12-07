package com.example.wheretoeat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.wheretoeat.util.Constants.Companion.QUERY_CITY
import com.example.wheretoeat.util.Constants.Companion.QUERY_PAGE
import com.example.wheretoeat.util.Constants.Companion.QUERY_PER_PAGE

class RestaurantsViewModel(application: Application) : AndroidViewModel(application) {

    fun applyQueries(): HashMap<String, String> {
        val queries = HashMap<String, String>()

        queries[QUERY_CITY] = "Chicago"
        queries[QUERY_PER_PAGE] = "100"
        queries[QUERY_PAGE] = "1"

        return queries
    }

}