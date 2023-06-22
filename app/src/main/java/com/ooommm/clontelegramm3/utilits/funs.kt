package com.ooommm.clontelegramm3.utilits

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.imageview.ShapeableImageView
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.models.CommonModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

//show toast
fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}

//replace Activity
fun AppCompatActivity.replaceActivity(activity: Activity) {
    startActivity(Intent(this, activity::class.java))
    this.finish()
}

//replace Fragment in activity
fun AppCompatActivity.replaceFragment(fragment: Fragment, addStack: Boolean = true) {
    if (addStack) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.data_container, fragment)
            .commit()
    } else {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.data_container, fragment)
            .commit()
    }

}

//replace Fragment in fragment
fun Fragment.replaceFragment(fragment: Fragment) {
    activity?.supportFragmentManager
        ?.beginTransaction()
        ?.addToBackStack(null)
        ?.replace(R.id.data_container, fragment)
        ?.commit()
}

//hide keyboard
fun hideKeyboard() {
    val imm: InputMethodManager =
        APP_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(APP_ACTIVITY.window.decorView.windowToken, 0)
}

fun ImageView.downloadAndSetImage(url: String) {
    Picasso.get()
        .load(url)
        .fit()
        .placeholder(R.drawable.ic_baseline_person_24)
        .into(this)
}

@SuppressLint("Range")
fun initContacts() {
    if (checkPermission(READ_CONTACTS)) {
        var arrayContacts = mutableSetOf<CommonModel>()
        val cursor = APP_ACTIVITY.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        cursor?.let {
            while (it.moveToNext()) {
                val fullname =
                    it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phone =
                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        .replace(" ", "")

                val newModel = CommonModel(fullname = fullname, phone = phone)

                arrayContacts.add(newModel)

            }
        }
        //добавлен номер который зарегестрирован в firebase
        arrayContacts.add(CommonModel(fullname = "IVAN", phone = "+16505551111"))
        arrayContacts.add(CommonModel(fullname = "EGOR", phone = "+16505551234"))
        arrayContacts.add(CommonModel(fullname = "TurkMEn", phone = "+16505552222"))
        cursor?.close()
        updatePhonesToDatabase(arrayContacts)
    }
}

fun String.assTime(): String {
    val time = Date(this.toLong())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(time)
}


