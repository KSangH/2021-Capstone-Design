package com.basecamp.campong

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basecamp.campong.RentalState.STATE_CANCEL
import com.basecamp.campong.RentalState.STATE_CONFIRM
import com.basecamp.campong.RentalState.STATE_RENTAL
import com.basecamp.campong.RentalState.STATE_RETURN
import com.basecamp.campong.RentalState.STATE_WAIT
import com.basecamp.campong.databinding.*
import com.basecamp.campong.model.ReserveItem

object RentalState {
    const val STATE_WAIT = 1
    const val STATE_CONFIRM = 2
    const val STATE_RENTAL = 3
    const val STATE_RETURN = 4
    const val STATE_CANCEL = 0
}

class RentalRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var reservationList: MutableList<ReserveItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            STATE_WAIT -> {
                val binding =
                    RvItemType01Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ItemWaitHolder(binding)
            }
            STATE_CONFIRM -> {
                val binding =
                    RvItemType02Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ItemConfirmHolder(binding)
            }
            STATE_RENTAL -> {
                val binding =
                    RvItemType03Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ItemRentalHolder(binding)
            }
            STATE_RETURN -> {
                val binding =
                    RvItemTypeBaseBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ItemBaseHolder(binding)
            }
            STATE_CANCEL -> {
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

//        val url = "${API.BASE_URL}/image/${item.imageid}"
//
//        Glide.with(itemView)
//            .load(url)
//            .centerCrop()
//            .into(binding.imageView)

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