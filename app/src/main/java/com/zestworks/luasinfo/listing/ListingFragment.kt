package com.zestworks.luasinfo.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.zestworks.luasinfo.R
import kotlinx.android.synthetic.main.fragment_luas_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class ListingFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val listingViewModel: ListingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_luas_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_refresh_layout.setOnRefreshListener(this)
        listingViewModel.currentLuasInfo.observe(this, Observer { it ->
            when (it) {
                is ListingViewModel.State.Success -> {
                    list_group.visibility = View.VISIBLE
                    text_error.visibility = View.GONE
                    loader.visibility = View.GONE
                    swipe_refresh_layout.isRefreshing = false

                    tram_search_time.text = it.stopInfo.created!!.split("T").last()
                    tram_stop_text_view.text = it.stopInfo.stop

                    if (it.stopInfo.stopAbv != null && it.stopInfo.stopAbv == ListingViewModel.Stops.STI.name || it.stopInfo.stop == "Stillorgan") {

                        if (recycler_tram.adapter == null) {
                            recycler_tram.apply {
                                adapter =
                                    ListingAdapter(
                                        it.stopInfo.direction.filter { it.name == "Inbound" }.flatMap { it.tram })
                                layoutManager = LinearLayoutManager(context)
                            }
                        } else {
                            (recycler_tram.adapter as ListingAdapter).setTramList(it.stopInfo.direction.filter { it.name == "Inbound" }.flatMap { it.tram })
                            (recycler_tram.adapter as ListingAdapter).notifyDataSetChanged()
                        }
                    } else if (it.stopInfo.stopAbv != null && it.stopInfo.stopAbv == ListingViewModel.Stops.MAR.name || it.stopInfo.stop == "Still") {

                        if (recycler_tram.adapter == null) {
                            recycler_tram.apply {
                                adapter =
                                    ListingAdapter(
                                        it.stopInfo.direction.filter { it.name == "Outbound" }.flatMap { it.tram })
                                layoutManager = LinearLayoutManager(context)
                            }
                        } else {
                            (recycler_tram.adapter as ListingAdapter).setTramList(it.stopInfo.direction.filter { it.name == "Outbound" }.flatMap { it.tram })
                            (recycler_tram.adapter as ListingAdapter).notifyDataSetChanged()
                        }
                    }
                }
                is ListingViewModel.State.Error -> {
                    text_error.visibility = View.VISIBLE
                    list_group.visibility = View.GONE
                    loader.visibility = View.GONE
                    swipe_refresh_layout.isRefreshing = false
                }
                ListingViewModel.State.Loading -> {
                    list_group.visibility = View.GONE
                    text_error.visibility = View.GONE
                    loader.visibility = View.VISIBLE
                }
            }
        })
        listingViewModel.onUILoad(Calendar.getInstance())
    }

    override fun onRefresh() {
        listingViewModel.onUILoad(Calendar.getInstance())
    }
}