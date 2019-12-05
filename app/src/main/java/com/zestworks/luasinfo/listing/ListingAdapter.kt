package com.zestworks.luasinfo.listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zestworks.luasinfo.R

class ListingAdapter(private var listOfTrams: List<TramItem>) :
    RecyclerView.Adapter<ListingAdapter.ListingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder =
        ListingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.tram_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = listOfTrams.size

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        listOfTrams[position].apply {
            holder.destination.text = destination
            if (isDue)
                holder.eta.text = dueMins
            else
                holder.eta.text =
                    holder.itemView.context.resources.getString(R.string.due_mins, dueMins)
        }
    }

    fun setTramList(tramList: List<TramItem>) {
        listOfTrams = tramList
    }

    inner class ListingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val destination: TextView = view.findViewById(R.id.tram_dest)
        val eta: TextView = view.findViewById(R.id.tram_eta)
    }
}