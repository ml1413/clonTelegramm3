package com.ooommm.clontelegramm3.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.PhoneAuthProvider
import com.ooommm.clontelegramm3.MainActivity
import com.ooommm.clontelegramm3.activities.RegisterActivity
import com.ooommm.clontelegramm3.databinding.FragmentEnterCodeBinding
import com.ooommm.clontelegramm3.utilits.AUTH
import com.ooommm.clontelegramm3.utilits.AppTextWatcher
import com.ooommm.clontelegramm3.utilits.replaceActivity
import com.ooommm.clontelegramm3.utilits.showToast

class EnterCodeFragment(val phoneNumber: String, val id: String) : Fragment() {
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
        (activity as RegisterActivity).title = phoneNumber
        binding.etRegisterInputCode.addTextChangedListener(AppTextWatcher {
            val string = binding.etRegisterInputCode.text.toString()
            if (string.length == 6) {
                enterCode()
            }
        })
    }

    private fun enterCode() {
        val code = binding.etRegisterInputCode.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isComplete) {
                showToast("Добро пожаловать")
                (activity as RegisterActivity).replaceActivity(MainActivity())
            } else {
                showToast(task.exception?.message.toString())
            }
        }
    }


}