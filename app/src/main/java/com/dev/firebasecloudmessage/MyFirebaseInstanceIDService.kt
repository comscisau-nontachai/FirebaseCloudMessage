package com.dev.firebasecloudmessage

import android.nfc.Tag
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService


class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {

    val TAG = this.packageName

    override fun onTokenRefresh() {

        val refreshToken:String? = FirebaseInstanceId.getInstance().token

        Log.d("logd", refreshToken)

        sendRegistrationToServer(refreshToken!!)

    }
    private fun sendRegistrationToServer(token:String){

    }

}