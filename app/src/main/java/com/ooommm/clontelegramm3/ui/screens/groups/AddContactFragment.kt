package com.ooommm.clontelegramm3.ui.screens.groups

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.dataBase.*
import com.ooommm.clontelegramm3.models.CommonModel
import com.ooommm.clontelegramm3.ui.screens.base.BaseFragment
import com.ooommm.clontelegramm3.utilits.*

class AddContactFragment : BaseFragment(R.layout.fradment_add_contacts) {

    companion object {
        val listContact = mutableListOf<CommonModel>()
    }

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: AddContactsAdapter

    private val refContactsList = REF_DATABASE_ROOT
        .child(NODE_PHONES_CONTACTS )
        .child(CURRENT_UID)
    private val refUsers = REF_DATABASE_ROOT
        .child(NODE_USERS)
    private val refMessages = REF_DATABASE_ROOT
        .child(NODE_MESSAGES)
        .child(CURRENT_UID)
    private var listItem = listOf<CommonModel>()

    override fun onResume() {
        super.onResume()
        // очистить список  во избежания дублей
        listContact.clear()

        APP_ACTIVITY.title = "Добавить участника"
        hideKeyboard()
        initRecycleView()

        APP_ACTIVITY.findViewById<FloatingActionButton>(R.id.add_contacts_btn_next)
            .setOnClickListener {
                // проверка  выбран ли елемент для добавления в группу
                if (listContact.isEmpty()) showToast("Не выбран ни одит элемент")
                else replaceFragment(CreateGroupFragment(listContact = listContact))
            }

    }

    private fun initRecycleView() {
        recyclerView = APP_ACTIVITY.findViewById(R.id.add_contacts_recycle_view)
        adapter = AddContactsAdapter()

        //1 запрос
        refContactsList.addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot ->
            listItem = dataSnapshot.children.map { it.getCommonModel() }

            listItem.forEach { model ->

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

                                adapter.updateListItem(newModel)
                            })
                    })
            }
        })
        recyclerView.adapter = adapter
    }
}