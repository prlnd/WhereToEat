package com.example.wheretoeat.ui.fragments.restaurants

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.wheretoeat.R
import com.example.wheretoeat.util.Constants.Companion.DEFAULT_PRICE
import com.example.wheretoeat.viewmodel.RestaurantsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.restaurants_bottom_sheet.view.*

class RestaurantsBottomSheet : BottomSheetDialogFragment() {

    private lateinit var restaurantsViewModel: RestaurantsViewModel

    private var priceCategoryChip = DEFAULT_PRICE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restaurantsViewModel =
            ViewModelProvider(requireActivity()).get(RestaurantsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mView = inflater.inflate(R.layout.restaurants_bottom_sheet, container, false)

        restaurantsViewModel.readPriceCategory.asLiveData().observe(viewLifecycleOwner, { value ->
            priceCategoryChip = value
            updateChip(value, mView.price_chipGroup)
        })

        mView.price_chipGroup.setOnCheckedChangeListener { _, selectedChipId ->
            val selectedPriceCategory = selectedChipId
            priceCategoryChip = selectedPriceCategory
        }

        mView.apply_button.setOnClickListener {
            restaurantsViewModel.savePriceCategory(
                priceCategoryChip
            )
            val action =
                RestaurantsBottomSheetDirections.actionRestaurantsBottomSheetToRestaurantsFragment(
                    true
                )
            findNavController().navigate(action)
        }

        return mView
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                Log.d("RestaurantsBottomSheet", e.message.toString())
            }
        }
    }
}