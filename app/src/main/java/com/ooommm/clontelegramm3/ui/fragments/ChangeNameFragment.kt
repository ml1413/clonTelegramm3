package com.ooommm.clontelegramm3.ui.fragments

import android.os.Bundle
import android.view.*
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.dataBase.USER
import com.ooommm.clontelegramm3.dataBase.setNameToDateBase
import com.ooommm.clontelegramm3.databinding.FragmentChangeNameBinding
import com.ooommm.clontelegramm3.utilits.*

class ChangeNameFragment : BaseChangeFragment(R.layout.fragment_change_name) {

    private lateinit var binding: FragmentChangeNameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initFullName()
    }

    private fun initFullName() {
        val fullnameList = USER.fullname.split("|")
        if (fullnameList.size > 1) {
            binding.settingsInputName.setText(fullnameList[0])
            binding.settingsInputSurname.setText(fullnameList[1])
        } else {
            binding.settingsInputName.setText(fullnameList[0])
        }
    }


    override fun change() {
        val name = binding.settingsInputName.text.toString()
        val surname = binding.settingsInputSurname.text.toString()
        if (name.isEmpty()) {
            showToast(getString(R.string.settings_toast_name_is_empty))
        } else {
            val fullName = "$name|$surname"
            setNameToDateBase(fullName = fullName)

        }
    }

}