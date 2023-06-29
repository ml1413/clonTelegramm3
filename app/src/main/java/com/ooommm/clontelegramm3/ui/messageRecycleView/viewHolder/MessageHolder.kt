package com.ooommm.clontelegramm3.ui.messageRecycleView.viewHolder

import com.ooommm.clontelegramm3.ui.messageRecycleView.views.MessageView

interface MessageHolder {
    fun drawHolder(view: MessageView)
    fun onAttach(view: MessageView)
    fun onDettach()
}