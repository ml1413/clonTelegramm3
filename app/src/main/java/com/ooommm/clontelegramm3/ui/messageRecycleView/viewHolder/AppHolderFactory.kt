package com.ooommm.clontelegramm3.ui.messageRecycleView.viewHolder

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.ui.messageRecycleView.views.MessageView
import com.ooommm.clontelegramm3.ui.messageRecycleView.views.ViewVoiceMessage

class AppHolderFactory {
    companion object {
        fun getHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                MessageView.MESSAGE_IMAGE -> {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.mesage_item_image, parent, false)
                    HolderImageMessage(view)
                }
                MessageView.MESSAGE_VOICE -> {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.mesage_item_voice, parent, false)
                    HolderVoiceMessage(view)
                }
                else -> {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.mesage_item_text, parent, false)
                    HolderTextMessage(view)
                }
            }
        }
    }
}