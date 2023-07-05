package com.ooommm.clontelegramm3.ui.screens.groups

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.dataBase.*
import com.ooommm.clontelegramm3.models.CommonModel
import com.ooommm.clontelegramm3.utilits.APP_ACTIVITY
import com.ooommm.clontelegramm3.utilits.AppValueEventListener
import com.ooommm.clontelegramm3.utilits.hideKeyboard

class AddContactFragment : Fragment(R.layout.fradment_add_contacts) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AddContactsAdapter

    companion object {
        val listContact = mutableListOf<CommonModel>()
    }

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
        APP_ACTIVITY.title = "Добавить участника"
        APP_ACTIVITY.appDrawer.enableDrawer()
        hideKeyboard()
        initRecycleView()
        APP_ACTIVITY.findViewById<FloatingActionButton>(R.id.add_contacts_btn_next)
            .setOnClickListener {
                listContact.forEach {
                    println(it.id)
                }
            }
    }

    private fun initRecycleView() {
        recyclerView = APP_ACTIVITY.findViewById(R.id.add_contacts_recycle_view)
        adapter = AddContactsAdapter()

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