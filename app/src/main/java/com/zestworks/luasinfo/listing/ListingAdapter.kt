package com.zestworks.luasinfo.listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.RecyclerView
import com.zestworks.luasinfo.R

class ListingAdapter(private var listOfTrams: List<Tram>) :
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
        holder.destination.text = listOfTrams[position].destination
        if (listOfTrams[position].dueMins!!.isDigitsOnly()) {
            holder.eta.text = listOfTrams[position].dueMins.plus(" min")
        } else {
            holder.eta.text = listOfTrams[position].dueMins
        }
    }

    fun setTramList(tramList: List<Tram>) {
        listOfTrams = tramList
        notifyDataSetChanged()
    }

    inner class ListingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val destination: TextView = view.findViewById(R.id.tram_dest)
        val eta: TextView = view.findViewById(R.id.tram_eta)
    }
}