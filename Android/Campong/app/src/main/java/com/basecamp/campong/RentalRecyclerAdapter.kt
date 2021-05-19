package com.basecamp.campong

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basecamp.campong.databinding.*
import com.basecamp.campong.model.ReserveItem
import com.basecamp.campong.utils.API
import com.basecamp.campong.utils.RentalState.RENTAL_STATE_CANCEL
import com.basecamp.campong.utils.RentalState.RENTAL_STATE_CONFIRM
import com.basecamp.campong.utils.RentalState.RENTAL_STATE_RENTAL
import com.basecamp.campong.utils.RentalState.RENTAL_STATE_RETURN
import com.basecamp.campong.utils.RentalState.RENTAL_STATE_WAIT
import com.bumptech.glide.Glide

class RentalRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var reservationList: MutableList<ReserveItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            RENTAL_STATE_WAIT -> {
                val binding =
                    RvItemType01Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ItemWaitHolder(binding)
            }
            RENTAL_STATE_CONFIRM -> {
                val binding =
                    RvItemType02Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ItemConfirmHolder(binding)
            }
            RENTAL_STATE_RENTAL -> {
                val binding =
                    RvItemType03Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ItemRentalHolder(binding)
            }
            RENTAL_STATE_RETURN -> {
                val binding =
                    RvItemTypeBaseBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ItemBaseHolder(binding)
            }
            RENTAL_STATE_CANCEL -> {
                val binding =
                    RvItemType00Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ItemCancelHolder(binding)
            }
            else -> {
                val binding =
                    RvItemTypeBaseBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ItemBaseHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemWaitHolder -> {
                holder.bind(reservationList[position])
            }
            is ItemConfirmHolder -> {
                holder.bind(reservationList[position])
            }
            is ItemRentalHolder -> {
                holder.bind(reservationList[position])
            }
            is ItemBaseHolder -> {
                holder.bind(reservationList[position])
            }
        }
    }

    override fun getItemCount(): Int = reservationList.size

    override fun getItemViewType(position: Int): Int {
        reservationList.let {
            return it[position].state
        }
    }

    fun setList(reservationList: List<ReserveItem>) {
        this.reservationList.addAll(reservationList)
        notifyDataSetChanged()
    }
}

class ItemWaitHolder(val binding: RvItemType01Binding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ReserveItem) {
        binding.apply {
            reserveItem = item
        }

        val url = "${API.BASE_URL}/image/${item.imageid}"

        Glide.with(itemView)
            .load(url)
            .centerCrop()
            .into(binding.imageView)

        itemView.setOnClickListener {
//            val intent = Intent(itemView.context, ShowPostActivity::class.java)
//            intent.putExtra("post_id", post.postid)
//            ContextCompat.startActivity(itemView.context, intent, null)
        }
    }
}

class ItemConfirmHolder(val binding: RvItemType02Binding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ReserveItem) {
        binding.apply {
            reserveItem = item
        }
    }
}

class ItemRentalHolder(val binding: RvItemType03Binding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ReserveItem) {
        binding.apply {
            reserveItem = item
        }
    }
}

class ItemCancelHolder(val binding: RvItemType00Binding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ReserveItem) {
        binding.apply {
            reserveItem = item
        }
    }
}

class ItemBaseHolder(val binding: RvItemTypeBaseBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ReserveItem) {
        binding.apply {
            reserveItem = item
        }
    }
}