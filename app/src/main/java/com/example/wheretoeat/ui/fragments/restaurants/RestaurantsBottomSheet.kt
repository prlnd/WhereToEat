package com.example.wheretoeat.ui.fragments.restaurants

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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

    private var cityName = ""

    private var priceCategoryChip = DEFAULT_PRICE
    private var priceCategoryChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelProvider = ViewModelProvider(requireActivity())
        restaurantsViewModel = viewModelProvider.get(RestaurantsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mView = inflater.inflate(R.layout.restaurants_bottom_sheet, container, false)
        setupSpinner(mView)
        setupChip(mView)
        mView.apply_button.setOnClickListener {
            restaurantsViewModel.apply {
                savePriceCategory(priceCategoryChip, priceCategoryChipId)
                saveCityName(cityName)
            }
            val action =
                RestaurantsBottomSheetDirections.actionRestaurantsBottomSheetToRestaurantsFragment(
                    true
                )
            findNavController().navigate(action)
        }
        return mView
    }

    private fun setupSpinner(mView: View) {
        val spinner = mView.cityFilter_searchableSpinner
        val cities = restaurantsViewModel.cities

        spinner.adapter = activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                cities.value
            )
        }

        restaurantsViewModel.readCityName.asLiveData().observe(viewLifecycleOwner, { value ->
            cityName = value
            val position = cities.value.indexOf(cityName)
            if (position >= 0) {
                spinner.setSelection(position)
            }
        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                cityName = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    private fun setupChip(mView: View) {
        restaurantsViewModel.readPriceCategory.asLiveData().observe(viewLifecycleOwner, { value ->
            priceCategoryChip = value.selectedPriceCategory
            priceCategoryChipId = value.selectedPriceCategoryId
            updateChip(value.selectedPriceCategoryId, mView.price_chipGroup)
        })

        mView.price_chipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedPriceCategory = chip.text.toString().toInt()
            priceCategoryChip = selectedPriceCategory
            priceCategoryChipId = selectedChipId
        }
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