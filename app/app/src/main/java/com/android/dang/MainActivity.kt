package com.android.dang

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.dang.databinding.ActivityMainBinding
import com.android.dang.databinding.HeaderBinding
import com.android.dang.home.HomeFragment
import com.android.dang.home.retrofit.HomeItemModel
import com.android.dang.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        val homeFragment = HomeFragment()
        switchFragment(homeFragment)

    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentView.id, fragment)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}