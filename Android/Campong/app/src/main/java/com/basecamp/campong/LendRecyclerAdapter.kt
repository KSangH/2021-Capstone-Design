package com.basecamp.campong

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basecamp.campong.databinding.RvItemType00Binding
import com.basecamp.campong.databinding.RvItemType01Binding
import com.basecamp.campong.databinding.RvItemType02Binding
import com.basecamp.campong.databinding.RvItemTypeBaseBinding
import com.basecamp.campong.model.ReserveItem
import com.basecamp.campong.utils.API
import com.basecamp.campong.utils.RentalState.RENTAL_STATE_CANCEL
import com.basecamp.campong.utils.RentalState.RENTAL_STATE_CONFIRM
import com.basecamp.campong.utils.RentalState.RENTAL_STATE_RENTAL
import com.basecamp.campong.utils.RentalState.RENTAL_STATE_RETURN
import com.basecamp.campong.utils.RentalState.RENTAL_STATE_WAIT
import com.bumptech.glide.Glide

class LendRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var reservationList: MutableList<ReserveItem> = mutableListOf()
    lateinit var clickListener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            // request state가 신청(1)이면
            RENTAL_STATE_WAIT -> {
                val binding =
                    RvItemType01Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                return Item01Holder(binding)
            }
            // request state가 확정(2)이면
            RENTAL_STATE_CONFIRM -> {
                val binding =
                    RvItemType02Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                return Item02Holder(binding)
            }
            // request state가 대여중(3)이면
            RENTAL_STATE_RENTAL -> {
                val binding =
                    RvItemTypeBaseBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ItemBaseHolder(binding)
            }
            // request state가 반납완료(4)이면
            RENTAL_STATE_RETURN -> {
                val binding =
                    RvItemTypeBaseBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ItemBaseHolder(binding)
            }
            // request state가 취소(5)이면
            RENTAL_STATE_CANCEL -> {
                val binding =
                    RvItemType00Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                return Item05Holder(binding)
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
            is Item01Holder -> {
                holder.bind(reservationList[position])
            }
            is Item02Holder -> {
                holder.bind(reservationList[position])
            }
            is Item05Holder -> {
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

    fun removeAll() {
        this.reservationList.clear()
        notifyDataSetChanged()
    }

    // 승인 대기
    inner class Item01Holder(val binding: RvItemType01Binding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: ReserveItem) {
            binding.apply {
                reserveItem = item
            }

            val url = "${API.BASE_URL}/image/${item.imageid}"

            Glide.with(itemView)
                .load(url)
                .centerCrop()
                .into(binding.imageView)

        }

        override fun onClick(v: View?) {
            if (v != null) {
                clickListener.onBaseItemClicked(v, reservationList[adapterPosition])
            }
        }
    }

    inner class Item02Holder(val binding: RvItemType02Binding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ReserveItem) {
            binding.apply {
                reserveItem = item
            }

            val url = "${API.BASE_URL}/image/${item.imageid}"

            Glide.with(itemView)
                .load(url)
                .centerCrop()
                .into(binding.imageView)
        }
    }

    // 취소
    inner class Item05Holder(val binding: RvItemType00Binding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: ReserveItem) {
            binding.apply {
                reserveItem = item
            }

            val url = "${API.BASE_URL}/image/${item.imageid}"

            Glide.with(itemView)
                .load(url)
                .centerCrop()
                .into(binding.imageView)
        }

        override fun onClick(v: View?) {
            if (v != null) {
                clickListener.onBaseItemClicked(v, reservationList[adapterPosition])
            }
        }
    }

    // 기본 (메인과 동일한 레이아웃)
    inner class ItemBaseHolder(val binding: RvItemTypeBaseBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: ReserveItem) {
            binding.apply {
                reserveItem = item
            }

            val url = "${API.BASE_URL}/image/${item.imageid}"

            Glide.with(itemView)
                .load(url)
                .centerCrop()
                .into(binding.imageView)
        }

        override fun onClick(v: View?) {
            if (v != null) {
                clickListener.onBaseItemClicked(v, reservationList[adapterPosition])
            }
        }
    }

    // 클릭리스너
    interface ClickListener {
        fun onBaseItemClicked(view: View, reserveItem: ReserveItem)
//        fun onRentalQRClicked(view: View, reserveItem: ReserveItem)
//        fun onReturnQRClicked(view: View, reserveItem: ReserveItem)
    }

    fun setOnItemClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }
}