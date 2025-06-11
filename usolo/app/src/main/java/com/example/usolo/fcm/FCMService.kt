package com.example.usolo.fcm

import com.example.usolo.util.NotificationUtil
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject

class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        val obj = JSONObject(message.data as Map<*, *>)
        val json = obj.toString()
        NotificationUtil.showNotification(this,"Mensaje nuevo",json)
    }
}