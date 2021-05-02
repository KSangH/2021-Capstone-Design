package com.basecamp.campong

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.basecamp.campong.databinding.RvItemBinding
import com.basecamp.campong.model.Post

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.MainViewHolder>() {

    private var postList: MutableList<Post> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.rv_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int = postList.size

    class MainViewHolder(private val binding: RvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            binding.apply {
                postItem = post
            }
        }

    }

    fun setList(postList: List<Post>) {
        this.postList = postList as MutableList<Post>
        notifyDataSetChanged()
    }

}