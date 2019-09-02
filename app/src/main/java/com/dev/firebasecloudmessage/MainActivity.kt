package com.dev.firebasecloudmessage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
     fun showToken(view:View){
        txtshow.text = FirebaseInstanceId.getInstance().token
    }
     fun subscribe(view :View){
        FirebaseMessaging.getInstance().subscribeToTopic("news")
        txtshow.text = "Subscribe to news topic"

    }
     fun unsubscribe(view : View){
        FirebaseMessaging.getInstance().unsubscribeFromTopic("news")
        txtshow.text = "UnSubscribe from news topic"
    }
}
