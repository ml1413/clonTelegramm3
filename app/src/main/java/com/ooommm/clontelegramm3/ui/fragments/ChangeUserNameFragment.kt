package com.ooommm.clontelegramm3.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.ooommm.clontelegramm3.MainActivity
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.databinding.FragmentChangeUserNameBinding
import com.ooommm.clontelegramm3.utilits.*
import java.util.*


class ChangeUserNameFragment : BaseFragment(R.layout.fragment_change_user_name) {
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
        setHasOptionsMenu(true)
        binding.settingsInputUserName.setText(USER.username)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        changeName()//нажатие на галочку подтвердить изминения
        return true
    }

    private fun changeName() {
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
            .setValue(UID)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateCurrentUserName()
                }
            }
    }

    private fun updateCurrentUserName() {
        REF_DATABASE_ROOT
            .child(NODE_USERS)
            .child(UID)
            .child(NODE_USERNAME).setValue(newUserName)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.toast_data_update))
                    deleteoldUserName()
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }

    private fun deleteoldUserName() {
        REF_DATABASE_ROOT
            .child(NODE_USERNAME)
            .child(USER.username)
            .removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.toast_data_update))
                    activity?.supportFragmentManager?.popBackStack()
                    USER.username = newUserName
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }


}