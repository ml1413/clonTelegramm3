package com.ooommm.clontelegramm3.ui.screens.groups

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.dataBase.CHILD_PHOTO_URL
import com.ooommm.clontelegramm3.dataBase.createGroupToDataBase
import com.ooommm.clontelegramm3.dataBase.getUrlFromStorage
import com.ooommm.clontelegramm3.dataBase.putFileToStorage
import com.ooommm.clontelegramm3.models.CommonModel
import com.ooommm.clontelegramm3.ui.screens.base.BaseFragment
import com.ooommm.clontelegramm3.ui.screens.main_list.MainListFragment
import com.ooommm.clontelegramm3.utilits.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class CreateGroupFragment(private var listContact: MutableList<CommonModel>) :
    BaseFragment(R.layout.fragment_create_group) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AddContactsAdapter
    private var uri = Uri.EMPTY


    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = getString(R.string.create_group)
        hideKeyboard()
        initRecycleView()
        APP_ACTIVITY.findViewById<ShapeableImageView>(R.id.create_group_photo)
            .setOnClickListener { addPhoto() }
        APP_ACTIVITY.findViewById<FloatingActionButton>(R.id.create_group_btn_complete)
            .setOnClickListener {
                // получаем имя группы
                val nameGroup = APP_ACTIVITY
                    .findViewById<EditText>(R.id.create_group_input_name).text.toString()

                if (nameGroup.isEmpty()) {
                    showToast("Введите имя")
                } else {
                    createGroupToDataBase(nameGroup, uri, listContact) {
                        replaceFragment(MainListFragment())
                    }
                }
            }
        APP_ACTIVITY.findViewById<EditText>(R.id.create_group_input_name).requestFocus()
        APP_ACTIVITY.findViewById<TextView>(R.id.create_group_counts).text =
            getPlurals(listContact.size)
    }

    private fun addPhoto() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(250, 250)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(APP_ACTIVITY, this)
    }

    private fun initRecycleView() {
        recyclerView = APP_ACTIVITY.findViewById(R.id.create_group_recycler)
        adapter = AddContactsAdapter()
        recyclerView.adapter = adapter
        listContact.forEach { adapter.updateListItem(it) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (
            requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == Activity.RESULT_OK
            && data != null
        ) {
            uri = CropImage.getActivityResult(data).uri
            APP_ACTIVITY.findViewById<ShapeableImageView>(R.id.create_group_photo)
                .setImageURI(uri)
        }
    }
}