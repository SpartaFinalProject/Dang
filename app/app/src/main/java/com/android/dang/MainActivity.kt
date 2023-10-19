package com.android.dang


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.dang.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)


        supportFragmentManager.beginTransaction().replace(R.id.fragment_view, ShelterFragment())
            .commit()
    }
}