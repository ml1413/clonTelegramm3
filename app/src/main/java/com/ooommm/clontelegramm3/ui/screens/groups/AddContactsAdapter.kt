package com.ooommm.clontelegramm3.ui.screens.groups

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.models.CommonModel
import com.ooommm.clontelegramm3.ui.screens.SingleChatFragment
import com.ooommm.clontelegramm3.utilits.downloadAndSetImage
import com.ooommm.clontelegramm3.utilits.replaceFragment
import com.ooommm.clontelegramm3.utilits.showToast

class AddContactsAdapter : RecyclerView.Adapter<AddContactsAdapter.AddContactsHolder>() {
    private val listItem = mutableListOf<CommonModel>()


    //////////////////////////////////////////////////////////////////////////////////////////////
    class AddContactsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.add_contacts_name)
        val itemLastMessage: TextView = view.findViewById(R.id.add_contacts_last_message)
        val itemPhoto: ShapeableImageView = view.findViewById(R.id.add_contacts_photo)
        val itemChoice: ShapeableImageView = view.findViewById(R.id.add_contact_item_choice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddContactsHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.add_contacts_item, parent, false)

        val holder = AddContactsHolder(view)
        // устанавливаем слушатель (нажатие на item)
        holder.itemView.setOnClickListener {
            // открываем фрагмент SingleChatFragment
            if (listItem[holder.bindingAdapterPosition].choice) {
                holder.itemChoice.isInvisible = true // убрать галочку (выбрано)
                listItem[holder.bindingAdapterPosition].choice = false
                AddContactFragment.listContact.remove(listItem[holder.bindingAdapterPosition])
            } else {
                holder.itemChoice.isInvisible = false // поставить галочку (выбрано)
                listItem[holder.bindingAdapterPosition].choice = true
                AddContactFragment.listContact.add(listItem[holder.bindingAdapterPosition])
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: AddContactsHolder, position: Int) {
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
