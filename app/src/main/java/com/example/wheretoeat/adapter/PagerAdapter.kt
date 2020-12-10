package com.example.wheretoeat.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter(
    private val restaurantBundle: Bundle,
    private val fragments: List<Fragment>,
    private val title: List<String>,
    fm: FragmentManager
): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount() = fragments.size

    override fun getItem(position: Int): Fragment {
        fragments[position].arguments = restaurantBundle
        return fragments[position]
    }

    override fun getPageTitle(position: Int) = title[position]
}