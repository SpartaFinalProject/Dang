package com.android.dang.shelter.view

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.dang.R
import com.android.dang.databinding.FragmentShelterBinding
import com.android.dang.retrofit.Constants
import com.android.dang.retrofit.abandonedDog.AbandonedShelter
import com.android.dang.retrofit.kind.Items
import com.android.dang.retrofit.sido.Sido
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


// TODO: api 호출 중에 로딩바? 띄워 유저가 상호작용하지 못하게 해야 함.
// TODO: 검색결과가 없을시에 Toast 를 띄워 안내할것
class ShelterFragment : Fragment() {
    private lateinit var binding: FragmentShelterBinding
    private lateinit var viewModel: ShelterViewModel
    private var kakaoMap: KakaoMap? = null
    private val duration = 500

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShelterBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this)[ShelterViewModel::class.java]

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
            }
        })

        return binding.root
    }

    private fun setLabel(geoPoint: GeoPoint, dog: AbandonedShelter) {
        val pos = LatLng.from(geoPoint.latitude, geoPoint.longitude)
        val styles = kakaoMap?.labelManager
            ?.addLabelStyles(LabelStyles.from(LabelStyle.from(R.drawable.icon_pink_marker)))
        val options = LabelOptions.from(pos)
            .setStyles(styles)
            .setClickable(true)

        val layer = kakaoMap?.labelManager?.layer
        val label: Label? = layer?.addLabel(options)
        label?.tag = dog.desertionNo

        Log.d(Constants.TestTAG, "setPin: ${label?.labelId}")
        kakaoMap?.setOnLabelClickListener { kakaoMap, layer, label ->
            viewModel.getShelterInfo(label.tag as String)?.let {
                setShelterInfo(it)
                showInfoWindow(it)
            }
        }
        kakaoMap?.moveCamera(
            CameraUpdateFactory.newCenterPosition(pos, 13),
            CameraAnimation.from(duration)
        )
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
    }

    private val onClickSigungu = { sigungu: Sido ->
        sigungu.orgCd.let { viewModel.setOrgCode(it) }
        binding.selectLocationDetail.text = sigungu.orgdownNm
        viewModel.getAbandonedDogs()
    }

    private val sidoObserver = Observer<Items<Sido>> { sidoList ->
        binding.selectLocationMain.text = sidoList.item[0].orgdownNm
        viewModel.getSigunguList(sidoList.item[0].orgCd)
    }

    private val sigunguObserver = Observer<Items<Sido>> { sigunguList ->
        removeAllMarkers()
        if (sigunguList.item.isNotEmpty()) {
            binding.selectLocationDetail.text = sigunguList.item[0].orgdownNm
            return@Observer
        }
        binding.selectLocationDetail.text = ""
    }

    private val abandonedDogObserver = Observer<List<AbandonedShelter>> {
        Log.d("test", "main abandonedDogs: ${it.size}")
        it.forEach { dog ->
            dog.careAddr?.let { it1 ->
                val addr = viewModel.findGeoPoint(it1)
                if (addr != null && dog.desertionNo != null) {
                    setLabel(addr, dog)
                }
            }
        }
        Log.d(Constants.TestTAG, "dogsss: $it")
    }

    private fun setShelterInfo(dog: AbandonedShelter) = with(binding) {
        shelterName.text = dog.careNm
        shelterLocation.text = dog.careAddr
        shelterPhone.text = dog.careTel
    }

    private fun showInfoWindow(dog: AbandonedShelter) {
        kakaoMap?.mapWidgetManager?.infoWindowLayer?.removeAll()

        val pos = LatLng.from(dog.pos!!.latitude, dog.pos.longitude)
        val body = GuiLayout(Orientation.Horizontal)
        body.setPadding(15, 15, 15, 15)

        val bgImage = GuiImage(R.drawable.icon_window_body, true)
        bgImage.setFixedArea(5, 5, 5, 5)

        body.setBackground(bgImage)

        val text = GuiText(dog.careNm)
        text.setTextSize(25)
        body.addView(text)

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