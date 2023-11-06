package com.android.dang

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.dang.common.Constants.SELECTED_BREED_NAME
import com.android.dang.databinding.ActivityMainBinding
import com.android.dang.detailFragment.DogDetailFragment
import com.android.dang.dictionary.DictionaryFragment
import com.android.dang.dictionary.OnDictionaryListener
import com.android.dang.home.HomeFragment
import com.android.dang.like.LikeFragment
import com.android.dang.search.SearchFragment
import com.android.dang.search.searchItemModel.SearchDogData
import com.android.dang.shelter.view.ShelterFragment
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), SearchFragment.DogData, OnDictionaryListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val dogDetailFragment = DogDetailFragment()
    private var backPressedTime:Long = 0
    //댕댕백과 인터페이스 호출할 때 사용하려고 멤버변수로 변경
    private val searchFragment by lazy {
        SearchFragment().apply {
            arguments = Bundle()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val shelterFragment = ShelterFragment()
        val likeFragment = LikeFragment()
        val dictionaryFragment = DictionaryFragment()


        switchFragment(homeFragment)
        binding.icBack.visibility = View.INVISIBLE

        binding.navBar.setOnItemSelectedListener {
            val activeFragment = supportFragmentManager.findFragmentById(binding.fragmentView.id)
            //이미 활성화된 경우 중복요청 방지
            when (it.itemId) {
                R.id.menu_home -> if (activeFragment is HomeFragment) return@setOnItemSelectedListener true
                R.id.menu_shelter -> if (activeFragment is ShelterFragment) return@setOnItemSelectedListener true
                R.id.menu_like -> if (activeFragment is BlankFragment2) return@setOnItemSelectedListener true
                R.id.menu_dictionary -> if (activeFragment is DictionaryFragment) return@setOnItemSelectedListener true
            }

            when (it.itemId) {
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

        binding.icSearch.setOnClickListener {
            binding.txtTitle.text = "댕찾기"
            switchFragment(searchFragment)
        }

        binding.icBack.setOnClickListener {}

        searchFragment.dogData(this)

    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.fragmentView.id, fragment)
            .addToBackStack(null)
            .commit() //백스택 처리가 백그라운드나 비정상적인 상황에서 발생할 수 있는 경우 commitAllowingStateLoss()
    }

    private fun setFragment(frag: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_view, frag)
            .setReorderingAllowed(true).addToBackStack(null).commit()
    }

    override fun pass(data: SearchDogData) {
        dogDetailFragment.receiveData(data)
        Log.d("aaa", "$data aaa")
        setFragment(dogDetailFragment)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
            binding.fragmentView.post {
                //백스택처리 후 프래그먼트 체크
                when (supportFragmentManager.fragments.lastOrNull()) {
                    is HomeFragment -> binding.navBar.selectedItemId = R.id.menu_home
                    is ShelterFragment -> binding.navBar.selectedItemId = R.id.menu_shelter
                    is BlankFragment2 -> binding.navBar.selectedItemId = R.id.menu_like
                    is DictionaryFragment -> binding.navBar.selectedItemId = R.id.menu_dictionary
                }
            }
        } else {
            finish()
        }

        if(System.currentTimeMillis() - backPressedTime >=1000 ) {
            backPressedTime = System.currentTimeMillis()
        } else {
            finish() //액티비티 종료
        }
    }

    override fun onDictionaryItemSelected(breedName: String) {
        searchFragment.arguments?.putString(SELECTED_BREED_NAME, breedName) //Bundle은 위에서 생성했음
        switchFragment(searchFragment)
    }
}