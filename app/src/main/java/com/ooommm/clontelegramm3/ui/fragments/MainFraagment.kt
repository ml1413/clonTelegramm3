package com.ooommm.clontelegramm3.ui.fragments

import androidx.fragment.app.Fragment
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.utilits.APP_ACTIVITY
import com.ooommm.clontelegramm3.utilits.hideKeyboard

class MainFraagment : Fragment(R.layout.fragment_chats) {


    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Telegramm "
        APP_ACTIVITY.appDrawer.enableDrawer()
        hideKeyboard()
    }


}