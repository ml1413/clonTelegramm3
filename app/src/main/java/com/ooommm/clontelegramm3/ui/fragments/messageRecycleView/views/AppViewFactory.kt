package com.ooommm.clontelegramm3.ui.fragments.messageRecycleView.views

import com.ooommm.clontelegramm3.models.CommonModel
import com.ooommm.clontelegramm3.utilits.TYPE_MESSAGE_IMAGE

class AppViewFactory {
    companion object {
        fun getVIew(message: CommonModel): MessageView {
            return when (message.type) {
                TYPE_MESSAGE_IMAGE -> ViewImageMessage(
                    id = message.id,
                    from = message.from,
                    timeStamp = message.timeStamp.toString(),
                    fileUrl = message.fileUrl
                )
                else -> ViewTextMessage(
                    id = message.id,
                    from = message.from,
                    timeStamp = message.timeStamp.toString(),
                    fileUrl = message.fileUrl,
                    text = message.text
                )
            }
        }
    }
}