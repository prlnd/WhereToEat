package com.example.wheretoeat.ui.fragments.overview

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.wheretoeat.R
import com.example.wheretoeat.model.Restaurant
import kotlinx.android.synthetic.main.fragment_overview.*
import kotlinx.android.synthetic.main.fragment_overview.view.*

class OverviewFragment : Fragment() {
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_overview, container, false)

        val args = arguments
        val myBundle: Restaurant? = args?.getParcelable("restaurantBundle")

        Glide.with(view.main_imageView.context)
            .load(myBundle?.imageUrl)
            .error(R.drawable.ic_error_placeholder)
            .into(view.main_imageView)

        view.titleDetails_textView.text = myBundle?.name
        view.money_textView.text = myBundle?.price.toString()
        view.addressDetail_textView.text =
            "${myBundle?.address},\n${myBundle?.city}, ${myBundle?.country}"
        view.phone_textView.text = myBundle?.phone

        view.call_button.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_DIAL
            intent.data = Uri.parse("tel:" + myBundle?.phone)
            startActivity(intent)
        }

        view.page_button.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data =  Uri.parse(myBundle?.reserveUrl)
            startActivity(intent)
        }

        return view
    }
}