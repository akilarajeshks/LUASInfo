package com.zestworks.luasinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_luas_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class LUASInfoFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val luasInfoViewModel: LUASInfoViewModel by viewModel()

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
    }

    override fun onStart() {
        super.onStart()

        luasInfoViewModel.currentLuasInfo.observe(this, Observer {
            when (it) {
                is LUASInfoViewModel.State.Success -> {
                    it.stopInfo.direction.forEach { direction ->
                        if (direction.name == "Inbound") {
                            textview.text = it.stopInfo.toString()
                        }
                    }
                }
                is LUASInfoViewModel.State.Error -> {
                    textview.text = it.reason
                }
                LUASInfoViewModel.State.Loading -> {

                }
            }
        })
        loadData()
    }

    override fun onRefresh() {
        loadData()
        swipe_refresh_layout.isRefreshing = false
    }

    private fun loadData() {
        luasInfoViewModel.onUILoad(Calendar.getInstance())
    }
}
