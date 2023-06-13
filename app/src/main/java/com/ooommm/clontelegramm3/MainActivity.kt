package com.ooommm.clontelegramm3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.ooommm.clontelegramm3.activities.RegisterActivity
import com.ooommm.clontelegramm3.databinding.ActivityMainBinding
import com.ooommm.clontelegramm3.objects.AppDrawer
import com.ooommm.clontelegramm3.ui.fragments.ChatsFragment
import com.ooommm.clontelegramm3.utilits.AUTH
import com.ooommm.clontelegramm3.utilits.replaceActivity
import com.ooommm.clontelegramm3.utilits.replaceFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar
    private lateinit var appDrawer: AppDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
    }

    override fun onStart() {
        super.onStart()
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
        appDrawer = AppDrawer(activity = this, toolbar = toolbar)
        //init firebase 1
        AUTH = FirebaseAuth.getInstance()
    }

}