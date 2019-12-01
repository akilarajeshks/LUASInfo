package com.zestworks.luasinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.RecyclerView

class LuasInfoAdapter(private var listOfTrams: List<Tram>) :
    RecyclerView.Adapter<LuasInfoAdapter.LuasInfoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LuasInfoViewHolder {
        return LuasInfoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.tram_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfTrams.size
    }

    override fun onBindViewHolder(holder: LuasInfoViewHolder, position: Int) {
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

    inner class LuasInfoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val destination: TextView = view.findViewById(R.id.tram_dest)
        val eta: TextView = view.findViewById(R.id.tram_eta)
    }

}