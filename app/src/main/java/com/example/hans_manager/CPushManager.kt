package com.example.hans_manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import com.example.hans_manager.CListCollector.ChannelId
import com.example.hans_manager.CListCollector.MY_kakaoId
import com.example.hans_manager.CListCollector.MY_nickName
import com.example.hans_manager.CListCollector.MY_token
import com.example.hans_manager.CListCollector.TAG
import com.example.hans_manager.CPushHttp.InsertPushData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import okhttp3.*
import okio.IOException

class CPushManager {


    fun createNotificationChannel(context: Context, importance: Int, showBadge: Boolean,
                                          name: String, description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ChannelId = "${context.packageName}-$name"
            val channel = NotificationChannel(ChannelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }


    fun linitToken() : String
    {
        var tmpToken =""
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            //val token = task.result
            val token = task.result

            Log.d(TAG,"m_My_token2 : ${token.toString()}")
            MY_token = token.toString()

            InsertPushData()
        })
        return tmpToken
    }



}