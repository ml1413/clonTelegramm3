package com.ooommm.clontelegramm3.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ooommm.clontelegramm3.databinding.FragmentEnterCodeBinding
import com.ooommm.clontelegramm3.utilits.AppTextWatcher
import com.ooommm.clontelegramm3.utilits.showToast

class EnterCodeFragment : Fragment() {
    private lateinit var binding: FragmentEnterCodeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnterCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.etRegisterInputCode.addTextChangedListener(AppTextWatcher {
            val string = binding.etRegisterInputCode.text.toString()
            if (string.length == 6) {
                verifiCode()
            }
        })
    }

    private fun verifiCode() {
            showToast("OK")

    }


}