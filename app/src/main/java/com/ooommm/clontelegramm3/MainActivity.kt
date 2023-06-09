package com.ooommm.clontelegramm3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.ooommm.clontelegramm3.databinding.ActivityMainBinding
import com.ooommm.objects.AppDrawer
import com.ooommm.ui.fragments.ChatsFragment

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
        // init toolbar 2
        setSupportActionBar(toolbar)
        //setFragment on Container 1
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, ChatsFragment()).commit()
        // create header & drawer2
        appDrawer.create()
    }


    private fun initFields() {
        // init toolbar 1
        toolbar = binding.mainToolbar
        // create header & drawer1
        appDrawer = AppDrawer(activity = this, toolbar = toolbar)
    }

}