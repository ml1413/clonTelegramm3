package com.ooommm.clontelegramm3.ui.screens.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.dataBase.AUTH
import com.ooommm.clontelegramm3.databinding.FragmentEnterPhoneNumberBinding
import com.ooommm.clontelegramm3.utilits.*
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
                        restartActivity()
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
            APP_ACTIVITY,
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