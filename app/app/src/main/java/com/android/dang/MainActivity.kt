package com.android.dang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.dang.databinding.ActivityMainBinding
import com.android.dang.home.HomeFragment
import com.android.dang.home.retrofit.HomeItemModel
import com.android.dang.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val shelterFragment = BlankFragment()
        val likeFragment = BlankFragment2()
        val dictionaryFragment = BlankFragment3()

        switchFragment(homeFragment)
        binding.icBack.visibility = View.INVISIBLE

        binding.navBar.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menu_home -> {
                    binding.txtTitle.text = "Dang"
                    switchFragment(homeFragment)
                }
                R.id.menu_shelter -> {
                    binding.txtTitle.text = "댕지킴이"
                    switchFragment(shelterFragment)
                }
                R.id.menu_like -> {
                    binding.txtTitle.text = "댕찜"
                    switchFragment(likeFragment)
                }
                R.id.menu_dictionary -> {
                    binding.txtTitle.text = "댕댕백과"
                    switchFragment(dictionaryFragment)
                }
            }
            true
        }

        binding.icSearch.setOnClickListener{
            binding.txtTitle.text = "댕찾기"
            switchFragment(searchFragment)
        }

        binding.icBack.setOnClickListener{
        }

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