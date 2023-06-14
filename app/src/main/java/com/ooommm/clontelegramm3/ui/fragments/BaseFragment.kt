package com.ooommm.clontelegramm3.ui.fragments

import androidx.fragment.app.Fragment
import com.ooommm.clontelegramm3.MainActivity


open class BaseFragment(layout: Int) : Fragment(layout) {


    override fun onStart() {
        super.onStart()
        (activity as MainActivity).appDrawer.disableDrawer()
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).appDrawer.enableDrawer()
    }

}