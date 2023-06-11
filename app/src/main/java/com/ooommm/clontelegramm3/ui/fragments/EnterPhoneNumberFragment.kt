package com.ooommm.clontelegramm3.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.databinding.FragmentEnterPhoneNumberBinding
import com.ooommm.clontelegramm3.utilits.replaceFragment
import com.ooommm.clontelegramm3.utilits.showToast


class EnterPhoneNumberFragment : Fragment() {
    private lateinit var binding: FragmentEnterPhoneNumberBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnterPhoneNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.fbRegisterBtnText.setOnClickListener { sendCode() }
    }

    private fun sendCode() {
        if (binding.etRegisterPhoneNumber.text.toString().isEmpty()) {
            showToast(getString(R.string.enter_phone_number))
        } else {
            replaceFragment(fragment = EnterCodeFragment())
        }
    }
}