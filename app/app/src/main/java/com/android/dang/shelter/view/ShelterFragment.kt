package com.android.dang.shelter.view

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.dang.MainViewModel
import com.android.dang.R
import com.android.dang.databinding.FragmentShelterBinding
import com.android.dang.retrofit.Constants
import com.android.dang.retrofit.kind.Items
import com.android.dang.retrofit.sido.Sido
import com.android.dang.search.searchItemModel.SearchDogData
import com.android.dang.shelter.shelterresult.ShelterResultFragment
import com.android.dang.shelter.vm.ShelterViewModel
import com.google.firebase.firestore.GeoPoint
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.camera.CameraAnimation
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.Label
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.kakao.vectormap.mapwidget.InfoWindowOptions
import com.kakao.vectormap.mapwidget.component.GuiImage
import com.kakao.vectormap.mapwidget.component.GuiLayout
import com.kakao.vectormap.mapwidget.component.GuiText
import com.kakao.vectormap.mapwidget.component.Orientation

class ShelterFragment : Fragment() {
    private lateinit var binding: FragmentShelterBinding
    private lateinit var viewModel: ShelterViewModel
    private lateinit var mainViewModel: MainViewModel
    private var kakaoMap: KakaoMap? = null
    private val duration = 500

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShelterBinding.inflate(layoutInflater, container, false)

