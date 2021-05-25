package com.basecamp.campong.view.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.basecamp.campong.R
import com.basecamp.campong.RecyclerAdapter
import com.basecamp.campong.SearchLocationActivity
import com.basecamp.campong.databinding.FragmentHomeBinding
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.Constants
import com.basecamp.campong.utils.RequestCode
import com.basecamp.campong.view.ScanQrActivity
import com.basecamp.campong.view.SearchActivity
import com.basecamp.campong.view.WritePostActivity


class HomeFragment : Fragment(), View.OnClickListener {

    private var mBinding: FragmentHomeBinding? = null
    private lateinit var mAdapter: RecyclerAdapter
    private var location: String? = null
    private var pageNum: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        mBinding = binding

        setHasOptionsMenu(true)

        mAdapter = RecyclerAdapter()

        getPostList(pageNum) // 게시물 목록 조회

        binding.recyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.addButton.setOnClickListener(this)
        binding.scannerButton.setOnClickListener(this)
        binding.searchButton.setOnClickListener(this)
        binding.locationButton.setOnClickListener(this)

        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    private fun goToSearchLocation(view: View) {
        val intent = Intent(context, SearchLocationActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.addButton -> {
                goToWritePost(v)
            }
            R.id.scannerButton -> {
                goToScanQR(v)
            }
            R.id.searchButton -> {
                goToSearch(v)
            }
            R.id.locationButton ->{
                goToSearchLocation(v)
            }
        }
    }

    /* QR 스캔 화면으로 이동*/
    private fun goToScanQR(view: View) {
        val intent = Intent(context, ScanQrActivity::class.java)
        startActivity(intent)
    }

    /* 게시물 등록 화면으로 이동 */
    private fun goToWritePost(view: View) {
        val intent = Intent(context, WritePostActivity::class.java)
        startActivityForResult(intent, RequestCode.WRITE_POST)
    }

    /* 검색 화면으로 이동 */
    private fun goToSearch(view: View) {
        val intent = Intent(context, SearchActivity::class.java)
        startActivity(intent)
    }

    private fun pageUp() {
        pageNum++
    }

    private fun pageReset() {
        pageNum = 0
    }

    /* 서버에서 게시물 목록 가져오기 */
    private fun getPostList(pageNum: Int) {
        RetrofitManager.instance.requestPostList(pageNum, location) { code, data ->
            when (code) {
                0 -> {
                    if (data != null) {
                        Log.d(Constants.TAG, "HomeFragment - getPostList() : data is not null!!")
                        mAdapter.setList(data)
                        pageUp() // 통신에 성공하면 다음 요청을 위해 pageNum++
                    } else {
                        Log.d(Constants.TAG, "HomeFragment - getPostList() : data is null!!")
                    }
                }
                else -> {
                    Log.d(Constants.TAG, "HomeFragment - getPostList() : 통신 실패")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            mAdapter.removeAll()
            pageReset()
            getPostList(pageNum)
        }

    }
}