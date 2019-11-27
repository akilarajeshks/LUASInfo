package com.zestworks.luasinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class LUASInfoFragment : Fragment() {

    private val luasInfoViewModel: LUASInfoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_luas_info, container, false)
    }

    override fun onStart() {
        super.onStart()

        luasInfoViewModel.currentLuasInfo.observe(this, Observer {
            when (it) {
                is LUASInfoViewModel.State.Success -> TODO()
                is LUASInfoViewModel.State.Error -> TODO()
                LUASInfoViewModel.State.Loading -> TODO()
            }
        })

        luasInfoViewModel.onUILoad(Calendar.getInstance())
    }
}
