package com.example.wheretoeat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.example.wheretoeat.data.RestaurantRepository
import com.example.wheretoeat.network.RestaurantApiRepository
import com.example.wheretoeat.ui.list.ListViewModel
import com.example.wheretoeat.ui.list.ListViewModelFactory

class SplashActivity : AppCompatActivity() {
    private lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}