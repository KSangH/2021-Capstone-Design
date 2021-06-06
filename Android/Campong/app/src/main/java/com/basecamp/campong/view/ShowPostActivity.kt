package com.basecamp.campong.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.basecamp.campong.R
import com.basecamp.campong.databinding.ActivityShowPostBinding
import com.basecamp.campong.model.Post
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.API
import com.basecamp.campong.utils.Constants
import com.basecamp.campong.utils.Keyword
import com.basecamp.campong.utils.RequestCode.EDIT_POST
import com.basecamp.campong.utils.RequestCode.GO_TO_RESERVE
import com.bumptech.glide.Glide
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker

class ShowPostActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mBinding: ActivityShowPostBinding
    private var naverMap: NaverMap? = null
    private var postid: Long? = null
    private var post: Post? = null
    private var marker: Marker? = null
    private var lat: Double? = null
    private var lon: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityShowPostBinding.inflate(layoutInflater)

        postid = intent.getLongExtra(Keyword.POST_ID, -1)

        if (postid != null) {
            getPost(postid!!)
        }

        setContentView(mBinding.root)

        // 지도 객체 가져오기
        val fm: FragmentManager = supportFragmentManager
        var mapFragment: MapFragment? = fm.findFragmentById(R.id.smallMap) as MapFragment
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance()
            fm.beginTransaction().add(R.id.smallMap, mapFragment).commit()
        }
        mapFragment!!.getMapAsync(this)
    }

    private fun initToolbar() {
        val toolbar = mBinding.toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayShowTitleEnabled(false)
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(
            R.menu.s_m_p_toolbar_menu,
            menu
        )       // main_menu 메뉴를 toolbar 메뉴 버튼으로 설정
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                setResult(RESULT_OK)
                finish()
                return true
            }
            R.id.modify -> {
                Log.d(Constants.TAG, "수정 클릭됨")
                goToEditPost()
            }
            R.id.delete -> {
                deletePost()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToEditPost() {
        val intent = Intent(this, EditPostActivity::class.java)
        intent.putExtra(Keyword.POST_ID, postid)
        startActivityForResult(intent, EDIT_POST)
    }

    private fun getPost(postid: Long) {
        RetrofitManager.instance.requestPostView(postid) { code, mypost, post ->
            when (code) {
                0 -> {
                    if (mypost != null) {
                        if (mypost) {
                            initToolbar()
                            mBinding.button.text = "예약 내역 보기"
                            mBinding.button.setOnClickListener {
                                goToReserveList()
                            }
                        } else {
                            if (post!!.reservestate == 1) {
                                mBinding.button.text = "예약 완료"
                                mBinding.button.isEnabled = false
                            } else {
                                mBinding.button.text = "예약하기"
                                mBinding.button.setOnClickListener {
                                    goToReserve()
                                }
                            }
                        }
                    }
                    if (post != null) {
                        this.post = post
                        val url = "${API.BASE_URL}/image/${post.imageid}"

                        Glide.with(this)
                            .load(url)
                            .centerCrop()
                            .into(mBinding.imageView)

                        mBinding.apply {
                            postItem = post
                        }

                        // 네이버 맵 준비 완료인 경우
                        if (naverMap != null) {
                            setLocationToUI(post.lat.toDouble(), post.lon.toDouble())
                            // 완료되지 않은 경우 onMapReady callback에서 실행
                        }
                    }
                }
                else -> {
                    Log.d(Constants.TAG, "ShowPostActivity - getPost() : 게시물을 불러오지 못했습니다.")
                    Toast.makeText(this, "게시물을 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // 예약하기
    private fun goToReserve() {
        val intent = Intent(this, ReqReserveActivity::class.java)
        intent.putExtra(Keyword.POST_ID, postid)

        RetrofitManager.instance.requestReserveInit(postid!!) {
            when (it) {
                0 -> {
                    if (post != null) {
                        intent.putExtra(Keyword.TITLE, post!!.title)
                        intent.putExtra(Keyword.LOCATION, post!!.location)
                        intent.putExtra(Keyword.FEE, post!!.fee)
                        intent.putExtra(Keyword.IMAGE_ID, post!!.imageid)
                        startActivityForResult(intent, GO_TO_RESERVE)
                    }
                }
                else -> {
                    Toast.makeText(this, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // 예약내역 보기
    private fun goToReserveList() {
        val intent = Intent(this, LendActivity::class.java)
        startActivity(intent)
    }

    private fun setLocationToUI(lat: Double, lon: Double) {
        if (marker == null) {
            marker = Marker()
        }
        marker!!.position = LatLng(lat, lon)
        marker!!.map = naverMap

        val cameraUpdate = CameraUpdate.scrollTo(LatLng(lat, lon))
        naverMap?.moveCamera(cameraUpdate)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        if (lat != null && lon != null) {
            setLocationToUI(lat!!, lon!!)
        }
    }

    private fun deletePost() {
        RetrofitManager.instance.requestDeletePost(
            postid!!
        ) {
            when (it) {
                0 -> {
                    Toast.makeText(applicationContext, "게시물이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else -> {
                    Toast.makeText(applicationContext, "게시물 삭제를 실패하였습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK) return
        when (requestCode) {
            EDIT_POST -> {
                getPost(postid!!)
            }
            GO_TO_RESERVE -> {
                mBinding.button.apply {
                    text = "예약 완료"
                    isEnabled = false
                }
            }
        }
    }
}