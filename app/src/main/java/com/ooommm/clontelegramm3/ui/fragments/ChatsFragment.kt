package com.ooommm.clontelegramm3.ui.fragments

import androidx.fragment.app.Fragment
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.utilits.APP_ACTIVITY

class ChatsFragment : Fragment(R.layout.fragment_chats) {


    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Чаты"
    }


}