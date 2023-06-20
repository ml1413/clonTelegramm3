package com.ooommm.clontelegramm3.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.databinding.FragmentSinglChatBinding
import com.ooommm.clontelegramm3.models.CommonModel
import com.ooommm.clontelegramm3.models.UserModel
import com.ooommm.clontelegramm3.utilits.*


class SingleChatFragment(private val contact: CommonModel) :
    BaseFragment(R.layout.fragment_singl_chat) {

    private lateinit var binding: FragmentSinglChatBinding
    private lateinit var listenerInfoToolbar: AppValueEventListener
    private lateinit var receivingUserModel: UserModel
    private lateinit var toolbarInfo: View
    private lateinit var refUser: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSinglChatBinding.inflate(inflater, container, false)
        toolbarInfo = APP_ACTIVITY.toolbar.findViewById(R.id.toolbar_info)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        toolbarInfo.visibility = View.VISIBLE


        listenerInfoToolbar = AppValueEventListener {
            receivingUserModel = it.getUserModel()
            initInfoToolbar()
        }
        refUser = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        refUser.addValueEventListener(listenerInfoToolbar)
    }

    private fun initInfoToolbar() {
        toolbarInfo.findViewById<ImageView>(R.id.toolbar_chat_image)
            .downloadAndSetImage(receivingUserModel.photoUrl)

        toolbarInfo.findViewById<TextView>(R.id.tv_toolbar_chat_fullname).text =
            receivingUserModel.fullname.replace("|"," ")

        toolbarInfo.findViewById<TextView>(R.id.tv_toolbar_chat_status).text =
            receivingUserModel.state


    }

    override fun onPause() {
        super.onPause()
        toolbarInfo.visibility = View.GONE

        refUser.removeEventListener(listenerInfoToolbar)

    }
}