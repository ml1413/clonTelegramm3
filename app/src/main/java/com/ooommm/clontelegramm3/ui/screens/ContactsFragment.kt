package com.ooommm.clontelegramm3.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.database.DatabaseReference
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.dataBase.*
import com.ooommm.clontelegramm3.databinding.FragmentContactsBinding
import com.ooommm.clontelegramm3.models.CommonModel
import com.ooommm.clontelegramm3.utilits.*


class ContactsFragment : BaseFragment(R.layout.fragment_contacts) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FirebaseRecyclerAdapter<CommonModel, ContactHolder>
    private lateinit var refContact: DatabaseReference
    private lateinit var refUsers: DatabaseReference
    private lateinit var refUserListener: AppValueEventListener
    private var mapListeners = hashMapOf<DatabaseReference, AppValueEventListener>()
    private lateinit var binding: FragmentContactsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Контакты"
        initRecycleView()
    }

    private fun initRecycleView() {
        recyclerView = binding.contactRecycleView
        refContact = REF_DATABASE_ROOT
            .child(NODE_PHONES_CONTACTS)
            .child(CURRENT_UID)

        val options = FirebaseRecyclerOptions.Builder<CommonModel>()
            .setQuery(refContact, CommonModel::class.java)
            .build()

        adapter = object : FirebaseRecyclerAdapter<CommonModel, ContactHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.contact_item, parent, false)

                return ContactHolder(view)
            }

            override fun onBindViewHolder(
                holder: ContactHolder,
                position: Int,
                model: CommonModel
            ) {
                refUsers = REF_DATABASE_ROOT.child(NODE_USERS).child(model.id)
                refUserListener = AppValueEventListener {
                    val contact = it.getCommonModel()

                    if (contact.fullname.isEmpty()) {
                        holder.name.text = model.fullname.replace("|", " ")
                    } else {
                        holder.name.text = contact.fullname.replace("|", " ")
                    }

                    holder.status.text = contact.state
                    holder.photo.downloadAndSetImage(contact.photoUrl)
                    holder.itemView.setOnClickListener {
                        replaceFragment(SingleChatFragment(contact = model))
                    }
                }

                refUsers.addValueEventListener(refUserListener)
                mapListeners.put(refUsers, refUserListener)

            }

        }
        recyclerView.adapter = adapter
        adapter.startListening()
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    class ContactHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tv_contact_fullname)
        val status: TextView = view.findViewById(R.id.tv_status)
        val photo: ShapeableImageView = view.findViewById(R.id.iv_contact_photo)
    }

    override fun onPause() {
        super.onPause()
        adapter.stopListening()
        println()
        mapListeners.forEach {
            it.key.removeEventListener(it.value)
        }
        println()
    }
}


