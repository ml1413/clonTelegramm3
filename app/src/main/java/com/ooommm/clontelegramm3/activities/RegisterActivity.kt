package com.ooommm.clontelegramm3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.databinding.ActivityRegisterBinding
import com.ooommm.clontelegramm3.ui.fragments.EnterPhoneNumberFragment
import com.ooommm.clontelegramm3.utilits.replaceFragment

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
            .also {
                setContentView(it.root)
            }
    }

    override fun onStart() {
        super.onStart()
        toolbar = binding.registerToolbar//init toolbar
        setSupportActionBar(toolbar)//set toolbar
        title = getString(R.string.register_action_toolbar_tittle) // set tittle toolbar
        //set fragment in container
        replaceFragment(EnterPhoneNumberFragment())
    }
}