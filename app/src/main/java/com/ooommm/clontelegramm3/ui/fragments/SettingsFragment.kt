package com.ooommm.clontelegramm3.ui.fragments

import android.os.Bundle
import android.view.*
import com.ooommm.clontelegramm3.MainActivity
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.activities.RegisterActivity
import com.ooommm.clontelegramm3.databinding.FragmentSettingsBinding
import com.ooommm.clontelegramm3.utilits.AUTH
import com.ooommm.clontelegramm3.utilits.USER
import com.ooommm.clontelegramm3.utilits.replaceActivity
import com.ooommm.clontelegramm3.utilits.replaceFragment

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        binding.tvBio.text = USER.bio
        binding.tvSettingFullName.text = USER.fullname.replace("|", " ")
        binding.tvPhoneNumber.text = USER.phone
        binding.tvSettingsStatus.text = USER.status
        binding.tvLogin.text = USER.username
        binding.settingsButtonChangeUserName
            .setOnClickListener { replaceFragment(ChangeUserNameFragment()) }

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
            R.id.settings_menu_change_name -> {
                replaceFragment(ChangeNameFragment())
            }
        }
        return true
    }
}