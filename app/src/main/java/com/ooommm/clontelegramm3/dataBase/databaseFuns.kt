package com.ooommm.clontelegramm3.dataBase

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.models.CommonModel
import com.ooommm.clontelegramm3.models.UserModel
import com.ooommm.clontelegramm3.utilits.APP_ACTIVITY
import com.ooommm.clontelegramm3.utilits.AppValueEventListener
import com.ooommm.clontelegramm3.utilits.TYPE_GROUP
import com.ooommm.clontelegramm3.utilits.showToast
import java.io.File

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    USER = UserModel()
    CURRENT_UID = AUTH.currentUser?.uid.toString()
    REF_STORAGE_ROOT = FirebaseStorage.getInstance().reference
}

inline fun putUrlToDatabase(it: String, crossinline function: () -> Unit) {
    REF_DATABASE_ROOT
        .child(NODE_USERS)
        .child(CURRENT_UID)
        .child(CHILD_PHOTO_URL)
        .setValue(it)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun getUrlFromStorage(path: StorageReference, crossinline function: (url: String) -> Unit) {
    path.downloadUrl
        .addOnSuccessListener { function(it.toString()) }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun putFileToStorage(uri: Uri, path: StorageReference, crossinline function: () -> Unit) {
    path.putFile(uri)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }

}

inline fun initUser(crossinline function: () -> Unit) {
    REF_DATABASE_ROOT
        .child(NODE_USERS)
        .child(CURRENT_UID)
        .addListenerForSingleValueEvent(AppValueEventListener {
            USER = it.getValue(UserModel::class.java) ?: UserModel()
            if (USER.username.isEmpty()) {
                USER.username = CURRENT_UID
            }
            function()
        })


}

fun updatePhonesToDatabase(arrayContacts: MutableSet<CommonModel>) {
    if (AUTH.currentUser != null) {
        REF_DATABASE_ROOT.child(NODE_PHONES)
            .addListenerForSingleValueEvent(AppValueEventListener {
                it.children.forEach { snapshot ->
                    arrayContacts.forEach { contact ->
                        if (snapshot.key == contact.phone) {
                            REF_DATABASE_ROOT
                                .child(NODE_PHONES_CONTACTS)
                                .child(CURRENT_UID)
                                .child(snapshot.value.toString()).child(CHILD_ID)
                                .setValue(snapshot.value.toString())
                                .addOnFailureListener { showToast(it.message.toString()) }

                            REF_DATABASE_ROOT
                                .child(NODE_PHONES_CONTACTS)
                                .child(CURRENT_UID)
                                .child(snapshot.value.toString()).child(CHILD_FULLNAME)
                                .setValue(contact.fullname)
                                .addOnFailureListener { showToast(it.message.toString()) }
                        }
                    }
                }
            })
    }
}

fun sendMessage(message: String, receivingUserID: String, typeText: String, function: () -> Unit) {

    val refDialogUser = "$NODE_MESSAGES/$CURRENT_UID/$receivingUserID"
    val refDialogReceivingUser = "$NODE_MESSAGES/$receivingUserID/$CURRENT_UID"
    val messageKey = REF_DATABASE_ROOT.child(refDialogUser).push().key

    val mapMessage = hashMapOf<String, Any>()
    mapMessage.put(CHILD_FROM, CURRENT_UID)
    mapMessage.put(CHILD_TYPE, typeText)
    mapMessage.put(CHILD_TEXT, message)
    mapMessage.put(CHILD_ID, messageKey.toString())
    mapMessage.put(CHILD_TIMESTAMP, ServerValue.TIMESTAMP)

    val mapDialog = hashMapOf<String, Any>()
    mapDialog.put("$refDialogUser/$messageKey", mapMessage)
    mapDialog.put("$refDialogReceivingUser/$messageKey", mapMessage)

    REF_DATABASE_ROOT
        .updateChildren(mapDialog)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }

}

fun updateCurrentUserName(newUserName: String) {
    REF_DATABASE_ROOT
        .child(NODE_USERS)
        .child(CURRENT_UID)
        .child(NODE_USERNAME).setValue(newUserName)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                showToast(APP_ACTIVITY.getString(R.string.toast_data_update))
                deleteoldUserName(newUserName = newUserName)
            } else {
                showToast(it.exception?.message.toString())
            }
        }
}

