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
import com.zestworks.luasinfo.common.ViewState
import kotlinx.android.synthetic.main.fragment_luas_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class ListingFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val listingViewModel: ListingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_luas_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_refresh_layout.setOnRefreshListener(this)
        listingViewModel.currentLuasInfo.observe(this, Observer { it ->
            when (it) {
                is ViewState.Content -> {
                    list_group.visibility = View.VISIBLE
                    text_error.visibility = View.GONE
                    loader.visibility = View.GONE
                    swipe_refresh_layout.isRefreshing = false

                    tram_search_time.text = it.listingViewData.time
                    tram_stop_text_view.text = it.listingViewData.stopName


                    if (recycler_tram.adapter == null) {
                        recycler_tram.apply {
                            adapter =
                                ListingAdapter(
                                    it.listingViewData.trams
                                )
                            layoutManager = LinearLayoutManager(context)
                        }
                    } else {
                        (recycler_tram.adapter as ListingAdapter).apply {
                            setTramList(it.listingViewData.trams)
                            notifyDataSetChanged()
                        }
                    }
                }
                is ViewState.Error -> {
                    text_error.visibility = View.VISIBLE
                    list_group.visibility = View.GONE
                    loader.visibility = View.GONE
                    swipe_refresh_layout.isRefreshing = false
                }
                ViewState.Loading -> {
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