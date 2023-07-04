package com.ooommm.clontelegramm3.ui.screens.main_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.models.CommonModel
import com.ooommm.clontelegramm3.utilits.downloadAndSetImage

class MainListAdapter : RecyclerView.Adapter<MainListAdapter.MainListHolder>() {
    val listItem = mutableListOf<CommonModel>()

    //////////////////////////////////////////////////////////////////////////////////////////////
    class MainListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.main_list_item_name)
        val itemLastMessage: TextView = view.findViewById(R.id.main_list_list_message)
        val itemPhoto: ShapeableImageView = view.findViewById(R.id.main_list_item_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.main_list_item, parent, false)
        return MainListHolder(view)
    }

    override fun onBindViewHolder(holder: MainListHolder, position: Int) {
        holder.itemPhoto.downloadAndSetImage(listItem[position].photoUrl)
        holder.itemLastMessage.text = listItem[position].lastMessage
        holder.itemName.text = listItem[position].fullname.replace("|", " ")
    }

    override fun getItemCount() = listItem.size

    fun updateListItem(item: CommonModel) {
        listItem.add(item)
        notifyItemInserted(listItem.size)
    }
}
