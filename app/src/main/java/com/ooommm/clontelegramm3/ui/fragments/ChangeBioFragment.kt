package com.ooommm.clontelegramm3.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.dataBase.USER
import com.ooommm.clontelegramm3.dataBase.setBioToDatabase
import com.ooommm.clontelegramm3.databinding.FragmentChangeBiofragmentBinding
import com.ooommm.clontelegramm3.utilits.*

class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_biofragment) {
    private lateinit var binding: FragmentChangeBiofragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeBiofragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.settingsInputBio.setText(USER.bio)
    }

    override fun change() {
        super.change()

        val newBio = binding.settingsInputBio.text.toString()

        setBioToDatabase(newBio = newBio)
    }



}