private fun deleteoldUserName(newUserName: String) {
    REF_DATABASE_ROOT
        .child(NODE_USERNAME)
        .child(USER.username)
        .removeValue()
        .addOnSuccessListener {
            showToast(APP_ACTIVITY.getString(R.string.toast_data_update))
            APP_ACTIVITY.supportFragmentManager.popBackStack()
            USER.username = newUserName
        }.addOnFailureListener {
            showToast(it.message.toString())
        }
}

fun setBioToDatabase(newBio: String) {
    REF_DATABASE_ROOT
        .child(NODE_USERS)
        .child(CURRENT_UID)
        .child(CHILD_BIO)
        .setValue(newBio)
        .addOnSuccessListener {
            showToast(APP_ACTIVITY.getString(R.string.toast_data_update))
            USER.bio = newBio
            APP_ACTIVITY.supportFragmentManager.popBackStack()
        }.addOnFailureListener {
            showToast(it.message.toString())
        }
}

fun setNameToDateBase(fullName: String) {
    REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_FULLNAME)
        .setValue(fullName)
        .addOnSuccessListener {
            showToast(APP_ACTIVITY.getString(R.string.toast_data_update))
            USER.fullname = fullName
            APP_ACTIVITY.appDrawer.updateProfile()
            APP_ACTIVITY.supportFragmentManager.popBackStack()
        }.addOnFailureListener {
            showToast(it.message.toString())
        }
}

fun sendMessageAsFile(
    receivingUserID: String,
    fileUrl: String,
    messageKey: String,
    typeMessage: String,
    filename: String
) {

    val refDialogUser = "$NODE_MESSAGES/$CURRENT_UID/$receivingUserID"
    val refDialogReceivingUser = "$NODE_MESSAGES/$receivingUserID/$CURRENT_UID"

    val mapMessage = hashMapOf<String, Any>()
    mapMessage.put(CHILD_FROM, CURRENT_UID)
    mapMessage.put(CHILD_TYPE, typeMessage)
    mapMessage.put(CHILD_ID, messageKey)
    mapMessage.put(CHILD_TIMESTAMP, ServerValue.TIMESTAMP)
    mapMessage.put(CHILD_FILE_URL, fileUrl)
    mapMessage.put(CHILD_TEXT, filename)

    val mapDialog = hashMapOf<String, Any>()
    mapDialog.put("$refDialogUser/$messageKey", mapMessage)
    mapDialog.put("$refDialogReceivingUser/$messageKey", mapMessage)

    REF_DATABASE_ROOT
        .updateChildren(mapDialog)
        .addOnFailureListener { showToast(it.message.toString()) }

}

fun getMessageKey(contactId: String) = REF_DATABASE_ROOT
    .child(NODE_MESSAGES)
    .child(CURRENT_UID)
    .child(contactId).push().key.toString()

fun uploadFileToStorage(
    uri: Uri,
    messageKey: String,
    receivedID: String,
    typeMessage: String,
    fileName: String = ""
) {
    val path = REF_STORAGE_ROOT
        .child(FOLDER_FILES)
        .child(messageKey)
    //положить ссылку на файл в storage
    putFileToStorage(uri, path) {
        getUrlFromStorage(path) {
            sendMessageAsFile(receivedID, it, messageKey, typeMessage, fileName)
        }
    }
}

// скачиваем  файля для проигрывания в случае отсутствия его в системе
fun getFileFromStorage(file: File, fileUrl: String, function: () -> Unit) {
    val path = REF_STORAGE_ROOT.storage.getReferenceFromUrl(fileUrl)
    path.getFile(file)
        .addOnSuccessListener { function() }
        .addOnFailureListener {
            showToast(it.message.toString())
            Log.d("TAG1", "getFileFromStorage: ${it.message}")
        }

}

fun saveToMailList(id: String, type: String) {
    val refUser = "$NODE_MAIN_LIST/$CURRENT_UID/$id"
    val refReceived = "$NODE_MAIN_LIST/$id/$CURRENT_UID"

    val userMap = hashMapOf<String, Any>()
    val receivedMap = hashMapOf<String, Any>()
    userMap.put(CHILD_ID, id)
    userMap.put(CHILD_TYPE, type)
    receivedMap.put(CHILD_ID, CURRENT_UID)
    receivedMap.put(CHILD_TYPE, type)

    val commonMap = hashMapOf<String, Any>()
    commonMap.put(refUser, userMap)
    commonMap.put(refReceived, receivedMap)

    REF_DATABASE_ROOT.updateChildren(commonMap)
        .addOnFailureListener { showToast(it.message.toString()) }
}

