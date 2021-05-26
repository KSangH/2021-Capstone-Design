package com.basecamp.campong.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import com.basecamp.campong.R
import com.basecamp.campong.databinding.ActivitySetMapBinding
import com.basecamp.campong.retrofit.RetrofitManager
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource

class SetMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mBinding: ActivitySetMapBinding
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySetMapBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initToolbar()

        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE)
        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        // 지도 객체 가져오기
        val fm: FragmentManager = supportFragmentManager
        var mapFragment: MapFragment? = fm.findFragmentById(R.id.setMapFragment) as MapFragment
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance()
            fm.beginTransaction().add(R.id.map, mapFragment).commit()
        }
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        naverMap.locationSource = locationSource
        naverMap.addOnCameraChangeListener { _, _ -> setDisableToUI() }

        naverMap.addOnCameraIdleListener {
            // 카메라 이동이 멈추면
            val cameraPosition = naverMap.cameraPosition

            // 현재 카메라 위치의 위도, 경도 받아오기
            val lat = cameraPosition.target.latitude
            val lon = cameraPosition.target.longitude

            getAddress(lat, lon)
            setEnableButton()
        }
    }

    private fun setDisableToUI() {
        mBinding.locationLabel.text = "위치 이동중"
        mBinding.setMapButton.isEnabled = false
    }

    private fun setEnableButton() {
        mBinding.setMapButton.isEnabled = true
    }

    private fun getAddress(lat: Double, lon: Double) {
        RetrofitManager.instance.requestReverseGeocoding(
            lat,
            lon
        ) { code, baseaddr, addr, roadaddr ->
            when (code) {
                0 -> {
                    mBinding.locationLabel.text = "$addr, $roadaddr"
                }
                else -> {
                    Toast.makeText(
                        applicationContext,
                        "오류가 발생하였습니다", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    // 현재 위치 권한 요청
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private const val PERMISSION_REQUEST_CODE = 100
        private val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private fun initToolbar() {
        val toolbar = mBinding.toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.title = "거래 위치 설정"
        ab?.setDisplayShowTitleEnabled(true)
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}