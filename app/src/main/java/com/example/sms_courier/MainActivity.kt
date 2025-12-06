package com.example.sms_courier

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : androidx.activity.ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Проверяем, есть ли разрешение на чтение СМС
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
            != PackageManager.PERMISSION_GRANTED) {

            // Если нет, запрашиваем его
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS), 1)
        }
    }
}