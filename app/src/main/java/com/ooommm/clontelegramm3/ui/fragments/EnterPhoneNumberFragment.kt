package com.ooommm.clontelegramm3.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.ooommm.clontelegramm3.MainActivity
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.activities.RegisterActivity
import com.ooommm.clontelegramm3.databinding.FragmentEnterPhoneNumberBinding
import com.ooommm.clontelegramm3.utilits.AUTH
import com.ooommm.clontelegramm3.utilits.replaceActivity
import com.ooommm.clontelegramm3.utilits.replaceFragment
import com.ooommm.clontelegramm3.utilits.showToast
import java.util.concurrent.TimeUnit


class EnterPhoneNumberFragment : Fragment() {
    private lateinit var binding: FragmentEnterPhoneNumberBinding
    private lateinit var phoneNumber: String
    private lateinit var callBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks
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

        binding.fbRegisterBtnText.setOnClickListener { sendCode() }//listener>>>>>>>>

        callBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isComplete) {
                        showToast("Добро пожаловать")
                        (activity as RegisterActivity).replaceActivity(MainActivity())
                    } else {
                        showToast(task.exception?.message.toString())
                        Log.d(
                            "TAG1",
                            "onVerificationCompleted:" + task.exception?.message.toString()
                        )
                    }
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                showToast(p0.message.toString())//show toast error
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                replaceFragment(EnterCodeFragment(phoneNumber = phoneNumber, id = id))
            }
        }
    }

    private fun sendCode() {
        if (binding.etRegisterPhoneNumber.text.toString().isEmpty()) {
            showToast(getString(R.string.enter_phone_number))
        } else {
            authUser()
        }
    }

    private fun authUser() {
        phoneNumber = binding.etRegisterPhoneNumber.text.toString()

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            activity as RegisterActivity,
            callBack
        )
//
//        PhoneAuthProvider.verifyPhoneNumber(
//            PhoneAuthOptions
//                .newBuilder(FirebaseAuth.getInstance())
//                .setActivity(activity as RegisterActivity)
//                .setPhoneNumber(phoneNumber)
//                .setTimeout(60L, TimeUnit.SECONDS)
//                .setCallbacks(callBack)
//                .build()
//        )
    }
}