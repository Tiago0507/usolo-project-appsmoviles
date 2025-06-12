package com.example.usolo.fcm

import android.util.Log
import com.example.usolo.util.NotificationUtil
import com.google.auth.oauth2.AccessToken
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeUnit

class FCMService : FirebaseMessagingService() {

    private val PROJECT_NAME = "usoloproject"
    private val KEY_FILE_NAME = "key.json"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    fun getAccessToken() : String{
        val inputStream : InputStream = assets.open(KEY_FILE_NAME)
        val googleCredentials = GoogleCredentials
            .fromStream(inputStream)
            .createScoped(listOf("https://www.googleapis.com/auth/firebase.messaging"))

        googleCredentials.refreshIfExpired()
        val token : AccessToken = googleCredentials.accessToken
        return token.tokenValue
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val obj = JSONObject(message.data as Map<*, *>)
        val json = obj.toString()
        NotificationUtil.showNotification(this,"Mensaje nuevo",json)
    }

    @Throws(IOException::class)
    fun POSTtoFCM(json:String, FCM_KEY:String) : Boolean {
        val url = "https://fcm.googleapis.com/v1/projects/$PROJECT_NAME/messages:send"

        val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .addHeader("Content-Type", "application/json; UTF-8")
            .addHeader("Authorization", "Bearer $FCM_KEY")
            .build()

        return client.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                Log.d(
                    "TAG_FCM_SERVICE",
                    "Notificación enviada exitosamente: ${response.body?.string()}"
                )
                true
            } else {
                val errorBody = response.body?.string()
                Log.e("TAG_FCM_SERVICE", "Error HTTP: ${response.code}, $errorBody")
                throw IOException("HTTP Error: ${response.code}, $errorBody") as Throwable
            }
        }
    }
}