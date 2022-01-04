package com.example.abstractapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.abstractapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        binding.navDrawer.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected TODO: properly figure out this routing eventually
            menuItem.isChecked = true
            binding.drawerLayout.close()
            true
        }
    }
}