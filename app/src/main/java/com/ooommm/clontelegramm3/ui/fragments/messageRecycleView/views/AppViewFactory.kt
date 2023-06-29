package com.ooommm.clontelegramm3.ui.fragments.messageRecycleView.views

import com.ooommm.clontelegramm3.models.CommonModel
import com.ooommm.clontelegramm3.utilits.TYPE_MESSAGE_IMAGE
import com.ooommm.clontelegramm3.utilits.TYPE_MESSAGE_VOICE

class AppViewFactory {
    companion object {
        fun getView(message: CommonModel): MessageView {
            return when (message.type) {
                TYPE_MESSAGE_IMAGE -> ViewImageMessage(
                    id = message.id,
                    from = message.from,
                    timeStamp = message.timeStamp.toString(),
                    fileUrl = message.fileUrl
                )
                TYPE_MESSAGE_VOICE -> ViewVoiceMessage(
                    id = message.id,
                    from = message.from,
                    timeStamp = message.timeStamp.toString(),
                    fileUrl = message.fileUrl,
                    text = message.text
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