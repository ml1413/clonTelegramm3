package com.ooommm.clontelegramm3.ui.screens.main_list

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.dataBase.*
import com.ooommm.clontelegramm3.models.CommonModel
import com.ooommm.clontelegramm3.utilits.*

class MainListFragment : Fragment(R.layout.fragment_main_list) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainListAdapter
    private val refMainList = REF_DATABASE_ROOT
        .child(NODE_MAIN_LIST)
        .child(CURRENT_UID)
    private val refUsers = REF_DATABASE_ROOT
        .child(NODE_USERS)
    private val refMessages = REF_DATABASE_ROOT
        .child(NODE_MESSAGES)
        .child(CURRENT_UID)
    private var listItem = listOf<CommonModel>()

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Telegram"
        APP_ACTIVITY.appDrawer.enableDrawer()
        hideKeyboard()
        initRecycleView()
    }

    private fun initRecycleView() {
        recyclerView = APP_ACTIVITY.findViewById(R.id.main_list_recycle_view)
        adapter = MainListAdapter()

        //1 запрос
        refMainList.addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot ->
            listItem = dataSnapshot.children.map { it.getCommonModel() }

            listItem.forEach { model ->

                when (model.type) {
                    TYPE_CHAT -> showChat(model)
                    TYPE_GROUP -> showGroup(model)
                }

            }
        })
        recyclerView.adapter = adapter
    }

    private fun showGroup(model: CommonModel) {
        //2 запрос
        REF_DATABASE_ROOT.child(NODE_GROUPS).child(model.id)
            .addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot1 ->
                val newModel = dataSnapshot1.getCommonModel()
                //3 запрос
                REF_DATABASE_ROOT.child(NODE_GROUPS)
                    .child(model.id)
                    .child(NODE_MESSAGES).limitToLast(1)//запрос последнего элемента
                    .addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot2 ->
                        val tempList = dataSnapshot2.children.map { it.getCommonModel() }

                        if (tempList.isEmpty()) {
                            newModel.lastMessage = "Чат пуст"
                        } else {
                            newModel.lastMessage = tempList[0].text
                        }
                        newModel.type = TYPE_GROUP
                        adapter.updateListItem(newModel)
                    })
            })
    }

    private fun showChat(model: CommonModel) {
        //2 запрос
        refUsers.child(model.id)
            .addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot1 ->
                val newModel = dataSnapshot1.getCommonModel()
                //3 запрос
                refMessages.child(model.id).limitToLast(1)//запрос последнего элемента
                    .addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot2 ->
                        val tempList = dataSnapshot2.children.map { it.getCommonModel() }

                        if (tempList.isEmpty()) {
                            newModel.lastMessage = "Чат пуст"
                        } else {
                            newModel.lastMessage = tempList[0].text
                        }

                        // если поле имени пустое устанавливаем номер вместо имени
                        if (newModel.fullname.isEmpty()) {
                            newModel.fullname = newModel.phone
                        }
                        newModel.type = TYPE_CHAT
                        adapter.updateListItem(newModel)
                    })
            })
    }
}