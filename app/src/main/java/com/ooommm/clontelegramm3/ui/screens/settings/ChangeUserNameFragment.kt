package com.ooommm.clontelegramm3.ui.screens.settings

import android.os.Bundle
import android.view.*
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.dataBase.*
import com.ooommm.clontelegramm3.databinding.FragmentChangeUserNameBinding
import com.ooommm.clontelegramm3.ui.screens.BaseChangeFragment
import com.ooommm.clontelegramm3.utilits.*
import java.util.*


class ChangeUserNameFragment : BaseChangeFragment(R.layout.fragment_change_user_name) {
    private lateinit var binding: FragmentChangeUserNameBinding
    lateinit var newUserName: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeUserNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.settingsInputUserName.setText(USER.username)
    }


    override fun change() {
        newUserName = binding.settingsInputUserName.text.toString().lowercase(Locale.ROOT)
        if (newUserName.isEmpty()) {
            showToast("Поле пустое")
        } else {
            REF_DATABASE_ROOT.child(NODE_USERNAME)
                .addListenerForSingleValueEvent(AppValueEventListener {
                    if (it.hasChild(newUserName)) {
                        showToast("Такой пользователь уже существует ")
                    } else {
                        changeUserName()
                    }
                })
        }
    }

    private fun changeUserName() {
        REF_DATABASE_ROOT.child(NODE_USERNAME)
            .child(newUserName)
            .setValue(CURRENT_UID)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateCurrentUserName(newUserName = newUserName)
                }
            }
    }


}