package com.android.dang

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.dang.databinding.ActivityMainBinding
import com.android.dang.detailFragment.DogDetailFragment
import com.android.dang.dictionary.DictionaryFragment
import com.android.dang.home.HomeFragment
import com.android.dang.like.LikeFragment
import com.android.dang.search.SearchFragment
import com.android.dang.search.searchItemModel.SearchDogData
import com.android.dang.shelter.view.ShelterFragment
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), SearchFragment.DogData {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val dogDetailFragment = DogDetailFragment()
    private var backPressedTime:Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
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
                R.id.menu_like -> if (activeFragment is LikeFragment) return@setOnItemSelectedListener true
                R.id.menu_dictionary -> if (activeFragment is DictionaryFragment) return@setOnItemSelectedListener true
            }

            when (it.itemId) {
                R.id.menu_home -> {
                    binding.txtTitle.text = "Dang"
                    binding.icBack.visibility = View.INVISIBLE
                    switchFragment(homeFragment)
                }

                R.id.menu_shelter -> {
                    binding.txtTitle.text = "댕지킴이"
                    binding.icBack.visibility = View.VISIBLE
                    switchFragment(shelterFragment)
                }

                R.id.menu_like -> {
                    binding.txtTitle.text = "댕찜"
                    binding.icBack.visibility = View.VISIBLE
                    switchFragment(likeFragment)
                }

                R.id.menu_dictionary -> {
                    binding.txtTitle.text = "댕댕백과"
                    binding.icBack.visibility = View.VISIBLE
                    switchFragment(dictionaryFragment)
                }
            }
            true
        }

        binding.icSearch.setOnClickListener {
            binding.txtTitle.text = "댕찾기"
            switchFragment(searchFragment)
        }

        binding.icBack.setOnClickListener {
            supportFragmentManager.popBackStack()
            binding.fragmentView.post {
                //백스택처리 후 프래그먼트 체크
                when (supportFragmentManager.fragments.lastOrNull()) {
                    is HomeFragment -> {
                        binding.navBar.selectedItemId = R.id.menu_home
                        binding.txtTitle.text = "Dang"
                        binding.icBack.visibility = View.INVISIBLE
                    }
                    is ShelterFragment -> {
                        binding.navBar.selectedItemId = R.id.menu_shelter
                        binding.txtTitle.text = "댕지킴이"
                    }
                    is LikeFragment -> {
                        binding.navBar.selectedItemId = R.id.menu_like
                        binding.txtTitle.text = "댕찜"
                    }
                    is DictionaryFragment -> {
                        binding.navBar.selectedItemId = R.id.menu_dictionary
                        binding.txtTitle.text = "댕댕백과"
                    }
                    is SearchFragment -> {
                        binding.txtTitle.text = "댕찾기"
                    }
                }
            }
        }

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
//        if (supportFragmentManager.backStackEntryCount > 1) {
//            supportFragmentManager.popBackStack()
//            binding.fragmentView.post {
//                //백스택처리 후 프래그먼트 체크
//                when (supportFragmentManager.fragments.lastOrNull()) {
//                    is HomeFragment -> {
//                        binding.navBar.selectedItemId = R.id.menu_home
//                        binding.txtTitle.text = "Dang"
//                        binding.icBack.visibility = View.INVISIBLE
//                    }
//                    is ShelterFragment -> {
//                        binding.navBar.selectedItemId = R.id.menu_shelter
//                        binding.txtTitle.text = "댕지킴이"
//                    }
//                    is LikeFragment -> {
//                        binding.navBar.selectedItemId = R.id.menu_like
//                        binding.txtTitle.text = "댕찜"
//                    }
//                    is DictionaryFragment -> {
//                        binding.navBar.selectedItemId = R.id.menu_dictionary
//                        binding.txtTitle.text = "댕댕백과"
//                    }
//                }
//            }
//        } else {
//            finish()
//        }

        if(System.currentTimeMillis() - backPressedTime >=2000 ) {
            // 한번누르면 뒤로가고 스낵바띄워줌
            backPressedTime = System.currentTimeMillis()
            Snackbar.make(binding.fragmentView,"뒤로가기 버튼을 한번 더 누르면 종료됩니다.",Snackbar.LENGTH_LONG).show()
        } else {
            // 2초안에 한번더누르면 앱종료
            finish() 
        }
    }
}