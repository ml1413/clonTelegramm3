package com.ooommm.clontelegramm3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.ooommm.clontelegramm3.activities.RegisterActivity
import com.ooommm.clontelegramm3.databinding.ActivityMainBinding
import com.ooommm.clontelegramm3.objects.AppDrawer
import com.ooommm.clontelegramm3.ui.fragments.ChatsFragment
import com.ooommm.clontelegramm3.utilits.*
import com.theartofdev.edmodo.cropper.CropImage

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar
    lateinit var appDrawer: AppDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
    }

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY = this
        initFields()
        initFun()
    }

    private fun initFun() {
        if (AUTH.currentUser != null) {//firebase check user Authentication

            setSupportActionBar(toolbar)// init toolbar 2

            replaceFragment(ChatsFragment(), false)//setFragment on Container 1

            appDrawer.create() // create header & drawer2
        } else {

            replaceActivity(activity = RegisterActivity())//open RegisterActivity
        }
    }


    private fun initFields() {
        // init toolbar 1
        toolbar = binding.mainToolbar
        // create header & drawer1
        appDrawer = AppDrawer(mainActivity = this, toolbar = toolbar)
        //init firebase 1
        initFirebase()
        //Заполнение модэли
        initUser()
    }

    private fun initUser() {
        REF_DATABASE_ROOT
            .child(NODE_USERS)
            .child(CURRENT_UID)
            .addListenerForSingleValueEvent(AppValueEventListener {
                Log.d("TAG1", "initUser: $USER")
                USER = it.getValue(User::class.java) ?: User()
                Log.d("TAG1", "initUser: ${it.getValue(User::class.java)}")
            })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (
            requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == RESULT_OK
            && data != null
        ) {
            val uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE_ROOT
                .child(FOLDER_PROFILE_IMAGE)
                .child(CURRENT_UID)

            path.putFile(uri).addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.toast_data_update))
                } else {
                    showToast(it.exception?.message.toString())
                    Log.d("TAG1", it.exception?.message.toString())
                }
            }
        }
    }

    fun hideKeyboard() {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }


}