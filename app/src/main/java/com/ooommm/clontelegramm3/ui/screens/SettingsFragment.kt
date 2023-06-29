package com.ooommm.clontelegramm3.ui.screens

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.*
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.dataBase.*
import com.ooommm.clontelegramm3.databinding.FragmentSettingsBinding
import com.ooommm.clontelegramm3.utilits.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        binding.tvBio.text = USER.bio
        binding.tvSettingFullName.text = USER.fullname.replace("|", " ")
        binding.tvPhoneNumber.text = USER.phone
        binding.tvSettingsStatus.text = USER.state
        binding.tvLogin.text = USER.username

        binding.settingsButtonChangeUserName
            .setOnClickListener { replaceFragment(ChangeUserNameFragment()) }

        binding.settingsButtonAbout
            .setOnClickListener { replaceFragment(ChangeBioFragment()) }

        binding.ivSettingsChangePhoto.setOnClickListener {
            changePhotoUser()
        }

        binding.ivSettingsPhoto.downloadAndSetImage(USER.photoUrl)


    }

    private fun changePhotoUser() {
        //Cropper Image
        CropImage.activity()
            .setAspectRatio(1, 1)//соотношение сторон
            .setRequestedSize(250, 250)// обрежет фото если оно больше чем 600 х 600
            .setCropShape(CropImageView.CropShape.OVAL) // форма фото круглая
            .start(APP_ACTIVITY, this)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AppStates.updateState(AppStates.OFFLINE)
                AUTH.signOut()// exit profile
                restartActivity()
            }
            R.id.settings_menu_change_name -> {
                replaceFragment(ChangeNameFragment())
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (
            requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == RESULT_OK
            && data != null
        ) {
            val uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE_ROOT
                .child(FOLDER_PROFILE_IMAGE)
                .child(CURRENT_UID)
            //функцыи высшего порядка
            putFileToStorage(uri, path) {
                getUrlFromStorage(path) {
                    putUrlToDatabase(it) {
                        binding.ivSettingsPhoto.downloadAndSetImage(it)
                        showToast(getString(R.string.toast_data_update))
                        USER.photoUrl = it
                        APP_ACTIVITY.appDrawer.updateProfile()
                    }
                }
            }
        }
    }
}

