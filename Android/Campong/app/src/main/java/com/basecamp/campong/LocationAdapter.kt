package com.basecamp.campong

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.basecamp.campong.databinding.RvItemLocationBinding
import com.basecamp.campong.model.Location

class LocationAdapter(private val locationList: ArrayList<Location>) :
    RecyclerView.Adapter<LocationAdapter.VH>(), Filterable {
    private val mArrayList: MutableList<Location> = locationList
    private var locationListFull = ArrayList(locationList)
    lateinit var clickListener: ClickListener

    inner class VH(private val binding: RvItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(location: Location) {
            binding.location = location.location
        }

        override fun onClick(v: View?) {
            if (v != null) {
                clickListener.onItemClicked(v, locationList[adapterPosition].location)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.rv_item_location,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(locationList[position])
    }

    override fun getItemCount(): Int = locationList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequnce: CharSequence): FilterResults {
                val charString = charSequnce.toString()
                val mFilteredList = ArrayList<Location>()
                if (charString.isEmpty()) {
                    mFilteredList.addAll(locationListFull)
                } else {
                    locationListFull.forEach { item ->
                        if (item.location.contains(charSequnce)) {
                            mFilteredList.add(item)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = mFilteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, filterResults: FilterResults) {
                mArrayList.clear()
                mArrayList.addAll(filterResults.values as Collection<Location>)
                notifyDataSetChanged()
            }

        }
    }

    interface ClickListener {
        fun onItemClicked(view: View, location: String)
    }

    fun setOnItemClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }
}