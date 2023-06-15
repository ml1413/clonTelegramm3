package com.ooommm.clontelegramm3.utilits

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ooommm.clontelegramm3.R

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