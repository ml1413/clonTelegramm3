package com.ooommm.clontelegramm3.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.DatabaseReference
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.dataBase.*
import com.ooommm.clontelegramm3.databinding.FragmentSinglChatBinding
import com.ooommm.clontelegramm3.models.CommonModel
import com.ooommm.clontelegramm3.models.UserModel
import com.ooommm.clontelegramm3.ui.messageRecycleView.views.AppViewFactory
import com.ooommm.clontelegramm3.ui.screens.singlChat.SingleChatAdapter
import com.ooommm.clontelegramm3.utilits.*
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var appVoiceRecorder: AppVoiceRecorder
    private var countMessages = 10
    private var isScrolling = false
    private var isSmoothScrollToPosition = true
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
        initFields()
        initToolbar()
        initRecycleView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initFields() {
        appVoiceRecorder = AppVoiceRecorder()
        swipeRefreshLayout = binding.chatSwipeRefresh
        layoutManager = LinearLayoutManager(this.context)
        //показать скрыть скрепку отправка файла
        binding.chatInputMessage.addTextChangedListener(AppTextWatcher {
            val string = binding.chatInputMessage.text.toString()
            if (string.isEmpty() || string == "запись") {
                binding.chatBtnSendMessage.isVisible = false
                binding.chatBtnVoice.isVisible = true
                binding.chatBtnAttach.isVisible = true
            } else {
                binding.chatBtnSendMessage.isVisible = true
                binding.chatBtnAttach.isVisible = false
                binding.chatBtnVoice.isVisible = false
            }
        })

        binding.chatBtnAttach.setOnClickListener { attachFile() }

        CoroutineScope(Dispatchers.IO).launch {
            binding.chatBtnVoice.setOnTouchListener { v, event ->

                if (checkPermission(RECORD_AUDIO)) {
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        //TODO record
                        binding.chatInputMessage.setText("запись")//текст в поле ввода
                        //установка цвета иконуи во время записи
                        binding.chatBtnVoice
                            .setColorFilter(ContextCompat.getColor(APP_ACTIVITY, R.color.red))

                        val messageKey = getMessageKey(contact.id)
                        appVoiceRecorder.startRecord(messageKey)

                    } else if (event.action == MotionEvent.ACTION_UP) {
                        //TODO stop record
                        binding.chatInputMessage.setText("") //текст в поле ввода
                        //установка цвета во время окончания записи
                        binding.chatBtnVoice.colorFilter = null

                        appVoiceRecorder.stopRecord() { file, messageKey ->
                            uploadFileToStorage(
                                Uri.fromFile(file),
                                messageKey,
                                contact.id,
                                TYPE_MESSAGE_VOICE
                            )
                            isSmoothScrollToPosition = true
                        }

                    }
                }
                true
            }
        }
    }


    private fun attachFile() {
        CropImage.activity()
            .setAspectRatio(1, 1)//соотношение сторон
            .setRequestedSize(250, 250)// обрежет фото если оно больше чем 600 х 600
            .start(APP_ACTIVITY, this)
    }

    private fun initRecycleView() {
        recyclerView = binding.chatRecycleView
        adapter = SingleChatAdapter()
        refMessage = REF_DATABASE_ROOT
            .child(NODE_MESSAGES)
            .child(CURRENT_UID)
            .child(contact.id)
        recyclerView.adapter = adapter

        recyclerView.setHasFixedSize(true)

        recyclerView.isNestedScrollingEnabled = false

        recyclerView.layoutManager = layoutManager

        messageListener = AppChildEventListener {
            val message = it.getCommonModel()

            if (isSmoothScrollToPosition) {
                adapter.addItemToBottom(item = AppViewFactory.getView(message = message)) {
                    recyclerView.smoothScrollToPosition(adapter.itemCount)
                }
            } else {
                adapter.addItemToTop(item = AppViewFactory.getView(message = message)) {
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        }
        refMessage.limitToLast(countMessages).addChildEventListener(messageListener)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
//                println(recyclerView.recycledViewPool.getRecycledViewCount(0))
                if (isScrolling && dy < 0 && layoutManager.findFirstVisibleItemPosition() <= 3) {
                    updateData()
                }
            }
        })

        swipeRefreshLayout.setOnRefreshListener { updateData() }
    }

    private fun updateData() {
        isSmoothScrollToPosition = false
        isScrolling = false
        countMessages += 10
        refMessage.removeEventListener(messageListener)
        refMessage.limitToLast(countMessages).addChildEventListener(messageListener)


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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (
            requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == Activity.RESULT_OK
            && data != null
        ) {
            val uri = CropImage.getActivityResult(data).uri

            val messageKey = getMessageKey(contact.id)

            uploadFileToStorage(uri, messageKey, contact.id, TYPE_MESSAGE_IMAGE)
            isSmoothScrollToPosition = true
        }
    }


    override fun onPause() {
        super.onPause()
        toolbarInfo.visibility = View.GONE

        refUser.removeEventListener(listenerInfoToolbar)


        refMessage.removeEventListener(messageListener)

    }

    override fun onDestroy() {
        super.onDestroy()
        // удаляем рекордэр
        appVoiceRecorder.releaseRecord()
        //уничтожить стушатели
        adapter.destroy()
    }
}