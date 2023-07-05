package com.ooommm.clontelegramm3.ui.screens.base

import androidx.fragment.app.Fragment
import com.ooommm.clontelegramm3.utilits.APP_ACTIVITY


open class BaseFragment(layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.appDrawer.disableDrawer()
    }



}