        viewModel = ViewModelProvider(this)[ShelterViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        binding.progressDictionary.visibility = View.VISIBLE

        viewModel.run {
            setGeoCoder(Geocoder(requireContext()))
            sido.observe(viewLifecycleOwner, sidoObserver)
            sigungu.observe(viewLifecycleOwner, sigunguObserver)
            abandonedDogsList.observe(viewLifecycleOwner, abandonedDogObserver)
        }

        binding.mapView.start(object : KakaoMapReadyCallback() {


            override fun getPosition(): LatLng {
                return LatLng.from(37.393865, 127.115795)
            }

            override fun onMapReady(kakaoMap: KakaoMap) {
                this@ShelterFragment.kakaoMap = kakaoMap
                binding.progressDictionary.visibility = View.GONE
            }

        })


        binding.shelterSelectBtn.setOnClickListener {
            if (!viewModel.orgCode.value.isNullOrBlank() && !viewModel.uprCode.value.isNullOrBlank()) {
                selectShelterResultFragment()
                return@setOnClickListener
            }
            Toast.makeText(requireContext(), "보호소 핀 터치 후 선택해 주세요 ", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun selectShelterResultFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_view, ShelterResultFragment())
            .addToBackStack(null)
            .commit()
    }


    private fun setLabel(geoPoint: GeoPoint, dog: SearchDogData) {

        val pos = LatLng.from(geoPoint.latitude, geoPoint.longitude)
        val styles = kakaoMap?.labelManager
            ?.addLabelStyles(LabelStyles.from(LabelStyle.from(R.drawable.icon_pink_marker)))
        if (kakaoMap != null) {
            val options = LabelOptions.from(pos)
                .setStyles(styles)
                .setClickable(true)

            val layer = kakaoMap?.labelManager?.layer
            val label: Label? = layer?.addLabel(options)
            label?.tag = dog.popfile

            Log.d(Constants.TestTAG, "setPin: ${label?.labelId}")

            label?.let {
                kakaoMap?.setOnLabelClickListener { _, _, clickedLabel ->
                    viewModel.getShelterInfo(clickedLabel.tag as String)?.let { clickedDog ->
                        setShelterInfo(clickedDog)
                        showInfoWindow(clickedDog)
                    }
                }
            }

            kakaoMap?.moveCamera(
                CameraUpdateFactory.newCenterPosition(pos, 14),
                CameraAnimation.from(duration)
            )
            binding.progressDictionary2.visibility = View.GONE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSidoList()
        binding.selectLocationMain.setOnClickListener {
            viewModel.sido.value?.item?.let { list ->
                val builder = SidoDialog(list, onClickSido)
                builder.show(childFragmentManager, "")
            }
        }


        binding.selectLocationDetail.setOnClickListener {
            viewModel.sigungu.value?.let { list ->
                if (list.item.isEmpty()) {
                    return@setOnClickListener
                }
                val builder = SidoDialog(list.item, onClickSigungu)
                builder.show(childFragmentManager, "")
            }
        }
    }

    private val onClickSido = { sido: Sido ->
        Log.d("test", "onClickSido: $sido")

        viewModel.run {
            getSigunguList(sido.orgCd)
            setUprCode(sido.orgCd)
        }

        binding.selectLocationMain.text = sido.orgdownNm
        if (sido.orgCd == "5690000") {
            viewModel.getAbandonedDogs()
        }
    }

    private val onClickSigungu = { sigungu: Sido ->
        sigungu.orgCd.let { viewModel.setOrgCode(it) }
        sigungu.uprCd?.let { viewModel.setUprCode(it) }
        binding.selectLocationDetail.text = sigungu.orgdownNm
        viewModel.getAbandonedDogs()
        binding.progressDictionary2.visibility = View.VISIBLE
    }

    private val sidoObserver = Observer<Items<Sido>> { sidoList ->
        binding.selectLocationMain.text = sidoList.item[0].orgdownNm
        viewModel.getSigunguList(sidoList.item[0].orgCd)
    }

    private val sigunguObserver = Observer<Items<Sido>> { sigunguList ->
        removeAllMarkers()
        if (sigunguList.item.isNotEmpty()) {
            binding.selectLocationDetail.text = sigunguList.item[1].orgdownNm
            return@Observer
        }
        binding.selectLocationDetail.text = ""
    }

    private val abandonedDogObserver = Observer<List<SearchDogData>> {
        mainViewModel.setAbandonedDogsList(it)
        Log.d("test", "main abandonedDogs: ${it.size}")

        if (it.isEmpty()) {
            Toast.makeText(requireContext(), "선택한 지역에서는 보호소가 없습니다. 다른 지역을 선택해주세요.", Toast.LENGTH_LONG).show()
            binding.progressDictionary2.visibility = View.GONE
            removeAllMarkers()
        }
        it.parallelStream().forEach { dog ->
            dog.careAddr?.let { it1 ->
                val addr = viewModel.findGeoPoint(it1)
                if (addr != null && dog.popfile != null) {
                    setLabel(addr, dog)
                }
            }
        }
        Log.d(Constants.TestTAG, "dogsss: $it")
    }

    private fun setShelterInfo(dog: SearchDogData) = with(binding) {
        shelterName.text = dog.careNm
        shelterLocation.text = dog.careAddr
        shelterPhone.text = dog.careTel
    }

    private fun showInfoWindow(dog: SearchDogData) {
        kakaoMap?.mapWidgetManager?.infoWindowLayer?.removeAll()


        val pos = dog.pos?.let { LatLng.from(it.latitude, it.longitude) }
        val body = GuiLayout(Orientation.Vertical)
        body.setPadding(15, 15, 15, 15)

        val bgImage = GuiImage(R.drawable.icon_window_body, true)
        bgImage.setFixedArea(5, 5, 5, 5)

        body.setBackground(bgImage)

        val shelterName = GuiText(dog.careNm)
        shelterName.setTextSize(25)

        val upperLayout = GuiLayout(Orientation.Horizontal)
        upperLayout.addView(shelterName)

        val dogsCount = GuiText("${viewModel.getDogCount(dog.careNm!!)}마리 보호중")
        dogsCount.paddingTop = 8
        dogsCount.setTextSize(23)
        body.addView(upperLayout)
        body.addView(dogsCount)

        val options = InfoWindowOptions.from(pos)
        options.setBody(body)
        options.setBodyOffset(0f, -100f)

        kakaoMap!!.mapWidgetManager!!.infoWindowLayer.addInfoWindow(options)
    }

    private fun removeAllMarkers() {
        kakaoMap?.mapWidgetManager?.infoWindowLayer?.removeAll()
        with(binding) {
            shelterName.text = ""
            shelterPhone.text = ""
            shelterLocation.text = ""
        }
    }

}