package com.basecamp.campong

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.basecamp.campong.databinding.RvItemBinding
import com.basecamp.campong.model.Post
import com.basecamp.campong.utils.API
import com.bumptech.glide.Glide

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.MainViewHolder>() {

    private var postList: MutableList<Post> = mutableListOf()
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        mContext = parent.context

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

            val url = "${API.BASE_URL}/image/${post.imageid}"

            Glide.with(itemView)
                .load(url)
                .centerCrop()
                .into(binding.imageView)
        }

    }

    fun setList(postList: List<Post>) {
        this.postList.addAll(postList)
        notifyDataSetChanged()
    }

}