package com.ooommm.clontelegramm3.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.ooommm.clontelegramm3.MainActivity
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.activities.RegisterActivity
import com.ooommm.clontelegramm3.utilits.AUTH
import com.ooommm.clontelegramm3.utilits.replaceActivity

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()// exit profile
                (activity as MainActivity).replaceActivity(RegisterActivity())
            }
        }
        return true
    }
}