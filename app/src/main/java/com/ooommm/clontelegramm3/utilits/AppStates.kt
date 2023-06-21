package com.ooommm.clontelegramm3.utilits

enum class AppStates(val state: String) {
    ONLINE("в сети"),
    OFFLINE("был недавно"),
    TYPING("печаьает");

    companion object {
        fun updateState(appStates: AppStates) {
            if (AUTH.currentUser != null) {
                REF_DATABASE_ROOT
                    .child(NODE_USERS)
                    .child(CURRENT_UID)
                    .child(CHILD_STATUS)
                    .setValue(appStates.state)
                    .addOnSuccessListener { USER.state = appStates.state }
                    .addOnFailureListener {
                        showToast(it.message.toString())
                    }
            }
        }
    }
}