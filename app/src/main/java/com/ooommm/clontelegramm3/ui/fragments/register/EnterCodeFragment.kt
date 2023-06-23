package com.ooommm.clontelegramm3.ui.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.PhoneAuthProvider
import com.ooommm.clontelegramm3.MainActivity
import com.ooommm.clontelegramm3.databinding.FragmentEnterCodeBinding
import com.ooommm.clontelegramm3.utilits.*

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
        APP_ACTIVITY.title = phoneNumber
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
                val uid = AUTH.currentUser?.uid.toString() // user id

                val dataMap = mutableMapOf<String, Any>()
                // add value in map
                dataMap.put(CHILD_ID, uid)
                dataMap.put(CHILD_PHONE, phoneNumber)
                dataMap.put(CHILD_USER_NAME, uid)

                REF_DATABASE_ROOT.child(NODE_PHONES).child(phoneNumber).setValue(uid)
                    .addOnFailureListener { showToast(it.message.toString()) }
                    .addOnSuccessListener {
                        REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dataMap)
                            .addOnSuccessListener {
                                showToast("Добро пожаловать")
                                restartActivity()
                            }.addOnFailureListener { showToast(it.message.toString()) }
                    }
            } else {
                showToast(task.exception?.message.toString())
            }
        }
    }


}