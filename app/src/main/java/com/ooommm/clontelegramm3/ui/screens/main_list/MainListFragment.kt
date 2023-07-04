package com.ooommm.clontelegramm3.ui.screens.main_list

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.dataBase.*
import com.ooommm.clontelegramm3.models.CommonModel
import com.ooommm.clontelegramm3.utilits.APP_ACTIVITY
import com.ooommm.clontelegramm3.utilits.AppValueEventListener
import com.ooommm.clontelegramm3.utilits.hideKeyboard

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
            //2 запрос
            listItem.forEach { model ->
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

                                adapter.updateListItem(newModel)
                            })
                    })
            }
        })
        recyclerView.adapter = adapter
    }
}