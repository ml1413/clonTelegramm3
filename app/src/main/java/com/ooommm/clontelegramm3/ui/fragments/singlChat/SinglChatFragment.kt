package com.ooommm.clontelegramm3.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DatabaseReference
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.databinding.FragmentSinglChatBinding
import com.ooommm.clontelegramm3.models.CommonModel
import com.ooommm.clontelegramm3.models.UserModel
import com.ooommm.clontelegramm3.ui.fragments.singlChat.SingleChatAdapter
import com.ooommm.clontelegramm3.utilits.*


class SingleChatFragment(private val contact: CommonModel) :
    BaseFragment(R.layout.fragment_singl_chat) {

    private lateinit var binding: FragmentSinglChatBinding
    private lateinit var listenerInfoToolbar: AppValueEventListener
    private lateinit var receivingUserModel: UserModel
    private lateinit var toolbarInfo: View
    private lateinit var refUser: DatabaseReference
    private lateinit var refMessage: DatabaseReference
    private lateinit var adapter: SingleChatAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageListener: AppChildEventListener
    private var countMessages = 10
    private var isScrolling = false
    private var isSmoothScrollToPosition = true
    private var listListeners = mutableListOf<AppChildEventListener>()
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
        initToolbar()
        initRecycleView()
    }

    private fun initRecycleView() {
        recyclerView = binding.chatRecycleView
        adapter = SingleChatAdapter()
        refMessage = REF_DATABASE_ROOT.child(NODE_MESSAGES)
            .child(CURRENT_UID)
            .child(contact.id)
        recyclerView.adapter = adapter
        messageListener = AppChildEventListener {
            adapter.addItem(item = it.getCommonModel())
            if (isSmoothScrollToPosition) {
                recyclerView.smoothScrollToPosition(adapter.itemCount)
            }
        }
        refMessage.limitToLast(countMessages).addChildEventListener(messageListener)
        listListeners.add(messageListener)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isScrolling && dy < 0) {
                    updateData()
                }
            }
        })
    }

    private fun updateData() {
        isSmoothScrollToPosition = false
        isScrolling = false
        countMessages += 10
        refMessage.limitToLast(countMessages).addChildEventListener(messageListener)
        listListeners.add(messageListener)


    }

    private fun initToolbar() {
        toolbarInfo.visibility = View.VISIBLE


        listenerInfoToolbar = AppValueEventListener {
            receivingUserModel = it.getUserModel()
            initInfoToolbar()
        }
        refUser = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        refUser.addValueEventListener(listenerInfoToolbar)

        binding.chatBtnSendMessage.setOnClickListener {
            isSmoothScrollToPosition = true
            val message = binding.chatInputMessage.text.toString()
            if (message.isEmpty()) {
                showToast("Введите сообщение")
            } else {
                sendMessage(message, contact.id, TYPE_TEXT) {
                    binding.chatInputMessage.setText("")
                }
            }

        }
    }

    private fun initInfoToolbar() {
        toolbarInfo.findViewById<ImageView>(R.id.toolbar_chat_image)
            .downloadAndSetImage(receivingUserModel.photoUrl)

        if (receivingUserModel.fullname.isEmpty()) {
            toolbarInfo.findViewById<TextView>(R.id.tv_toolbar_chat_fullname).text =
                contact.fullname.replace("|", " ")
        } else {
            toolbarInfo.findViewById<TextView>(R.id.tv_toolbar_chat_fullname).text =
                receivingUserModel.fullname.replace("|", " ")
        }

        toolbarInfo.findViewById<TextView>(R.id.tv_toolbar_chat_status).text =
            receivingUserModel.state


    }

    override fun onPause() {
        super.onPause()
        toolbarInfo.visibility = View.GONE

        refUser.removeEventListener(listenerInfoToolbar)

        listListeners.forEach {
            refMessage.removeEventListener(it)
        }

        refMessage.removeEventListener(messageListener)

    }
}