package com.example.wheretoeat.model

import com.example.wheretoeat.util.Constants.Companion.DEFAULT_PER_PAGE

data class QueryParameters(
    val city: String? = null,
    val price: Int? = null,
    val page: Int? = null,
    val per_page: Int? = DEFAULT_PER_PAGE
)
