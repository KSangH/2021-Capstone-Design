package com.basecamp.campong.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.basecamp.campong.R
import com.basecamp.campong.RecyclerAdapter
import com.basecamp.campong.databinding.FragmentHomeBinding
import com.basecamp.campong.utils.postList
import com.basecamp.campong.view.WritePostActivity


class HomeFragment : Fragment(), View.OnClickListener {

    private var mBinding: FragmentHomeBinding? = null
    lateinit var mAdapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        mBinding = binding

//        val toolbar = mBinding!!.toolbar
//        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)

        setHasOptionsMenu(true)

        val tmpList = postList()

        mAdapter = RecyclerAdapter()
        mAdapter.setList(tmpList)

        binding.recyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.addButton.setOnClickListener(this)

        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_toolbar_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun goToWritePost(view: View) {
        val intent = Intent(context, WritePostActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.addButton -> {
                goToWritePost(v)
            }
        }
    }
}