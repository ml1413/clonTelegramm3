package com.ooommm.clontelegramm3.objects

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.ui.fragments.SettingsFragment
import com.ooommm.clontelegramm3.utilits.replaceFragment

class AppDrawer(val mainActivity: AppCompatActivity, val toolbar: Toolbar) {
    private lateinit var drawer: Drawer
    private lateinit var header: AccountHeader
    private lateinit var drawerLayout: DrawerLayout

    fun create() {
        //init  header1
        createHeader()
        //init drawer1
        createDrawer()
        //init drawerLayout1
        drawerLayout = drawer.drawerLayout
    }

    //отключить меню три полоски Toggle и ставим в место нее стрелку назад
    fun disableDrawer() {
        drawer.actionBarDrawerToggle
            ?.isDrawerIndicatorEnabled = false // off toggle in drawer toolbar

        mainActivity.supportActionBar
            ?.setDisplayHomeAsUpEnabled(true)// on <- back button in toolbar

        // hold drawer menu close
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        //возврат назад по стэку
        toolbar.setNavigationOnClickListener {
            mainActivity.supportFragmentManager.popBackStack()
        }
    }

    //отключить стрелку назад и включить меню три полоски
    fun enableDrawer() {
        mainActivity.supportActionBar
            ?.setDisplayHomeAsUpEnabled(false)// off <- back button in toolbar

        drawer.actionBarDrawerToggle
            ?.isDrawerIndicatorEnabled = true // on toggle in drawer toolbar

        // drawer menu close (OFF)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        //open drawer
        toolbar.setNavigationOnClickListener {
            drawer.openDrawer()
        }
    }

    private fun createDrawer() {
        drawer = DrawerBuilder()
            .withActivity(mainActivity)
            .withToolbar(toolbar)
            .withActionBarDrawerToggle(true)
            .withSelectedItem(-1)
            .withAccountHeader(header)
            .addDrawerItems(
                //-------------------------------------------------------------------------------
                PrimaryDrawerItem()
                    .withIdentifier(100)
                    .withIconTintingEnabled(true)
                    .withName("Создать группу")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_baseline_group_24),


                //-------------------------------------------------------------------------------
                PrimaryDrawerItem()
                    .withIdentifier(101)
                    .withIconTintingEnabled(true)
                    .withName("Создать секретный чат")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_baseline_lock_24),


                //-------------------------------------------------------------------------------
                PrimaryDrawerItem()
                    .withIdentifier(102)
                    .withIconTintingEnabled(true)
                    .withName("Создать группу")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_baseline_group_24),


                //-------------------------------------------------------------------------------
                PrimaryDrawerItem()
                    .withIdentifier(103)
                    .withIconTintingEnabled(true)
                    .withName("Создать канал")
                    .withSelectable(false)
                    .withIcon(R.drawable.campaing),


                //-------------------------------------------------------------------------------
                PrimaryDrawerItem()
                    .withIdentifier(104)
                    .withIconTintingEnabled(true)
                    .withName("Контакты")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_baseline_person_24),


                //-------------------------------------------------------------------------------
                PrimaryDrawerItem()
                    .withIdentifier(105)
                    .withIconTintingEnabled(true)
                    .withName("Звонки")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_baseline_phone_24),


                //-------------------------------------------------------------------------------
                PrimaryDrawerItem()
                    .withIdentifier(106)
                    .withIconTintingEnabled(true)
                    .withName("Избранное")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_baseline_bookmark_24),


                //-------------------------------------------------------------------------------
                PrimaryDrawerItem()
                    .withIdentifier(107)
                    .withIconTintingEnabled(true)
                    .withName("Настройки")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_baseline_settings_24),


                //-------------------------------------------------------------------------------
                //Разделитель
                DividerDrawerItem(),

                //-------------------------------------------------------------------------------
                PrimaryDrawerItem()
                    .withIdentifier(108)
                    .withIconTintingEnabled(true)
                    .withName("Пригласить друзей")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_baseline_group_add_24),


                //-------------------------------------------------------------------------------
                PrimaryDrawerItem()
                    .withIdentifier(109)
                    .withIconTintingEnabled(true)
                    .withName("Вопросы о телеграмме")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_baseline_help_24),


                )
            //Listener>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    when (drawerItem.identifier.toInt()) {
                        107 -> {
                            mainActivity.replaceFragment(SettingsFragment())
                        }
                    }
                    return false
                }

            }).build()


    }

    private fun createHeader() {
        header = AccountHeaderBuilder()
            .withActivity(mainActivity)
            .withHeaderBackground(R.drawable.header)
            .addProfiles(
                ProfileDrawerItem()
                    .withName("Ivan Ivanov")
                    .withEmail("+3 095 454 34 54")
            ).build()
    }
}