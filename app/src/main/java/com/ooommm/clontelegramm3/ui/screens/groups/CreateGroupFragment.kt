package com.ooommm.clontelegramm3.ui.screens.groups

import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.models.CommonModel
import com.ooommm.clontelegramm3.ui.screens.base.BaseFragment
import com.ooommm.clontelegramm3.utilits.*

class CreateGroupFragment(private var listContact: MutableList<CommonModel>) :
    BaseFragment(R.layout.fragment_create_group) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AddContactsAdapter


    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = getString(R.string.create_group)
        hideKeyboard()
        initRecycleView()
        APP_ACTIVITY.findViewById<FloatingActionButton>(R.id.create_group_btn_complete)
            .setOnClickListener {
                showToast("click")
            }
        APP_ACTIVITY.findViewById<EditText>(R.id.create_group_input_name).requestFocus()
        APP_ACTIVITY.findViewById<TextView>(R.id.create_group_counts).text =
            getPlurals(listContact.size)
    }

    private fun initRecycleView() {
        recyclerView = APP_ACTIVITY.findViewById(R.id.create_group_recycler)
        adapter = AddContactsAdapter()
        recyclerView.adapter = adapter
        listContact.forEach { adapter.updateListItem(it) }
    }


}