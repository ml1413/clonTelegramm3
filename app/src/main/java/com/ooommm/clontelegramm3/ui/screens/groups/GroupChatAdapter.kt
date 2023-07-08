package com.ooommm.clontelegramm3.ui.screens.groups

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ooommm.clontelegramm3.ui.messageRecycleView.viewHolder.*
import com.ooommm.clontelegramm3.ui.messageRecycleView.views.MessageView

class GroupChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listMessageCache = mutableListOf<MessageView>()
    private var listHolders = mutableListOf<MessageHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AppHolderFactory.getHolder(parent = parent, viewType = viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MessageHolder).drawHolder(listMessageCache[position])
    }

    override fun getItemViewType(position: Int): Int {
        return listMessageCache[position].getTypeView()
    }

    override fun getItemCount() = listMessageCache.size

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        (holder as MessageHolder).onAttach(listMessageCache[holder.bindingAdapterPosition])
        listHolders.add((holder as MessageHolder))
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        (holder as MessageHolder).onDetach()
        listHolders.remove((holder as MessageHolder))
        super.onViewDetachedFromWindow(holder)
    }

    fun addItemToBottom(item: MessageView, onSuccess: () -> Unit) {
        if (!listMessageCache.contains(item)) {
            listMessageCache.add(item)
            notifyItemInserted(listMessageCache.size)
        }
        onSuccess()
    }

    fun addItemToTop(item: MessageView, onSuccess: () -> Unit) {
        if (!listMessageCache.contains(item)) {
            listMessageCache.add(item)
            listMessageCache.sortBy { it.timeStamp.toString() }
            notifyItemInserted(0)
        }
        onSuccess()
    }

    fun destroy() {
        listHolders.forEach { it.onDetach() }
    }

}




