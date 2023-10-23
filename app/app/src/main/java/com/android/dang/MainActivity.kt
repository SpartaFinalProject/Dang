package com.android.dang

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.dang.databinding.ActivityMainBinding
import com.android.dang.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val searchFragment = SearchFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_view, searchFragment)
            .commit()

    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_view, fragment)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}