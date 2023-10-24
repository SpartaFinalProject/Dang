package com.android.dang.shelter.vm

import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.dang.retrofit.Constants
import com.android.dang.retrofit.DangClient
import com.android.dang.retrofit.abandonedDog.AbandonedDog
import com.android.dang.retrofit.abandonedDog.AbandonedDogRes
import com.android.dang.retrofit.kind.Items
import com.android.dang.retrofit.sido.Sido
import com.android.dang.retrofit.sido.SidoRes
import com.google.firebase.firestore.GeoPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ShelterViewModel : ViewModel() {
    private lateinit var geocoder: Geocoder
    val sido: LiveData<Items<Sido>>
        get() = _sido
    private val _sido: MutableLiveData<Items<Sido>> = MutableLiveData()

    val sigungu: LiveData<Items<Sido>>
        get() = _sigungu
    private val _sigungu: MutableLiveData<Items<Sido>> = MutableLiveData()

    private val orgCode: LiveData<String>
        get() = _orgCode
    private val _orgCode = MutableLiveData("")

    private val uprCode: LiveData<String>
        get() = _uprCode
    private val _uprCode = MutableLiveData("")

    val abandonedDogsList: LiveData<List<AbandonedDog>>
        get() = _abandonedDogsList
    private val _abandonedDogsList =
        MutableLiveData((listOf(AbandonedDog())))

    fun getSidoList() {
        DangClient.api.getSidoList().enqueue(object : retrofit2.Callback<SidoRes> {
            override fun onResponse(call: Call<SidoRes>, response: Response<SidoRes>) {
                if (!response.isSuccessful) {
                    // Failed
                    return
                }

                response.body()?.response?.body?.items.let {
                    Log.d("test", "onResponse: $this")
                    _sido.value = it
                }
            }

            override fun onFailure(call: Call<SidoRes>, t: Throwable) {

            }
        })
    }

    fun getSigunguList(code: String) {
        DangClient.api.getSigunguList(code = code).enqueue(object : Callback<SidoRes> {
            override fun onResponse(call: Call<SidoRes>, response: Response<SidoRes>) {
                Log.d("test", "sigungu onResponse: $response")
                val sigunguList = response.body()?.response?.body?.items
                if (!response.isSuccessful) {
                    return
                }
                if (!sigunguList?.item.isNullOrEmpty()) {
                    _sigungu.value = sigunguList
                    return
                }
                _sigungu.value = Items(mutableListOf())
            }

            override fun onFailure(call: Call<SidoRes>, t: Throwable) {
                Log.d("test", "sigungunn onFailure: ${t.localizedMessage}")
            }
        })
    }

    fun getAbandonedDogs() {
        Log.d("test", "abandonedDogShelter: ${orgCode.value} / $uprCode.value")
        DangClient.api.abandonedDogSearch(
            uprCode = uprCode.value,
            orgCode = orgCode.value,
            upkind = 417000,
            numOfRows = 10
        ).enqueue(object : Callback<AbandonedDogRes?> {
            override fun onResponse(
                call: Call<AbandonedDogRes?>,
                response: Response<AbandonedDogRes?>
            ) {
                val abandonedDogList = response.body()?.response?.body?.items?.item?.toMutableList()
                if (!response.isSuccessful) {
                    return
                }
                if (abandonedDogList.isNullOrEmpty()) {
                    _abandonedDogsList.value = listOf()
                    return
                }
                abandonedDogList.forEachIndexed { index, dog -> //자동으로 index에는 index값이 i는 value값이 들어감
                    abandonedDogList[index] = dog?.copy(
                        pos = findGeoPoint(dog.careAddr ?: "")
                    )
               }

                _abandonedDogsList.value = abandonedDogList?.filterNotNull()
                Log.d(Constants.TestTAG, "abandoned onResponse: ${response.body()?.response?.body}")
            }

            override fun onFailure(call: Call<AbandonedDogRes?>, t: Throwable) {

            }
        })
    }

    fun setOrgCode(orgCode: String) {
        Log.d(Constants.TestTAG, "setOrgCode: $orgCode")
        _orgCode.value = orgCode
    }

    fun setUprCode(uprCode: String) {
        Log.d(Constants.TestTAG, "setUprCode: $uprCode")
        _uprCode.value = uprCode
    }

    fun getShelterInfo(desertionNo: String): AbandonedDog? {
        return abandonedDogsList.value?.find {
            it.desertionNo == desertionNo
        }
    }

    fun findGeoPoint(address: String): GeoPoint? {
        val addr: Address
        var location: GeoPoint? = null
        try {
            val listAddress: List<Address>? = geocoder.getFromLocationName(address, 1)
            if (listAddress!!.isNotEmpty()) { // 주소값이 존재 하면
                addr = listAddress[0] // Address형태로
                val lat = (addr.latitude)
                val lng = (addr.longitude)
                Log.d(Constants.TestTAG, "findGeoPoint: $lat / $lng")
                location = GeoPoint(lat, lng)
                Log.d(Constants.TestTAG, "주소로부터 취득한 위도 : $lat, 경도 : $lng")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return location
    }

    fun setGeoCoder(geocoder: Geocoder) {
        this.geocoder = geocoder
    }
}