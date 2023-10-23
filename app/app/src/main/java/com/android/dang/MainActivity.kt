package com.android.dang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.android.dang.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(binding.fragmentView.id, BlankFragment()).commit()

        binding.navBar.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction().replace(binding.fragmentView.id, BlankFragment()).commit()
                }
                R.id.menu_shelter -> {
                    supportFragmentManager.beginTransaction().replace(binding.fragmentView.id, BlankFragment2()).commit()
                }
                R.id.menu_like -> {
                    supportFragmentManager.beginTransaction().replace(binding.fragmentView.id, BlankFragment3()).commit()
                }
                R.id.menu_dictionary -> {
                    supportFragmentManager.beginTransaction().replace(binding.fragmentView.id, BlankFragment4()).commit()
                }
            }
            true
        }
        
    }
}