fun deleteChat(id: String, function: () -> Unit) {
    REF_DATABASE_ROOT
        .child(NODE_MAIN_LIST)
        .child(CURRENT_UID)
        .child(id)
        .removeValue()
        .addOnFailureListener { showToast(it.message.toString()) }// показать тост если не удача
        .addOnSuccessListener { function() }// если все успешно вызываем колбек
}

fun clearChat(id: String, function: () -> Unit) {
    REF_DATABASE_ROOT
        .child(NODE_MESSAGES)
        .child(CURRENT_UID)
        .child(id)
        .removeValue() // удаляем все сообщения пользователя
        .addOnFailureListener { showToast(it.message.toString()) }// показать тост если не удача
        // если удаление прошло успешно то удаляем свои сообщения у пользователя
        .addOnSuccessListener {
            REF_DATABASE_ROOT
                .child(NODE_MESSAGES)
                .child(id)
                .child(CURRENT_UID)
                .removeValue()//удаляем наши сообщения у пользователя
                .addOnSuccessListener { function() }// если все успешно вызываем колбек
        }
        .addOnFailureListener { showToast(it.message.toString()) }// показать тост если не удача
}


fun createGroupToDataBase(
    nameGroup: String,
    uri: Uri,
    listContacts: MutableList<CommonModel>,
    function: () -> Unit
) {
    val keyGroup = REF_DATABASE_ROOT.child(NODE_GROUPS).push().key.toString()
    val path = REF_DATABASE_ROOT.child(NODE_GROUPS).child(keyGroup)
    val pathStorage = REF_STORAGE_ROOT.child(FOLDER_GROUPS_IMAGE).child(keyGroup)

    val mapData = hashMapOf<String, Any>()
    mapData.put(CHILD_ID, keyGroup)
    mapData.put(CHILD_FULLNAME, nameGroup)
    mapData.put(CHILD_PHOTO_URL, "empty")
    val mapMembers = hashMapOf<String, Any>()

    listContacts.forEach {
        mapMembers.put(it.id, USER_MEMBER)
    }
    mapMembers.put(CURRENT_UID, USER_CREATOR)

    mapData.put(NODE_MEMBERS, mapMembers)

    path.updateChildren(mapData)
        .addOnSuccessListener {
            if (uri != Uri.EMPTY) {
                putFileToStorage(uri, pathStorage) {
                    getUrlFromStorage(pathStorage) {
                        path.child(CHILD_PHOTO_URL).setValue(it)
                        addGroupToMainList(mapData, listContacts) {
                            function()
                        }
                    }
                }
            } else {
                addGroupToMainList(mapData, listContacts) {
                    function()
                }
            }

        }
        .addOnFailureListener { showToast(it.message.toString()) }
}

fun addGroupToMainList(
    mapData: HashMap<String, Any>,
    listContacts: MutableList<CommonModel>,
    function: () -> Unit
) {
    val path = REF_DATABASE_ROOT.child(NODE_MAIN_LIST)
    val map = hashMapOf<String, Any>()
    map.put(CHILD_ID, mapData.getValue(CHILD_ID).toString())
    map.put(CHILD_TYPE, TYPE_GROUP)

    for (i in listContacts) {
        path.child(i.id).child(map.getValue(CHILD_ID).toString())
            .updateChildren(map)
    }
    path.child(CURRENT_UID).child(map.getValue(CHILD_ID).toString())
        .updateChildren(map)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

fun sendMessageToGroup(message: String, groupId: String, typeText: String, function: () -> Unit) {
    var refMessages = "$NODE_GROUPS/$groupId/$NODE_MESSAGES"

    val messageKey = REF_DATABASE_ROOT.child(refMessages).push().key

    val mapMessage = hashMapOf<String, Any>()
    mapMessage.put(CHILD_FROM, CURRENT_UID)
    mapMessage.put(CHILD_TYPE, typeText)
    mapMessage.put(CHILD_TEXT, message)
    mapMessage.put(CHILD_ID, messageKey.toString())
    mapMessage.put(CHILD_TIMESTAMP, ServerValue.TIMESTAMP)

    REF_DATABASE_ROOT
        .child(refMessages)
        .child(messageKey.toString())
        .updateChildren(mapMessage)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }

}


//extension fun
fun DataSnapshot.getCommonModel(): CommonModel {
    return this.getValue(CommonModel::class.java) ?: CommonModel()
}

fun DataSnapshot.getUserModel(): UserModel {
    return this.getValue(UserModel::class.java) ?: UserModel()
}