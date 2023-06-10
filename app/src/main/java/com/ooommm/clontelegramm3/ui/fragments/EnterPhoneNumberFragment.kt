package com.ooommm.clontelegramm3.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.databinding.FragmentEnterPhoneNumberBinding


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
            Toast.makeText(activity, "Введите номер телефона", Toast.LENGTH_SHORT).show()
        } else {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.register_data_container, EnterCodeFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}