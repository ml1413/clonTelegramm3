package com.ooommm.clontelegramm3

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.ooommm.clontelegramm3.dataBase.AUTH
import com.ooommm.clontelegramm3.dataBase.initFirebase
import com.ooommm.clontelegramm3.dataBase.initUser
import com.ooommm.clontelegramm3.databinding.ActivityMainBinding
import com.ooommm.clontelegramm3.objects.AppDrawer
import com.ooommm.clontelegramm3.ui.fragments.MainFraagment
import com.ooommm.clontelegramm3.ui.fragments.register.EnterPhoneNumberFragment
import com.ooommm.clontelegramm3.utilits.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var toolbar: Toolbar
    lateinit var appDrawer: AppDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        APP_ACTIVITY = this
        //init firebase 1
        initFirebase()
        //Заполнение модэли
        initUser {
            CoroutineScope(Dispatchers.IO).launch {
                initContacts()
            }
            initFields()
            initFun()
        }
    }


    private fun initFun() {
        setSupportActionBar(toolbar)// init toolbar 2

        if (AUTH.currentUser != null) {//firebase check user Authentication

            replaceFragment(MainFraagment(), false)//setFragment on Container 1

            appDrawer.create() // create header & drawer2
        } else {
            replaceFragment(EnterPhoneNumberFragment(), false)
        }
    }


    private fun initFields() {
        // init toolbar 1
        toolbar = binding.mainToolbar
        // create header & drawer1
        appDrawer = AppDrawer()

    }

    override fun onStart() {
        super.onStart()
        AppStates.updateState(AppStates.ONLINE)
    }

    override fun onStop() {
        super.onStop()
        AppStates.updateState(AppStates.OFFLINE)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (
            ContextCompat.checkSelfPermission(APP_ACTIVITY, READ_CONTACTS)
            ==
            PackageManager.PERMISSION_GRANTED
        ) {
            initContacts()
        }
    }

}