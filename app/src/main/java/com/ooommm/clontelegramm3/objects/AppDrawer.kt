package com.ooommm.clontelegramm3.objects

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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

class AppDrawer(val activity: AppCompatActivity, val toolbar: Toolbar) {
    private lateinit var drawer: Drawer
    private lateinit var header: AccountHeader

    fun create() {
        //init  header1
        createHeader()
        //init drawer1
        createDrawer()
    }

    private fun createDrawer() {
        drawer = DrawerBuilder()
            .withActivity(activity)
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
                            activity.supportFragmentManager
                                .beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.main_container, SettingsFragment())
                                .commit()
                        }
                    }
                    return false
                }

            }).build()


    }

    private fun createHeader() {
        header = AccountHeaderBuilder()
            .withActivity(activity)
            .withHeaderBackground(R.drawable.header)
            .addProfiles(
                ProfileDrawerItem()
                    .withName("Ivan Ivanov")
                    .withEmail("+3 095 454 34 54")
            ).build()
    }
}