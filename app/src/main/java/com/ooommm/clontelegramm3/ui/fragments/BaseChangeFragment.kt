package com.ooommm.clontelegramm3.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.ooommm.clontelegramm3.MainActivity
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.utilits.APP_ACTIVITY


open class BaseChangeFragment(layout: Int) : Fragment(layout) {
    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
        (activity as MainActivity).appDrawer.disableDrawer()
    }

    override fun onStop() {
        super.onStop()
        APP_ACTIVITY.hideKeyboard()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        change()//нажатие на галочку подтвердить изминения
        return true
    }

    open fun change() {
    }


}