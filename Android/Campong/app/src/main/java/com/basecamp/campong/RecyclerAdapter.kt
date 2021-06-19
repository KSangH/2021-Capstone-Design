package com.basecamp.campong

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.basecamp.campong.databinding.RvItemBinding
import com.basecamp.campong.model.Post
import com.basecamp.campong.utils.API
import com.basecamp.campong.utils.Keyword
import com.basecamp.campong.utils.timeDiff
import com.basecamp.campong.view.ShowPostActivity
import com.bumptech.glide.Glide
import java.time.ZoneId

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
            val uploadTime: Long = post.uploaddate.atZone(ZoneId.of("Asia/Seoul")).toEpochSecond()

            binding.apply {
                uploadDateTextView.text = timeDiff(uploadTime)
                postItem = post
            }

            val url = "${API.BASE_URL}/image/${post.imageid}"

            Glide.with(itemView)
                .load(url)
                .centerCrop()
                .into(binding.imageView)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ShowPostActivity::class.java)
                intent.putExtra(Keyword.POST_ID, post.postid)
                startActivity(itemView.context, intent, null)
            }

        }

    }

    fun setList(postList: List<Post>) {
        this.postList.addAll(postList)
        notifyDataSetChanged()
    }

    fun removeAll() {
        this.postList.clear()
        notifyDataSetChanged()
    }
}